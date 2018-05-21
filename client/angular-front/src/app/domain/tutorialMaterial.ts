import { TutorialDTO } from './tutorial';
import { MaterialType } from './materialType';

export  class TutorialMaterialDTO {
    idTutorialMaterial: number;
    materialType: MaterialType;
    link: string;
    fileMaterial: Uint8Array;
    title: string;
    description: string;
    tutorial: TutorialDTO;
}
