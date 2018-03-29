import {UserDTO} from './user';

export class UserInformationDTO {
  id: number;
  team: string;
  building: string;
  store: string;
  buddy: UserDTO;

}
