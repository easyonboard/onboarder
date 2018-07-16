import {AfterViewChecked, Component, Inject, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import {MatChipInputEvent, MatSnackBar} from '@angular/material';
import {DOCUMENT, Location} from '@angular/common';

import {UserDTO} from '../../domain/user';
import {MaterialType} from '../../domain/materialType';
import {TutorialMaterialDTO} from '../../domain/tutorialMaterial';
import {TutorialDTO} from '../../domain/tutorial';

import {UserService} from '../../service/user.service';
import {TutorialService} from '../../service/tutorial.service';
import {MaterialService} from '../../service/material.service';
import {RootConst} from '../../util/RootConst';

import {map} from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import {ActivatedRoute} from '@angular/router';
import {LocalStorageConst} from '../../util/LocalStorageConst';
import {MyException} from 'ng-multiselect-dropdown/multiselect.model';


@Component({
  selector: 'app-add-tutorial',
  templateUrl: './add-update-tutorial.component.html',
  styleUrls: ['./add-update-tutorial.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AddUpdateTutorialComponent implements OnInit, AfterViewChecked {
  private rootConst = new RootConst();

  public selectedContactPersonsIds: number[] = [];
  public dropdownSettings = {};
  public usersOptions: UserDTO[] = [];

  tutorial = new TutorialDTO();
  public materialsForCurrentTutorial: TutorialMaterialDTO[] = [];
  public materialType = MaterialType;
  public files: File[] = [];

  public keywords: String[] = [];
  public inputKeyword: any;

  public separatorKeysCodes = [ENTER, COMMA, SPACE];
  public onUpdateTutorialMode = false;
  private tutorialId: number = null;

  public show;

  constructor(private location: Location,
              private tutorialService: TutorialService,
              private userService: UserService,
              private materialService: MaterialService,
              @Inject(DOCUMENT) private document: any,
              public snackBar: MatSnackBar,
              private route: ActivatedRoute) {
    this.tutorialId = +this.route.snapshot.paramMap.get('id');
    this.show = LocalStorageConst.IS_DEMO_ENABLED;
  }


  ngOnInit() {
    this.getUsers();
    if (this.tutorialId) {
      this.getTutorialInformation();
    }
    this.dropdownSettings = {
      singleSelection: false,
      allowSearchFilter: true,
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 1,
    };
  }

  ngAfterViewChecked() {
    this.show = LocalStorageConst.IS_DEMO_ENABLED;
  }

  private getTutorialInformation() {
    this.onUpdateTutorialMode = true;
    const tutorialId = +this.route.snapshot.paramMap.get('id');
    this.tutorialService.getTutorialWithId(tutorialId).subscribe(tutorial => {
      this.tutorial = tutorial;
      this.selectedContactPersonsIds = this.tutorial.contactPersons.map(cp => cp.idUser);
      this.keywords = this.tutorial.keywords.split(' ');
      this.materialsForCurrentTutorial = this.tutorial.tutorialMaterials.slice(0);
    });
  }

  private setCurrentUserAsContactPerson() {
    const currentUser = this.usersOptions.find(u => u.msgMail === localStorage.getItem('msgMail'));
    this.selectedContactPersonsIds.push(currentUser.idUser);
  }

  private getUsers() {
    this.userService.getAllUsers().subscribe(us => {
      this.usersOptions = us;
      if (!this.tutorialId) {
        this.setCurrentUserAsContactPerson();
      }
    });
  }


  addTutorial(): void {
    try {
      this.getUploadedFiles();

      this.verifyConstraintsForTutorial();

      this.tutorial.keywords = this.keywords.join(' ');

      this.tutorialService.addTutorial(this.tutorial, this.selectedContactPersonsIds).subscribe(tutorial => {
        this.tutorial = tutorial;
        this.addMaterials();
        this.redirectToTutorialPage(this.tutorial.idTutorial);
      }, err => {
        this.snackBarMessagePopup('Error!');
      });
    } catch (e) {
      this.snackBarMessagePopup(e);
    }
  }

  private addMaterials() {
    if (this.materialsForCurrentTutorial.length > 0) {
      for (const material of this.materialsForCurrentTutorial) {
        if (!material.idTutorialMaterial) {
          if (material.materialType.valueOf().toString() === MaterialType[MaterialType.LINK].toString()) {
            this.materialService.addMaterialToTutorial(material, null, this.tutorial.idTutorial);
          } else {
            this.materialService.addMaterialToTutorial(material, this.files[0], this.tutorial.idTutorial);
            this.files.splice(0, 1);
          }
        }
      }
    }
  }

  private getUploadedFiles() {
    for (const positionOfMaterial in this.materialsForCurrentTutorial) {
      if (document.getElementById(positionOfMaterial)) {
        this.files.push(((<HTMLInputElement>document.getElementById(positionOfMaterial)).files[0]));
      }
    }
  }

  removeKeyword(keyword: any): void {
    const index = this.keywords.indexOf(keyword);
    if (index >= 0) {
      this.keywords.splice(index, 1);
      if (this.inputKeyword.hidden === true) {
        this.inputKeyword.hidden = false;
      }
    }
  }

  addKeyword(event: MatChipInputEvent): void {
    this.inputKeyword = event.input;
    const value = event.value;
    if ((value || '').trim() && this.keywords.length < 4) {
      this.keywords.push(value.trim());
      if (this.inputKeyword) {
        this.inputKeyword.value = '';
      }
      if (this.keywords.length === 4) {
        this.inputKeyword.hidden = true;
      }
    }
  }

  private redirectToTutorialPage(tutorialId: number) {
    location.replace(this.rootConst.FRONT_TUTORIALS_PAGE + '/' + `${tutorialId}`);
  }

  openFile(position: number): void {
    const blobFile = (<HTMLInputElement>document.getElementById(position.toString())).files[0].slice();
    const file = new Blob([blobFile], {type: 'application/pdf'});
    const fileURL = URL.createObjectURL(file);
    window.open(fileURL);
  }

  addNewEmptyMaterial() {
    this.materialsForCurrentTutorial.push(new TutorialMaterialDTO());
  }

  removeMaterialFromUI(positionInList: number) {
    if (confirm('Do you want to delete this material?')) {
      this.materialsForCurrentTutorial.splice(positionInList, 1);
      this.files.splice(positionInList, 1);
    }
  }

  openURL(link: string) {
    window.open(link);
  }

  private verifyConstraintsForTutorial() {
    let tutorialErrorMessage = '';
    if (!this.tutorial.titleTutorial || this.tutorial.titleTutorial.length < 5) {
      tutorialErrorMessage += 'Title is too short!\n';
    }
    if (!this.tutorial.overview || this.tutorial.overview.length > 500 ) {
      tutorialErrorMessage += 'Description must contain at most 500 characters!\n';
    }
    if (tutorialErrorMessage !== '') {
      throw new Error(tutorialErrorMessage);
    }
  }

  getFileWithId(idFile: number) {
    this.materialService.getFileWithId(idFile).subscribe((response) => {
      const file = new Blob([response], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

  updateTutorial() {
    this.getUploadedFiles();
    this.verifyConstraintsForTutorial();

    this.tutorial.keywords = this.keywords.join(' ');

    this.tutorialService.updateTutorial(this.tutorial, this.selectedContactPersonsIds).subscribe(tutorial => {
      this.tutorial = tutorial;
      this.addMaterials();
      this.deleteFromServerMaterials();
      this.redirectToTutorialPage(this.tutorial.idTutorial);
    });
  }


  private deleteFromServerMaterials() {
    const ids = this.materialsForCurrentTutorial.map(ma => ma.idTutorialMaterial);
    this.tutorial.tutorialMaterials.forEach(mat => {
      if (mat.idTutorialMaterial && ids.indexOf(mat.idTutorialMaterial) < 0) {
        this.materialService.deleteMaterialWithId(mat.idTutorialMaterial).subscribe();
      }
    });
  }

  snackBarMessagePopup(message: string) {
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }

  saveAsDraft() {
    this.tutorial.draft = true;
    if (this.tutorial.idTutorial) {
      this.updateTutorial();
    } else {
      this.addTutorial();
    }
  }

  publishDraft() {
    this.tutorial.draft = false;
    this.updateTutorial();
  }

  goBack() {
    history.go(-1);
  }


}
