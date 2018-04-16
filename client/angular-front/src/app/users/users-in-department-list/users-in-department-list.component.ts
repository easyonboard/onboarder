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
  panelOpenState: boolean = false;


  constructor(private userService: UserService) {
  }

  ngOnInit() {

    let userLogged: string = localStorage.getItem('userLogged');
    console.log('--->' + userLogged);
    debugger;
    // console.log('inainte de get department');
    // this.userService.getDepartmentForUsername(userLogged).subscribe(value => {
    //   console.log('-------------->' + value);
    //   this.department = value;
    //   console.log(this.department);
    // });
    // console.log('---->' + this.department);
    // console.log('dupa get department');
    this.employeesInDepartment = [];
    this.userService.getUsersInDepartment(userLogged).subscribe(employeesInDepartment => {
      this.employeesInDepartment = employeesInDepartment;
      console.log(this.employeesInDepartment);
    });

  }

}
