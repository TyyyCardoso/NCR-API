package ipt.lei.dam.ncrapi.database.entities;

import ipt.lei.dam.ncrapi.database.idclass.EventParticipantID;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "EventParticipants")
@IdClass(EventParticipantID.class)
public class EventParticipant {
    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    private Date participationDate;

    private Date createdAt;

    private Date updatedAt;

    // Getters and Setters
}
