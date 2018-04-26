import {RoleDTO} from './role';
import { DepartmentType } from './departmentType';

export class UserDTO {
  idUser: number;
  email: string;
  name: string;
  username: string;
  password: string;
  role: RoleDTO;
}

export class UserInformationDTO {
  idUserInformation: number;
  team: string;
  building: string;
  floor: string;
  project: string;
  department: DepartmentType;
  buddyUser: UserDTO;
  userAccount: UserDTO;
  mailSent: boolean;
  startDate: Date;
}

export class UserDetailsToExport{
  name: string;
  email: string;
  username: string;
  startDate: string;
  team: string;
  project: string;

}
