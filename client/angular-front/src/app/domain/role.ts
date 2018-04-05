export enum RoleType {
  'ROLE_ADMIN' = 'Admin',
  'ROLE_READER' = 'Reader',
  'ROLE_ABTEILUNGSLEITER' = 'Abteilungsleiter',
  'ROLE_HR' = 'Human Resources',
  'ROLE_BUDDY' = 'Buddy',
  'ROLE_USER' = 'Employee'
}

export class RoleDTO {
  idRole: number;
  roleType: RoleType;
}
