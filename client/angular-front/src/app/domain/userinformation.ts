import {UserDTO} from './user';

export class UserInformationDTO {
  idUserInformation: number;
  team: string;
  building: string;
  store: string;
  buddy: UserDTO;
  project: string;
  buddyUser: UserDTO;
  userAccount: UserDTO;
  mailSent: boolean;

}
