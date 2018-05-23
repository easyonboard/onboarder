import {RoleDTO} from './role';
import {DepartmentType} from './departmentType';
import {LocationDTO} from './location';

export class UserDTO {
  idUser: number;
  email: string;
  msgMail: string;
  name: string;
  username: string;
  password: string;
  role: RoleDTO;
}

export class UserInformationDTO {
  idUserInformation: number;
  team: string;
  location: LocationDTO;
  floor: string;
  project: string;
  department: DepartmentType;
  buddyUser: UserDTO;
  userAccount: UserDTO;
  mailSent: boolean;
  startDate: Date;
  startDateString: String;
}

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
