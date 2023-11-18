package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Lectures")
public class Lecture {
    @Id
    private Integer eventId;

    private Date createdAt;

    private Date updatedAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "EventID")
    private Event event;

    // Getters and Setters
}
