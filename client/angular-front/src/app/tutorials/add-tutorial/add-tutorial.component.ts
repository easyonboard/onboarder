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

import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-add-tutorial',
  templateUrl: './add-tutorial.component.html',
  styleUrls: ['./add-tutorial.component.css']
})
export class AddTutorialComponent implements OnInit {

  private rootConst: RootConst;

  // multiselect
  public selectedItems = [];
  public dropdownSettings = {};

  public saved: Boolean;
  public tutorial: TutorialDTO;

  public materialTypeLink: MaterialType.LINK;
  public materialTypeFile: MaterialType.FILE;
  public material: TutorialMaterialDTO;
  public file: File;

  public keywords: String[];
  public inputKeyword: any;

  public selectedMaterialType: string;
  public materialErrorMessage: string;
  public tutorialErrorMessage: string;

  public usersOptions: string[];
  public contactPersonsUsername: string[];

  separatorKeysCodes = [ENTER, COMMA, SPACE];

  private currentStep: string;
  public materialsForCurrentTutorial: TutorialMaterialDTO[] = [];


  constructor(private location: Location, private tutorialService: TutorialService, private userService: UserService,
              private materialService: MaterialService, @Inject(DOCUMENT) private document: any, public snackBar: MatSnackBar) {
    this.keywords = [];
    this.contactPersonsUsername = [];
    this.rootConst = new RootConst();
    this.tutorial = new TutorialDTO();
    this.tutorial.overview = '';
    this.tutorial.titleTutorial = '';
    this.material = new TutorialMaterialDTO();
    this.material.description = '';
    this.material.title = '';
    this.saved = false;

    var userArrayObjects: Array<UserDTO> = new Array<UserDTO>();
    this.userService.getAllUsers().subscribe(us => {
      userArrayObjects = userArrayObjects.concat(us);
      this.usersOptions = [];
      userArrayObjects.forEach(u => this.usersOptions.push(u.name + '(' + u.username + ')' + ', email:  ' + u.email));
    });
  }

  ngOnInit() {
    this.currentStep = 'one';
    this.materialErrorMessage = '';
    this.tutorialErrorMessage = '';

    this.dropdownSettings = {
      singleSelection: false,
      allowSearchFilter: true
    };
  }

  addTutorial(): void {
    console.log(this.selectedItems);
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

    this.tutorial.keywords = this.keywords.join(' ');
    this.tutorialService.addTutorial(this.tutorial, this.selectedItems).subscribe(tutorial => {
      this.tutorial = tutorial;
      this.saved = true;
      this.incStep();
    }, err => {
      alert(err.error.message);
    });
  }

  removeKeyword(keyword: any): void {
    debugger;
    let index = this.keywords.indexOf(keyword);
    if (index >= 0) {
      this.keywords.splice(index, 1);
      if (this.inputKeyword.hidden === true) {
        this.inputKeyword.hidden = false;
      }
    }
  }

  addKeyword(event: MatChipInputEvent): void {
    this.inputKeyword = event.input;
    let value = event.value;
    console.log('keywords: ' + this.keywords);
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

  addMaterial(): void {
    console.log('add meterial \n');
    this.file = (<HTMLInputElement>document.getElementById('file')).files[0];
    if (this.material.title.length < 5) {
      this.materialErrorMessage += 'Title is too short!\n';
    }
    if (this.material.description.length > 500) {
      this.materialErrorMessage += 'Description must contain at most 500 characters!\n';
    }
    if (this.material.materialType === null) {
      this.materialErrorMessage += 'Material type not chosen\n';
    }
    if (this.material.materialType !== undefined && this.material.materialType.toString() === 'LINK') {
      if (this.material.link === undefined || this.material.link.length < 5) {
        this.materialErrorMessage += 'Link must have at least 6 characters\n';
      }
    }
    if (this.material.materialType !== undefined && this.material.materialType.toString() === 'FILE' && this.file == null) {
      this.materialErrorMessage += 'File not uploaded\n';
    }

    if (this.materialErrorMessage !== '') {
      this.snackBarMessagePopup(this.materialErrorMessage);
      this.materialErrorMessage = '';
      return;
    }

    try {
      this.materialService.addMaterialToTutorial(this.material, this.file, this.tutorial.idTutorial);
      // this.incStep();
      this.tutorialService.getMaterialsForTutorialId(this.tutorial.idTutorial).subscribe(
        materials => {
          this.materialsForCurrentTutorial = materials;
          console.log(this.materialsForCurrentTutorial);
          this.snackBarMessagePopup('Material added successfully');
        });
    } catch (err) {
      alert(err.error.message);
    }

    // this.materialsForCurrentTutorial = new Array<TutorialMaterialDTO>();

    // this.file = null;
    // var fileInput = <HTMLInputElement>document.getElementById('file');
    // fileInput.innerHTML = null;
    // this.material = new TutorialMaterialDTO();
    // this.material.materialType = this.materialTypeLink;

  }

  printMaterialType(): void {
    var e = this.document.getElementById('selectedMaterialType');
    this.selectedMaterialType = e.options[e.selectedIndex].value;
    if (this.selectedMaterialType === 'FILE') {
      var addFileDiv = this.document.getElementById('addFile');
      addFileDiv.style.visibility = 'visible';
      var linkFile = this.document.getElementById('addLink');
      linkFile.style.visibility = 'hidden';
    }
    if (this.selectedMaterialType === 'LINK') {
      var addFileDiv = this.document.getElementById('addFile');
      addFileDiv.style.visibility = 'hidden';
      var linkFile = this.document.getElementById('addLink');
      linkFile.style.visibility = 'visible';
    }
  }

  getCurrentStep(): string {
    return this.currentStep;
  }

  incStep() {
    switch (this.currentStep) {
      case ('one'):
        this.currentStep = 'two';
        this.saved = false;
        break;
      case ('two'):
        this.currentStep = 'three';
        break;
    }
  }

  redirectToTutorialsPage() {
    location.replace(this.rootConst.FRONT_TUTORIALS_PAGE);
  }

  addNewMaterial() {
    this.currentStep = 'two';
    this.material.materialType = null;
  }

  uploadFile() {
    this.file = (<HTMLInputElement>document.getElementById('file')).files[0];
  }

  downloadFile(material: TutorialMaterialDTO): void {
    this.tutorialService.getFileWithId(material.idTutorialMaterial);
  }

  snackBarMessagePopup(message: string) {
    console.log('tralalalaaaa\n');
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }

  addNewEmptyMaterial() {
    this.materialsForCurrentTutorial.push(new TutorialMaterialDTO());
  }
}
