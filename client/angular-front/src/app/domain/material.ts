import {MaterialType} from './materialType';
import {Subject} from './subject';

export  class Material {
  idMaterial: number;
  materialType: MaterialType;
  link: string;
  fileMaterial: Uint8Array;
  containedBySubjects: Subject[];
  title: string;
  description: string;
}