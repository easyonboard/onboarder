import {RoleType} from './role';
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
  mate: User;
  team: string;
  location: Location;
  floor: string;
  project: string;
  startDateString: String;
  role: RoleType;
}

export class UserDetailsToExport {
  name: string;
  email: string;
  username: string;
  startDate: string;
  team: string;
  project: string;
  department: string;
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
