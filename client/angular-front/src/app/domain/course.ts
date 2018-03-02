import {UserDTO} from './user';
import {Subject} from './subject';

export class Course {
  idCourse: number;
  titleCourse: string;
  overview: string;
  subjects: Subject;
  contactPersons: UserDTO;
  owners: UserDTO;
  keywords:string;
}
