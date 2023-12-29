package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StaffID")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID")
    private User user;

    private String cargo;

    private String descricao;

    private String telefone;

    private String codpais;

    private String status;

    @Column(name = "DataEntrada")
    private LocalDate dataEntrada;

    @Column(name = "CreatedAt")
    private LocalDate createdAt;

    @Column(name = "UpdatedAt")
    private LocalDate updatedAt;

}
