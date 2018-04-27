import {Component, OnInit} from '@angular/core';
import {UserDetailsToExport, UserDTO, UserInformationDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {ExcelService} from '../../service/excel.service';
import {UserInformationService} from '../../service/user-information.service';

@Component({
  selector: 'app-users-in-department-list',
  templateUrl: './users-in-department-list.component.html',
  styleUrls: ['./users-in-department-list.component.css']
})
export class UsersInDepartmentListComponent implements OnInit {

  public employeesInDepartment: UserDTO[];
  private department = '';
  panelOpenState = false;
  allUserDetails: UserDetailsToExport[] = [];
  userDetails: UserDetailsToExport[] = [];
  userDetail: UserDetailsToExport;
  userInformation: UserInformationDTO;

  public searchValue = '';

  constructor(private userService: UserService, private excelService: ExcelService,
              private userInformationService: UserInformationService) {
    this.excelService = excelService;
  }

  ngOnInit() {

    const userLogged: string = localStorage.getItem('userLogged');

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
      this.getAllInformation();
      this.getUserTeamAndStartDate();
      this.userDetails = this.allUserDetails;
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
      this.allUserDetails.push(this.userDetail);
    });
  }

  getUserTeamAndStartDate() {
    this.allUserDetails.forEach(userInfo => {
      this.userInformationService.getUserInformation(userInfo.username).subscribe(user => {
        userInfo.team = user.team;
        const myDate = new Date(user.startDate).toDateString();
        userInfo.startDate = myDate;
        userInfo.project = user.project;
      });
    });


  }

  searchByName() {
    if (this.searchValue !== '' && this.searchValue !== null) {
      this.userDetails = this.allUserDetails.filter(user => user.name.toLowerCase().includes(this.searchValue.toLowerCase()));
    } else {
      this.userDetails = this.allUserDetails;
    }
  }
}
