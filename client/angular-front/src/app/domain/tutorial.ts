import {UserDTO, ContactPersonDto} from './user';
import {TutorialMaterialDTO} from './tutorialMaterial';

export class TutorialDTO {
  idTutorial: number;
  titleTutorial: string;
  overview: string;
  keywords: string;
  contactPersons: UserDTO[];
  tutorialMaterials: TutorialMaterialDTO[];
  draft: boolean;
}

export class TutorialDto {
  titleTutorial: string;
  overview: string;
  keywords: string;
  contactPersons: ContactPersonDto[];
}
