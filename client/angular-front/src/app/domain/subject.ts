import {Course} from './course';

export class Subject {
  idSubject: number;
  containedByCourses: Course[];
  materials: any;
  description: string;
  name: string;
  numberOfDays: number;
}
