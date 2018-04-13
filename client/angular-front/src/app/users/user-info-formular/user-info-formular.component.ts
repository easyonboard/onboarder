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
  show = true;
  users$: Observable<UserDTO[]>;
  private searchTerms = new Subject<string>();

  constructor(private userInformationService: UserInformationService, private userService: UserService,
              @Inject(MAT_DIALOG_DATA) public userInformation: UserInformationDTO) {
  }

  search(term: string): void {
    this.searchTerms.next(term);
    this.show = true;
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
    );
  }

  updateUserInformation(team: string, building: string,
                        floor: string, project: string,
                        mailSent: boolean): void {

    team.trim();
    building.trim();
    floor.trim();

    let idUserInformation = this.userInformation.idUserInformation;
    let buddyUser = this.selectedBuddy;
    let userAccount = this.userInformation.userAccount;
    let startDate: Date;
    let userInfo: UserInformationDTO = {idUserInformation, team, building, floor, project, buddyUser, userAccount, mailSent, startDate};
    this.userInformationService.updateUserInformation(userInfo).subscribe();
  }

}
