import {Component, OnInit} from '@angular/core';
import {UserDetailsToExport, User} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {ExcelService} from '../../service/excel.service';
import {LocalStorageConst} from '../../util/LocalStorageConst';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-users-in-department-list',
  templateUrl: './users-in-department-list.component.html',
  styleUrls: ['./users-in-department-list.component.css']
})
export class UsersInDepartmentListComponent implements OnInit {
  public employeesInDepartment: User[];
  panelOpenState = false;
  allUserDetails: UserDetailsToExport[] = [];
  userDetails: UserDetailsToExport[] = [];
  userDetail: UserDetailsToExport;

  public searchValue = '';

  constructor(private userService: UserService,
              private excelService: ExcelService,
              public snackBar: MatSnackBar) {
    this.excelService = excelService;
  }

  ngOnInit() {

    const userLogged: string = localStorage.getItem(LocalStorageConst._USER_USERNAME);
    this.employeesInDepartment = [];
    this.userService.getUsersInDepartment(userLogged).subscribe(employeesInDepartment => {
      this.employeesInDepartment = employeesInDepartment;
      this.getAllInformation();
      this.userDetails = this.allUserDetails;
    }, err => {
      this.snackBarMessagePopup(err.error.message, 'Close');
    });


  }

  export() {
    this.excelService.exportAsExcelFile(this.userDetails, 'Users');
  }

  getAllInformation() {
    this.employeesInDepartment.forEach(user => {
      this.userDetail = new UserDetailsToExport();
      this.userDetail.firstName = user.firstName;
      this.userDetail.lastName = user.lastName;
      this.userDetail.email = user.email;
      this.userDetail.username = user.username;
      this.userDetail.department = user.department.departmentName;
      this.userDetail.project = user.project;
      this.allUserDetails.push(this.userDetail);
    });
  }

  searchByName() {
    if (this.searchValue !== '' && this.searchValue !== null) {
      this.userDetails = this.allUserDetails.filter(user => user.lastName.toLowerCase().includes(this.searchValue.toLowerCase()));
    } else {
      this.userDetails = this.allUserDetails;
    }
  }

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }
}
