package ipt.lei.dam.ncrapi.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sentOTP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer otpId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "otpCode")
    private String otpCode;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "expiresAt")
    private LocalDateTime expiresAt;

    @Column(name = "isUsed")
    private Boolean isUsed;

    public SentOTP(Integer userID, String otpCode){
        this.userId = userID;
        this.otpCode = otpCode;

        LocalDateTime localDateTime = LocalDateTime.now();
        this.createdAt = localDateTime;
        this.expiresAt = localDateTime.plusMinutes(1);

        this.isUsed = false;

    }

}
