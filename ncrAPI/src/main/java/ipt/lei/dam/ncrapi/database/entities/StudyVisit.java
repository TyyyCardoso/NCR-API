package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "StudyVisits")
public class StudyVisit {
    @Id
    private Integer eventId;

    private String partnerships;

    private Date createdAt;

    private Date updatedAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "EventID")
    private Event event;

    // Getters and Setters
}
