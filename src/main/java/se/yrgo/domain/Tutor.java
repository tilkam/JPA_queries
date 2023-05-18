package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity

public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String tutorId;
    private String name;
    private int salary;
    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;
    @OneToMany(cascade = {CascadeType.PERSIST})
    @MapKey(name = "enrollmentID")
    @JoinColumn(name = "TUTOR_FK")
    private Set<Student> teachingGroup;

    public Tutor() {

    }

    public Tutor(String tutorId, String name, int salary) {
        this.name = name;
        this.tutorId = tutorId;
        this.salary = salary;
        this.teachingGroup = new HashSet<Student>();
        this.subjectsToTeach = new HashSet<Subject>();
    }

    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);

    }

    public void addSubjectsToTeach(Subject subject) {
        subject.getTutors().add(this);
        this.subjectsToTeach.add(subject);

    }

    public Set<Subject> getSubjects() {
        return this.subjectsToTeach;
    }


    public Set<Student> getTeachingGroup() {
        Set<Student> unmodifiable =
                Collections.unmodifiableSet(this.teachingGroup);
        return unmodifiable;
    }

    public void createStudentAndAddtoTeachingGroup(String studentName,
                                                   String enrollmentID, String street, String city,
                                                   String zipcode) {
        Student student = new Student(studentName, enrollmentID,
                street, city, zipcode);
        this.addStudentToTeachingGroup(student);
    }


    public String getTutorId() {
        return tutorId;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "tutorId='" + tutorId + '\'' +
                ", name='" + this.getName() + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tutor)) return false;
        Tutor tutor = (Tutor) o;
        return tutorId.equals(tutor.tutorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorId);
    }
}
