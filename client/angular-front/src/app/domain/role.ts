export enum RoleType {
  'ROLE_ABTEILUNGSLEITER' = 'Abteilungsleiter',
  'ROLE_HR' = 'Human Resources',
  'ROLE_USER' = 'Employee'

}

export class RoleDTO {
  idRole: number;
  roleType: RoleType;
}
