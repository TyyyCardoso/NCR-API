package ipt.lei.dam.ncrapi.utils.enums;

import lombok.Getter;

public enum UserRoles {

    STUDENT(7, "ESTUDANTE"),
    MEMBER(8, "MEMBRO"),
    ADMIN(9,"ADMINISTRADOR");

    @Getter
    private int roleID;
    @Getter
    private String role;

    UserRoles(int id, String role) {
        this.roleID = id;
        this.role = role;
    }

    public static String getRoleByID(int id) {
        for (UserRoles role : UserRoles.values()) {
            if (role.getRoleID() == id) {
                return role.getRole();
            }
        }
        return null; // or throw an exception if an unknown ID is not expected
    }

}
