import {Component, Inject, OnInit} from '@angular/core';
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
import {Ng2OrderPipe} from 'ng2-order-pipe';
import {FormControl} from '@angular/forms';


@Component({
  selector: 'app-add-tutorial',
  templateUrl: './add-tutorial.component.html',
  styleUrls: ['./add-tutorial.component.css']
})
export class AddTutorialComponent implements OnInit {
  private rootConst: RootConst;

  public selectedContactPersonsIds: number[];
  public dropdownSettings = {};
  public usersOptions: UserDTO[];

  public tutorial: TutorialDTO;
  public materialsForCurrentTutorial: TutorialMaterialDTO[];
  public materialType = MaterialType;
  public files: File[];

  public keywords: String[];
  public inputKeyword: any;

  public tutorialErrorMessage: string;
  separatorKeysCodes = [ENTER, COMMA, SPACE];
  public contacts = new FormControl();

  constructor(private location: Location, private tutorialService: TutorialService, private userService: UserService,
              private materialService: MaterialService, @Inject(DOCUMENT) private document: any, public snackBar: MatSnackBar) {
    this.keywords = [];
    this.rootConst = new RootConst();
    this.tutorial = new TutorialDTO();
    this.tutorial.overview = '';
    this.tutorial.titleTutorial = '';
    this.files = [];
    this.materialsForCurrentTutorial = [];
    this.usersOptions = [];
    this.selectedContactPersonsIds = [];
  }

  ngOnInit() {
    this.getUsers();
    this.dropdownSettings = {
      singleSelection: false,
      allowSearchFilter: true,
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 1,
    };
  }

  private getUsers() {
    this.userService.getAllUsers().subscribe(us => {
      this.usersOptions = us;
    });
  }


  addTutorial(): void {
    this.getUploadedFiles();
    alert(this.selectedContactPersonsIds);

    this.verifyConstraintsForTutorial();

    this.tutorial.keywords = this.keywords.join(' ');

    this.tutorialService.addTutorial(this.tutorial, this.selectedContactPersonsIds).subscribe(tutorial => {
      this.tutorial = tutorial;
      this.addMaterials();
      this.snackBarMessagePopup('You added a new tutorial successfully!');
    }, err => {
      alert(err.error.message);
    });
  }

  private addMaterials() {
    for (const material of this.materialsForCurrentTutorial) {
      if (material.materialType.valueOf().toString() === MaterialType[MaterialType.LINK].toString()) {
        this.materialService.addMaterialToTutorial(material, null, this.tutorial.idTutorial);
      } else {
        console.log(this.files[0]);
        this.materialService.addMaterialToTutorial(material, this.files[0], this.tutorial.idTutorial);
        this.files.splice(0, 1);
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

  redirectToTutorialsPage() {
    location.replace(this.rootConst.FRONT_TUTORIALS_PAGE);
  }

  openFile(position: number): void {
    const blobFile = (<HTMLInputElement>document.getElementById(position.toString())).files[0].slice();
    const file = new Blob([blobFile], {type: 'application/pdf'});
    const fileURL = URL.createObjectURL(file);
    window.open(fileURL);
  }

  snackBarMessagePopup(message: string) {
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }

  addNewEmptyMaterial() {
    this.materialsForCurrentTutorial.push(new TutorialMaterialDTO());
  }

  deleteMaterial(positionInList: number) {
    this.materialsForCurrentTutorial.splice(positionInList, 1);
  }

  openURL(link: string) {
    window.open(link);
  }

  private verifyConstraintsForTutorial() {
    if (this.tutorial.titleTutorial.length < 5) {
      this.tutorialErrorMessage += 'Title is too short!\n';
    }
    if (this.tutorial.overview.length > 500) {
      this.tutorialErrorMessage += 'Description must contain at most 500 characters!\n';
    }

    if (this.tutorialErrorMessage !== '') {
      this.snackBarMessagePopup(this.tutorialErrorMessage);
      this.tutorialErrorMessage = '';
      return;
    }
  }
}
