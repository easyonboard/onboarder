import {Component, Inject, OnInit} from '@angular/core';
import {UserInformationService} from '../../service/user-information.service';
import {UserService} from '../../service/user.service';
import {Observable} from 'rxjs/Observable';
import {UserDTO, UserInformationDTO} from '../../domain/user';
import {Subject} from 'rxjs/Subject';

import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-user-info-formular',
  templateUrl: './user-info-formular.component.html',
  styleUrls: ['./user-info-formular.component.css']
})
export class UserInfoFormularComponent implements OnInit {

  selectedBuddy: UserDTO;
  users$: Observable<UserDTO[]>;
  private searchTerms = new Subject<string>();

  constructor(private userInformationService: UserInformationService, private userService: UserService,
              @Inject(MAT_DIALOG_DATA) public userAccount: UserDTO, @Inject(MAT_DIALOG_DATA) public idUserInformation: number) {
    console.log(this.idUserInformation);
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit() {
    this.selectedBuddy = new UserDTO();
    this.selectedBuddy.name = '';
    this.users$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.userService.searchUsers(term))
    )
    ;
  }

  addUserInformation(team: string, building: string,
                     store: string, project: string,
                     mailSent: boolean): void {

    team.trim();
    building.trim();
    store.trim();

    let buddyUser = this.selectedBuddy;
    let userAccount = this.userAccount;
    let idUserInformation: number;
    let userInfo: UserInformationDTO = {idUserInformation, team, building, store, project, buddyUser, userAccount, mailSent};
    this.userInformationService.addUserInformation(userInfo).subscribe();
  }

}
