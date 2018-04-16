import {RoleDTO} from './role';

export class UserDTO {
  idUser: number;
  email: string;
  name: string;
  username: string;
  password: string;
  role:RoleDTO;
}

export class UserInformationDTO {
  idUserInformation: number;
  team: string;
  building: string;
  floor: string;
  project: string;
  buddyUser: UserDTO;
  userAccount: UserDTO;
  mailSent: boolean;
  startDate: Date;
  department: string;
}
