package se.yrgo.domain;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)

public abstract class Person {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Person(){

    }
    public Person(String name){
        this.name = name;
    }


    public abstract void getReport();


    public String getName(){
        return this.name;
    }
}
