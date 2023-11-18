package ipt.lei.dam.ncrapi.database.idclass;

import java.io.Serializable;
import java.util.Objects;

public class EventParticipantID implements Serializable {
    private Integer event;
    private Integer user;

    // Constructors, Getters, Setters, equals(), and hashCode()

    public EventParticipantID() {
    }

    public EventParticipantID(Integer event, Integer user) {
        this.event = event;
        this.user = user;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventParticipantID)) return false;
        EventParticipantID that = (EventParticipantID) o;
        return Objects.equals(event, that.event) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user);
    }
}
