package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DidYouKnow")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DidYouKnow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FactID")
    private Integer id;

    private String title;

    private String text;

    @Column(name = "Reference")
    private String references;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Getters and Setters
}
