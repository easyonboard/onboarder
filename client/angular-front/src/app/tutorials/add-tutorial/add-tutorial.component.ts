import { Component, OnInit } from '@angular/core';
import { ENTER, COMMA, SPACE } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material';
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';

import { TutorialService } from '../../service/tutorial.service';

import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/Rx';
import { TutorialDTO } from '../../domain/tutorial';

@Component({
  selector: 'app-add-tutorial',
  templateUrl: './add-tutorial.component.html',
  styleUrls: ['./add-tutorial.component.css']
})
export class AddTutorialComponent implements OnInit {

  public saved: Boolean;

  public tutorial: TutorialDTO;

  public keywords: String[];
  public inputKeyword: any;

  public usersOptions: IMultiSelectOption[];
  public ownersIds: number[];
  public contactPersonsIds: number[];

  separatorKeysCodes = [ENTER, COMMA, SPACE];

  constructor(private tutorialService: TutorialService) {
    this.keywords = [];
    this.tutorial = new TutorialDTO();

    this.saved = false;
  }

  ngOnInit() {
  }

  addTutorial(): void {
    this.tutorial.keywords = this.keywords.join(' ');
    this.tutorialService.addTutorial(this.tutorial, this.ownersIds, this.contactPersonsIds).subscribe(course => {
      this.saved = true;
    }, err => {
      alert(err.error.message);
    });
  }

  removeKeyword(keyword: any): void {
    debugger
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

}
