import {Component, OnInit} from '@angular/core';
import {UserDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-users-in-department-list',
  templateUrl: './users-in-department-list.component.html',
  styleUrls: ['./users-in-department-list.component.css']
})
export class UsersInDepartmentListComponent implements OnInit {

  public employeesInDepartment: UserDTO[];
  private department = '';


  constructor(private userService: UserService) {
  }

  ngOnInit() {

    let userLogged: string = localStorage.getItem('userLogged');

    this.userService.getDepartmentForUsername(userLogged).subscribe(department => {
      this.department = department;
      console.log(this.department);
    });

    this.employeesInDepartment = [];
    this.userService.getUsersInDepartment(this.department).subscribe(employeesInDepartment => {
      this.employeesInDepartment = employeesInDepartment;
      console.log(this.employeesInDepartment);
    });

  }

}
