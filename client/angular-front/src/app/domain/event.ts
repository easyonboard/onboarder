import { UserDTO } from './user';
import { TutorialMaterialDTO } from './tutorialMaterial';

export  class EventDTO {
  idEvent: number;
  titleEvent: string;
  overview: string;
  keywords: string;
  contactPersons: UserDTO[];
  enrolledPersons: UserDTO[];
  maxEnrolledUsers: number;
}
