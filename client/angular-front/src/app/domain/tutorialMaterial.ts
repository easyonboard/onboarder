import { Tutorial } from './tutorial';
import { MaterialType } from './materialType';

export  class Material {
    idMaterial: number;
    materialType: MaterialType;
    link: string;
    fileMaterial: Uint8Array;
    title: string;
    description: string;
    tutorial: Tutorial;
}
