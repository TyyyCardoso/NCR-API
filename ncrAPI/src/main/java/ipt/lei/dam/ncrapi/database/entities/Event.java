package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Events")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventID")
    private Integer id;

    private String name;

    private String description;

    @Column(name = "Date")
    private Date date;

    private String location;

    private Boolean transport;

    private Date createdAt;

    private Date updatedAt;
    
    private String image;

    // Getters and Setters
}
