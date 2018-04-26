import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSelectChange } from '@angular/material';

import { RoleDTO, RoleType } from '../../domain/role';
import { UserDTO, UserInformationDTO } from '../../domain/user';
import { UserService } from '../../service/user.service';
import { UserInformationService } from '../../service/user-information.service';

import { UserInfoFormularComponent } from '../user-info-formular/user-info-formular.component';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  public firstName: string;
  public lastName: string;
  public roleType = RoleType;
  public selectedRole: RoleType;

  public user = new UserDTO();
  public role: RoleDTO = new RoleDTO();

  public roles = Object.keys(RoleType);

  constructor(private userService: UserService, private userInformationService: UserInformationService) {
  }

  ngOnInit(): void {

  }

  addUser(): void {
    this.user.username = this.firstName + this.lastName;
    this.user.password = 'testPassw';
    this.user.name = this.firstName + ' ' + this.lastName;

    this.userService.addUser(this.user, this.selectedRole, this.childUserInfoFormularComponent.userInformation).subscribe();
  }

  selectRoleValue(event: MatSelectChange) {
    this.selectedRole = event.value;
  }
}
