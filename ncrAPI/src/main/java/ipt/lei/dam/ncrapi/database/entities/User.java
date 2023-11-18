package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "TypeID")
    private UserType userType;

    private String name;

    private String email;

    private String password;

    @Column(name = "RegistrationDate")
    private Date registrationDate;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "UpdatedAt")
    private Date updatedAt;

    // Getters and Setters
}
