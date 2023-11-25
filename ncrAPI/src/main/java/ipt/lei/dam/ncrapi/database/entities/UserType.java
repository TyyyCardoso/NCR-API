package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UsersType")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeID")
    private Integer id;

    private String type;

    public UserType(String type){
        this.type = type;
    }
    // Getters and Setters
}
