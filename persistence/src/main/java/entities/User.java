package entities;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @NotNull
    @Column
    private String name;


    @NotNull
    @Min(6)
    private String username;

    @NotNull
    @Column(nullable = false)
    @Min(6)
    private String password;

    @ManyToOne
    private Role role;

    public int getIdUser() {
        return idUser;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
