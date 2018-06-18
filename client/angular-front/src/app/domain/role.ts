export enum RoleType {
  'ROLE_ADMIN' = 'Admin',
  'ROLE_CONTENT_MANAGER' = 'Content Manager',
  'ROLE_ABTEILUNGSLEITER' = 'Abteilungsleiter',
  'ROLE_HR' = 'Human Resources',
  'ROLE_BUDDY' = 'Buddy',
  'ROLE_USER' = 'Employee'
}

export class RoleDTO {
  idRole: number;
  role: RoleType;
}
