package ipt.lei.dam.ncrapi.utils.enums;

import lombok.Getter;

public enum UserRoles {

    STUDENT(7, "student"),
    MEMBER(8, "member"),
    ADMIN(9,"admin");

    @Getter
    private int roleID;
    @Getter
    private String role;

    UserRoles(int id, String role) {
        this.role = role;
    }
}
