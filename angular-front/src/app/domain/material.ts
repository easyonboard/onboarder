import {MaterialType} from "./materialType";
import {Subject} from "./subject";

export  class material{
  idMaterial:number;

  materialType: MaterialType;

  link:string;

  fileMaterial:any;

  containedBySubjects:Subject[];

  title:string;

  description:string;

}
