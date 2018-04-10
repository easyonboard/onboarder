import {Component, OnInit} from '@angular/core';
import {RoleDTO, RoleType} from '../../domain/role';
import {UserDTO, UserInformationDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
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

  public startDate: Date = new Date;

  public user = new UserDTO();
  public userInfo = new UserInformationDTO();
  public role: RoleDTO = new RoleDTO();

  public roles = Object.keys(RoleType);

  employees = [
    {value: 'employee-0', viewValue: 'ONE'},
    {value: 'employee-1', viewValue: 'TWO'},
    {value: 'employee-2', viewValue: 'DREI'}
  ];

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {

  }

  addUser(): void {
    this.user.username = this.firstName + this.lastName;
    this.user.password = 'testPassw';
    this.user.name = this.firstName + ' ' + this.lastName;

    console.log('date picker ' + this.startDate);

    this.userService.addUser(this.user, this.selectedRole).subscribe();
    this.userService.addUserInfo(this.userInfo).subscribe();
  }

  selectValue(event: MatSelectChange) {
    this.selectedRole = event.value;
  }
}
