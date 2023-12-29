package ipt.lei.dam.ncrapi.database.entities;

import ipt.lei.dam.ncrapi.utils.enums.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer id;

    @Column(name = "TypeID")
    private String userType;

    private String name;

    private String email;

    private String password;

    @Column(name = "isValidated")
    private boolean isValidated;

    @Column(name = "RegistrationDate")
    private LocalDate registrationDate;

    @Column(name = "CreatedAt")
    private LocalDate createdAt;

    @Column(name = "UpdatedAt")
    private LocalDate updatedAt;

    private String image;

    private String about;

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = "8";
        this.isValidated = false;
        LocalDate localDate = LocalDate.now();
        this.registrationDate = localDate;
        this.createdAt = localDate;
        this.updatedAt = localDate;
        this.image = null;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userType.equals(UserRoles.MEMBER.getRoleID())) return List.of(new SimpleGrantedAuthority("ROLE_STUDENT"), new SimpleGrantedAuthority("ROLE_MEMBER"));
        if(this.userType.equals(UserRoles.ADMIN.getRoleID())) return List.of(new SimpleGrantedAuthority("ROLE_STUDENT"), new SimpleGrantedAuthority("ROLE_MEMBER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        return List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
