package ru.sber.alex.minibank.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="login")
    private String login;
    @Column(name="pass")
    private String pass;
    @Column(name="email")
    private String email;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @Column(name="second_name")
    private String secondName;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private List<AccountEntity> accounts;

    @ManyToMany
    @JoinTable (name="logs",
            joinColumns=@JoinColumn (name="clients_id"),
            inverseJoinColumns=@JoinColumn(name="operation_id"))
    private List<OperationEntity> operations;

    public ClientEntity() {
    }

    public ClientEntity(int id, String login, String pass, String email, String name, String surname, String secondName) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<AccountEntity> getAccounts() {
        return accounts;
    }

    public List<OperationEntity> getOperations() {
        return operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return id == that.id &&
                Objects.equals(login, that.login) &&
                Objects.equals(pass, that.pass) &&
                Objects.equals(email, that.email) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, pass, email, name, surname, secondName);
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
