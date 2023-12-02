package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import lombok.Setter;

@Entity
@Table(name = "Events")
@Getter
@Setter
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
    private LocalDateTime date;

    private String location;

    private Boolean transport;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private String image;

    // Getters and Setters

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", name=" + name + ", description=" 
                + description + ", date=" + date + ", location=" + location 
                + ", transport=" + transport + ", createdAt=" + createdAt 
                + ", updatedAt=" + updatedAt + ", image=" + ((image==null) ? "NoImage" : "YesImage") + "}";
    }
    
    
}
