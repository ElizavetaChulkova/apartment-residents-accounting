package ru.chulkova.residents.accounting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "users_unique_name_idx")})
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends AbstractBaseEntity implements UserDetails {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Apartment> property;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Apartment residence;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return name;
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