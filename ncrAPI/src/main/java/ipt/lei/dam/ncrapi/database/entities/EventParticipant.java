package ipt.lei.dam.ncrapi.database.entities;

import ipt.lei.dam.ncrapi.database.idclass.EventParticipantID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "EventParticipants")
@IdClass(EventParticipantID.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipant {
    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    private LocalDate participationDate;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private boolean canceled;

    // Getters and Setters
}
