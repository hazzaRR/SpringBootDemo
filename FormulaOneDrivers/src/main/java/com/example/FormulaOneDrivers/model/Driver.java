package com.example.FormulaOneDrivers.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Entity(name = "Driver")
@Table(name = "Driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname")
    private String firstname;
    @Column(name = "surname")
    private String surname;

    @Column(name = "racing_number", unique = true, nullable = false)
    private int racingNumber;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Transient
    private int age;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Constructor team;

    protected Driver() {
    }

    public Driver(String firstname, String surname, int racingNumber, LocalDate dob, Constructor team) {
        this.firstname = firstname;
        this.surname = surname;
        this.racingNumber = racingNumber;
        this.dob = dob;
        this.team = team;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.getFirstname() + " " + this.getSurname();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setRacingNumber(int racingNumber) {
        this.racingNumber = racingNumber;
    }

    public int getRacingNumber() {
        return racingNumber;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }


    public Constructor getTeam() {
        return team;
    }

    public void setTeam(Constructor team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                ", team=" + team +
                '}';
    }
}
