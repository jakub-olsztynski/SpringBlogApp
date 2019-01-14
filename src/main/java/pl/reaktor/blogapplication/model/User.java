package pl.reaktor.blogapplication.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_u;
    @Size(min = 3, max = 255, message = "POLE MUSI MIEĆ POMIEDZY 3 A 255 ZNAKOW")
    private String name;
    @Size(min = 3, max = 255, message = "POLE MUSI MIEĆ POMIEDZY 3 A 255 ZNAKOW")
    private String lastname;
    @Email(message = "NIEPOPRAWNY FORMAT ADRESU EMAIL")
    private String email;
    @Size(min = 3, max = 255, message = "HASŁO MUSI MIEĆ DŁUGOŚĆ POMIĘDZY 3 A 55 ZNAKÓW")
    private String password;
    private String permission;
    @Column(name = "date_registered")
    private Date register_date;
    private boolean active;

    public User() {
        this.permission = "ROLE_USER";
        this.register_date = new Date();
        this.active = true;
    }

    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.permission = "ROLE_USER";
        this.register_date = new Date();
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId_u() {
        return id_u;
    }

    public void setId_u(long id_u) {
        this.id_u = id_u;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_u=" + id_u +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", permission='" + permission + '\'' +
                ", register_date=" + register_date +
                '}';
    }
}

