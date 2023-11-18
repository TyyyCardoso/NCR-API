package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "DidYouKnow")
public class DidYouKnow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FactID")
    private Integer id;

    private String title;

    private String text;

    @Column(name = "References")
    private String references;

    private Date createdAt;

    private Date updatedAt;

    // Getters and Setters
}
