import {User} from './user';
import {Material} from './tutorialMaterial';

export class Tutorial {
  idTutorial: number;
  titleTutorial: string;
  overview: string;
  keywords: string;
  contactPersons: User[];
  materials: Material[];
}

