package ru.kata.spring.boot_security.demo.model;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Роман Дамбуев:
 * Data использовать в entity плохая практика и так как используется set необходимо переопределить equals и hashCode
 * можно только по полю id, для изучения https://habr.com/ru/company/haulmont/blog/564682/
 *
 * Константин Харин: именно по этому @Data уже закомментирован
 */
@Entity
//@Data
@Setter
//@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "username",unique = true)
    @Size(min=3, message = "At least 3 symbols")
    private String username;
    @Column(name = "password")
    @Size(min=2, message = "At least 2 symbols")
    private String password;

    /**
     * Nikita Nesterenko: private List<Role> roles; - поменять на Set,
     *                      разобраться с аннотациями над полем,
     *                      у тебя куча их и не все нужны
     */
    /*
    @Fetch(FetchMode.JOIN)
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_roles"))
    */

    //@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    //@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))

    //@ManyToMany(cascade = {CascadeType.ALL}) // “Detached Entity Passed to Persist” Error
    //@ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    //@Enumerated(EnumType.STRING)
    //private Set<Role> roles;
    //@LazyCollection(LazyCollectionOption.TRUE)
    //private List<Role> roles;
    /**
     * Роман Дамбуев:
     * @Fetch(FetchMode.JOIN) по факту равен FetchType.EAGER и ухудшает скорость запроса,
     * лучше запрос с join fetch  https://www.baeldung.com/hibernate-fetchmode
     */
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany
    private Set<Role> roles;


    public Long getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public String getRolesAsString() {
        return roles.stream().map(Role::getName).collect(Collectors.joining(", "));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
