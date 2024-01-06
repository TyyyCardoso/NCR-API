package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Docs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Docs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocID")
    private Integer id;

    private String docName;

    private String docDescription;

    private int docType;

    private String docReference;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
