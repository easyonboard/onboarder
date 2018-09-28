import {RoleDTO} from './role';
import {DepartmentType} from './departmentType';
import {LocationDTO} from './location';
import {Department} from './Department';

export class UserDTO {
  idUser: number;
  email: string;
  msgMail: string;
  name: string;
  username: string;
  password: string;
  role: RoleDTO;
  startDate: Date;
  department: Department;
  buddyUser: UserDTO;
  team: string;
  location: LocationDTO;
  floor: string;
  project: string;



}

// export class UserInformationDTO {
//   idUserInformation: number;
//   team: string;
//   location: LocationDTO;
//   floor: string;
//   project: string;
//   department: DepartmentType;
//   buddyUser: UserDTO;
//   userAccount: UserDTO;
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
}

export class LeaveCheckList {
  userAccount: UserDTO;
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
//   buddyUser: UserDTO;
//   startDateString: Date;
// }
