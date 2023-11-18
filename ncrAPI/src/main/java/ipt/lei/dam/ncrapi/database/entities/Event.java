package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Events")
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

    // Getters and Setters
}
