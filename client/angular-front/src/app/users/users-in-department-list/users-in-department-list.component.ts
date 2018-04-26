import {Component, OnInit} from '@angular/core';
import {UserDetailsToExport, UserDTO, UserInformationDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {ExcelService} from "../../service/excel.service";
import {UserInformationService} from "../../service/user-information.service";

@Component({
  selector: 'app-users-in-department-list',
  templateUrl: './users-in-department-list.component.html',
  styleUrls: ['./users-in-department-list.component.css']
})
export class UsersInDepartmentListComponent implements OnInit {

  public employeesInDepartment: UserDTO[];
  private department = '';
  panelOpenState: boolean = false;
  userDetails: UserDetailsToExport[] = [];
  userDetail: UserDetailsToExport;
  userInformation: UserInformationDTO;

  constructor(private userService: UserService, private excelService: ExcelService, private userInformationService: UserInformationService) {
    this.excelService = excelService;
  }

  ngOnInit() {

    let userLogged: string = localStorage.getItem('userLogged');

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
      this.getAllInformation();
      this.getUserTeamAndStartDate();

    });


  }

  export() {

    this.excelService.exportAsExcelFile(this.userDetails, 'Users');

  }

  getAllInformation() {
    this.employeesInDepartment.forEach(user => {
      this.userDetail = new UserDetailsToExport();
      this.userDetail.name = user.name;
      this.userDetail.email = user.email;
      this.userDetail.username = user.username;
      this.userDetails.push(this.userDetail);
    });
  }

  getUserTeamAndStartDate() {
    this.userDetails.forEach(userInfo => {
      this.userInformationService.getUserInformation(userInfo.username).subscribe(user => {
        userInfo.team = user.team;
        const myDate = new Date(user.startDate).toDateString();
        userInfo.startDate = myDate;
      });
    });


  }

}
