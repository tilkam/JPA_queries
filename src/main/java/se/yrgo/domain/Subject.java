package se.yrgo.domain;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    private String subjectName;
    private int numberOfSemesters;

    @ManyToMany
    private Set<Tutor> tutors;

    public Subject(){

    }

    public Subject(String subjectName, int numberOfSemesters) {
        this.subjectName = subjectName;
        this.numberOfSemesters=numberOfSemesters;
        this.tutors = new HashSet<Tutor>();
    }
    public void addTutorToSubject(Tutor tutor) {
        this.tutors.add(tutor);
        tutor.getSubjects().add(this);
    }
    public Set<Tutor> getTutors() {
        return this.tutors;
    }

    public String subjectName() {
        return subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public int getNumberOfSemesters() {
        return numberOfSemesters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return id == subject.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
