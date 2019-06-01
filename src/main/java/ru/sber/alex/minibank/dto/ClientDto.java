package ru.sber.alex.minibank.dto;

import java.util.Objects;

public class ClientDto {

    private int id;
    private String login;
    private String pass;
    private String email;
    private String name;
    private String surname;
    private String secondName;

    public ClientDto() {
    }

    public ClientDto(String login, String pass, String email, String name, String surname, String secondName) {
        this.login = login;
        this.pass = pass;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(login, clientDto.login) &&
                Objects.equals(pass, clientDto.pass) &&
                Objects.equals(email, clientDto.email) &&
                Objects.equals(name, clientDto.name) &&
                Objects.equals(surname, clientDto.surname) &&
                Objects.equals(secondName, clientDto.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, pass, email, name, surname, secondName);
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
