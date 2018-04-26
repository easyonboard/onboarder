import {Component, OnInit, Input, Inject, ContentChild} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import {UserInformationService} from '../../service/user-information.service';
import {UserService} from '../../service/user.service';
import {UserDTO, UserInformationDTO} from '../../domain/user';
import {DepartmentType} from '../../domain/departmentType';

import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {MAT_DIALOG_DATA, MatSelectChange} from '@angular/material';

@Component({
  selector: 'app-user-info-formular',
  templateUrl: './user-info-formular.component.html',
  styleUrls: ['./user-info-formular.component.css']
})
export class UserInfoFormularComponent implements OnInit {
  public today: Date;
  @Input()
  show = true;
  @Input()
  userInformation = new UserInformationDTO();

  public departmentType = DepartmentType;
  public departments = Object.keys(DepartmentType);

  public users$: Observable<UserDTO[]>;
  private searchTerms = new Subject<string>();

  constructor(private userInformationService: UserInformationService, private userService: UserService) {
  }

  ngOnInit() {
    this.today = new Date(Date.now());
    if (this.userInformation.buddyUser === undefined) {
      this.userInformation.buddyUser = new UserDTO();
      this.userInformation.buddyUser.name = '';
    }

    this.users$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.userService.searchUsers(term))
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
    this.show = true;
  }

  selectValue(event: MatSelectChange) {
    this.userInformation.department = event.value;
  }

  getDate(): Date {
    return new Date(this.userInformation.startDate);
  }
}
