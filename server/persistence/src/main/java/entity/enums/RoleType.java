package entity.enums;

public enum RoleType {
    ROLE_ADMIN(1),
    ROLE_READER(2),
    ROLE_ABTEILUNGSLEITER(3),
    ROLE_HR(4),
    ROLE_BUDDY(5),
    ROLE_USER(6);

    private int roleTypeId;

    RoleType(int roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public int getRoleTypeId() {
        return roleTypeId;
    }

}
