package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "UsersType")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeID")
    private Integer id;

    private String type;

    // Getters and Setters
}
