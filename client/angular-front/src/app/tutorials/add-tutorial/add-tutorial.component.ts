import {Component, OnInit, Inject} from '@angular/core';
import {ENTER, COMMA, SPACE} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material';
import {DOCUMENT} from '@angular/common';
import {IMultiSelectOption} from 'angular-2-dropdown-multiselect';
import {Location} from '@angular/common';

import {UserDTO} from '../../domain/user';
import {Material} from '../../domain/material';
import {MaterialType} from '../../domain/materialType';
import {TutorialMaterialDTO} from '../../domain/tutorialMaterial';
import {TutorialDTO} from '../../domain/tutorial';

import {UserService} from '../../service/user.service';
import {TutorialService} from '../../service/tutorial.service';
import {MaterialService} from '../../service/material.service';

import {Router} from '@angular/router';
import {RootConst} from '../../util/RootConst';

import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/Rx';

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
  public materialErrorMessage: String;

  public usersOptions: string[];
  public contactPersonsUsername: string[];

  separatorKeysCodes = [ENTER, COMMA, SPACE];

  private currentStep: string;
  private materialsForCurrentTutorial: Array<TutorialMaterialDTO>;

  constructor(private location: Location, private tutorialService: TutorialService, private userService: UserService,
              private materialService: MaterialService, @Inject(DOCUMENT) private document: any) {
    this.keywords = [];
    this.contactPersonsUsername = [];
    this.rootConst = new RootConst();

    this.tutorial = new TutorialDTO();
    this.material = new TutorialMaterialDTO();
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

    this.dropdownSettings = {
      singleSelection: false,
      allowSearchFilter: true
    };
  }

  addTutorial(): void {
    console.log(this.selectedItems);
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
    this.file = (<HTMLInputElement>document.getElementById('file')).files[0];
    if (this.material.title == null || this.material.title.length < 5) {
      this.materialErrorMessage += 'Title too short!\n';
    }
    if (this.material.description == null || this.material.description.length < 20) {
      this.materialErrorMessage += 'Description too short!\n';
    }
    if (this.material.materialType === null) {
      this.materialErrorMessage += 'Material type not chose\n';
    }
    if (this.material.materialType.toString() === 'LINK') {
      if (this.material.link === undefined || this.material.link.length < 5) {
        this.materialErrorMessage += 'Link must have at least 6 characters\n';
      }
    }
    if (this.material.materialType.toString() === 'FILE' && this.file == null) {
      this.materialErrorMessage += 'File not uploaded\n';
    }

    console.log('material from component ' + this.materialErrorMessage + '\n');

    if (this.materialErrorMessage !== '') {
      this.materialErrorMessage = '';
      return;
    }

    console.log('add material to tutorial: ' + this.tutorial.idTutorial);
    try {
      this.materialService.addMaterialToTutorial(this.material, this.file, this.tutorial.idTutorial);
      this.incStep();
    } catch (err) {
      alert(err.error.message);
    }
    this.materialsForCurrentTutorial = new Array<TutorialMaterialDTO>();

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
}
