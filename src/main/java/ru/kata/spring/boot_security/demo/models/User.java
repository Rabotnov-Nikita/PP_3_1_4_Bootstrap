package ru.kata.spring.boot_security.demo.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Enter Email!")
    @Column(name = "email")
    private String username;

    @NotEmpty(message = "Enter Password!")
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @NotEmpty(message = "Set User Roles")
    @ManyToMany//(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();


    public User() {
    }


    public User(String username, String password, String firstName, String lastName, Integer age, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String listOfRoles() {

        StringBuilder listOfRoles = new StringBuilder();
        for (Role role: roles) {
            listOfRoles.append(role.getName()).append(" ");
        }
        return String.valueOf(listOfRoles);
    }
}
