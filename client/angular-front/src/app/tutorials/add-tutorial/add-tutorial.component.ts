import {Component, Inject, OnInit} from '@angular/core';
import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import {MatChipInputEvent, MatSnackBar} from '@angular/material';
import {DOCUMENT, Location} from '@angular/common';

import {MaterialType} from '../../domain/materialType';
import {Material} from '../../domain/tutorialMaterial';
import {Tutorial} from '../../domain/tutorial';

import {UserService} from '../../service/user.service';
import {TutorialService} from '../../service/tutorial.service';
import {MaterialService} from '../../service/material.service';

import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import {LocalStorageConst} from '../../util/LocalStorageConst';
import {FrontURLs} from '../../util/FrontURLs';

@Component({
  selector: 'app-add-tutorial',
  templateUrl: './add-tutorial.component.html',
  styleUrls: ['./add-tutorial.component.css']
})
export class AddTutorialComponent implements OnInit {

  public dropdownSettings = {};
  public selectedUsers: String[] = [];
  public allUsers: String[] = [];

  public tutorial = new Tutorial();
  public materialsForCurrentTutorial: Material[] = [];
  public materialType = MaterialType;
  public files: File[] = [];

  public keywords: String[] = [];
  public inputKeyword: any;

  public separatorKeysCodes = [ENTER, COMMA, SPACE];

  constructor(private location: Location,
              private tutorialService: TutorialService,
              private userService: UserService,
              private materialService: MaterialService,
              @Inject(DOCUMENT) private document: any,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.getUserMsgMails();

    this.setCurrentUserAsContactPerson();

    this.dropdownSettings = {
      singleSelection: false,
      allowSearchFilter: true,
      selectAllText: 'Select All',
      unSelectAllText: 'Unselect All',
      itemsShowLimit: 1,
    };
  }

  private setCurrentUserAsContactPerson() {
    this.selectedUsers.push(localStorage.getItem(LocalStorageConst._MSG_MAIL));
  }

  private getUserMsgMails() {
    this.userService.getAllMsgMails().subscribe((users: String[]) => {
      this.allUsers = users;
    });
  }

  addTutorial(): void {
    try {
      this.getUploadedFiles();
      this.tutorial.keywords = this.keywords.join(' ');
      this.verifyConstraintsForTutorial();
      this.materialsForCurrentTutorial.forEach((material, index) => this.verifyConstraintsForMaterial(index + 1, material));
      this.tutorialService.addTutorial(this.tutorial, this.selectedUsers).subscribe(tutorial => {
        this.tutorial = tutorial;
        this.addMaterials();
        this.redirectToTutorialPage(this.tutorial.idTutorial);
      }, err => {
        this.snackBarMessagePopup(err.error.message, 'Close');
      });
    } catch (e) {
      if (e instanceof Error) {
        console.log(e);
        this.snackBarMessagePopup(e.message, 'Close');
      }
    }
  }

  private addMaterials() {
    if (this.materialsForCurrentTutorial.length > 0) {
      for (const material of this.materialsForCurrentTutorial) {
        if (!material.idMaterial) {
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
    location.replace(FrontURLs.TUTORIALS_PAGE + '/' + `${tutorialId}`);
  }

  openFile(position: number): void {
    const blobFile = (<HTMLInputElement>document.getElementById(position.toString())).files[0].slice();
    const file = new Blob([blobFile], {type: 'application/pdf'});
    const fileURL = URL.createObjectURL(file);
    window.open(fileURL);
  }

  addNewEmptyMaterial() {
    this.materialsForCurrentTutorial.push(new Material());
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
    if (!this.tutorial.overview || this.tutorial.overview.length < 10) {
      tutorialErrorMessage += 'Description must contain at least 10 characters!\n';
    }
    if (!this.tutorial.overview || this.tutorial.overview.length > 500) {
      tutorialErrorMessage += 'Description must contain at most 500 characters!\n';
    }
    if (!this.tutorial.keywords || this.tutorial.keywords.length < 1) {
      tutorialErrorMessage += 'Tutorial must have at least one keyword!\n';
    }
    if (!this.selectedUsers || this.selectedUsers.length < 1) {
      tutorialErrorMessage += 'Tutorial must have at least one contact person!\n';
    }
    if (tutorialErrorMessage !== '') {
      throw new Error(tutorialErrorMessage);
    }
  }

  private verifyConstraintsForMaterial(positionInList: number, material: Material) {
    let materialErrorMessage = '';
    if (!material.title) {
      materialErrorMessage += `Title is required for material with number ${positionInList}!`;
    } else {
      if (material.title.length < 5) {
        materialErrorMessage += `Title is too short for material with number ${positionInList}! Required at least 5 characters`;
      }
    }

    if (!material.materialType) {
      materialErrorMessage += `Material type is required for material with number ${positionInList}!`;
    }

    if (materialErrorMessage !== '') {
      throw new Error(materialErrorMessage);
    }
  }

  getFileWithId(idFile: number) {
    this.materialService.getFileWithId(idFile).subscribe((response) => {
      const file = new Blob([response], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

  private deleteFromServerMaterials() {
    const ids = this.materialsForCurrentTutorial.map(ma => ma.idMaterial);
    this.tutorial.materials.forEach(mat => {
      if (mat.idMaterial && ids.indexOf(mat.idMaterial) < 0) {
        this.materialService.deleteMaterialWithId(mat.idMaterial).subscribe();
      }
    });
  }

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }

  goBack() {
    history.go(-1);
  }

}
