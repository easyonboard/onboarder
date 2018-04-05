import {RoleDTO} from './role';

export class UserDTO {
  idUser: number;
  email: string;
  name: string;
  username: string;
  password: string;
}

export class UserInformationDTO {
  team: string;
  building: string;
  store: string;
  project: string;
  buddyUser: UserDTO;
  userAccount: UserDTO;
  mailSent: boolean;
}
