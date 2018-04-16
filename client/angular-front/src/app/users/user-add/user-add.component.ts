import {Component, OnInit} from '@angular/core';
import {RoleDTO, RoleType} from '../../domain/role';
import {UserDTO, UserInformationDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {UserInformationService} from '../../service/user-information.service';
import {MatSelectChange} from '@angular/material';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {
  public firstName: string;
  public lastName: string;
  public roleType = RoleType;
  public selectedRole: RoleType;

  public user = new UserDTO();
  public userInfo = new UserInformationDTO();
  public role: RoleDTO = new RoleDTO();

  public roles = Object.keys(RoleType);

  employees = [
    {value: 'employee-0', viewValue: 'ONE'},
    {value: 'employee-1', viewValue: 'TWO'},
    {value: 'employee-2', viewValue: 'DREI'}
  ];

  constructor(private userService: UserService, private userInformationService: UserInformationService) {
  }

  ngOnInit(): void {

  }

  addUser(): void {
    this.user.username = this.firstName + this.lastName;
    this.user.password = 'testPassw';
    this.user.name = this.firstName + ' ' + this.lastName;

    this.userService.addUser(this.user, this.selectedRole).subscribe();
    this.userInformationService.addUserInformation(this.userInfo).subscribe();
  }

  selectValue(event: MatSelectChange) {
    this.selectedRole = event.value;
  }
}
