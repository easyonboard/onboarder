import {Component, OnInit} from '@angular/core';
import {UserInformationService} from '../../service/user-information.service';
import {UserInformationDTO} from '../../domain/userinformation';
import {UserService} from '../../service/user.service';
import {Observable} from 'rxjs/Observable';
import {UserDTO} from '../../domain/user';
import {Subject} from 'rxjs/Subject';

import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-user-info-formular',
  templateUrl: './user-info-formular.component.html',
  styleUrls: ['./user-info-formular.component.css']
})
export class UserInfoFormularComponent implements OnInit {

  users$: Observable<UserDTO[]>;
  private searchTerms = new Subject<string>();

  constructor(private userInformationService: UserInformationService, private userService: UserService) {
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit() {
    console.log('Inainte de pipe');
    this.users$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.userService.getUserByUsername(term)),
    );
    console.log('muie');
    console.log(this.users$);
  }

  addUserInformation(team: string, building: string,
                     store: string,
                     buddy: string): void {

    team.trim();
    building.trim();
    store.trim();
    buddy.trim();
    this.userInformationService.addUserInformation({building, store, team} as UserInformationDTO).subscribe();
  }
}
