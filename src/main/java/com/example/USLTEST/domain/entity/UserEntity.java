package com.example.USLTEST.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "db_user")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password cannot be blank")
    @JsonIgnore
    private String password;

    @JsonIgnore
    @NotNull(message = "Confirm Password can't be null")
    @NotBlank(message = "Confirm password is required")
    @Column(name = "confirm_password", nullable = false)
    private String confirmPassword;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TicketEntity> tickets;

    @OneToOne
    @JoinColumn(name = "bus_id")
    private BusEntity bus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
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
