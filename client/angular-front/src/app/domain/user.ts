import {Role, RoleType} from './role';
import {DepartmentType} from './departmentType';
import {Location} from './location';
import {Department} from './Department';

export class User {
  idUser: number;
  email: string;
  msgMail: string;
  name: string;
  username: string;
  password: string;
  startDate: Date;
  department: Department;
  buddyUser: User;
  team: string;
  location: Location;
  floor: string;
  project: string;
  startDateString: String;
  role: RoleType;


}

// export class UserInformationDTO {
//   idUserInformation: number;
//   team: string;
//   location: LocationDTO;
//   floor: string;
//   project: string;
//   department: DepartmentType;
//   buddyUser: User;
//   userAccount: User;
//   startDate: Date;
//   startDateString: String;
// }

export class UserDetailsToExport {
  name: string;
  email: string;
  username: string;
  startDate: string;
  team: string;
  project: string;
  department:string
}

export class LeaveCheckList {
  userAccount: User;
  inventoryObjects: boolean;
  resignationForm: boolean;
  cards: boolean;
}

export class ContactPersonDto {
  username: string;
  msgMail: string;
}

// export class UserDetails {
//   email: string;
//   msgMail: string;
//   name: string;
//   username: string;
// }


// export class UserInformationsDetails {
//   team: string;
//   location: LocationDTO;
//   floor: string;
//   project: string;
//   department: DepartmentType;
//   buddyUser: User;
//   startDateString: Date;
// }
