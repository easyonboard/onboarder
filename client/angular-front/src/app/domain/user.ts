import { RoleDTO } from './role';

export class UserDTO {
  idUser: number;
  email: string;
  name: string;
  username: string;
  password: string;
  role: RoleDTO;
}

export class UserInfoDTO {
  team: string;
  building: string;
  floor: string;
  buddy: string;
}
