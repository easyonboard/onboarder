import { Component, OnInit, ViewChild } from '@angular/core';

import { UserInformationService } from '../../service/user-information.service';
import { UserService } from '../../service/user.service';

import { UserInfoFormularComponent } from '../reusables/user-info-formular/user-info-formular.component';

@Component({
  selector: 'app-user-info-update',
  templateUrl: './user-info-update.component.html',
  styleUrls: ['./user-info-update.component.css']
})
export class UserInfoUpdateComponent implements OnInit {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  constructor(private userInformationService: UserInformationService, private userService: UserService) { }

  ngOnInit() {
  }

  updateUserInformation(): void {
      this.userInformationService.updateUserInformation(this.childUserInfoFormularComponent.userInformation).subscribe();
  }

}
