package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.Objects;


@Entity

@Table(name = "Student")
@SecondaryTable(name="ADDRESS")
public class Student extends Person {
  /*  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //This line is optional
    private int id;*/
    @Column(unique=true, nullable=false)
    private String enrollmentID;
    /*private String name;*/
    @Column(name = "NUM_COURSES")
    private Integer numberOfCourses;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="TUTOR_FK")
    private Tutor tutor;

    @Embedded
    private Address address;


    public Student(String name, String enrollmentID) {
        super(name);
        this.numberOfCourses = 10;
        this.enrollmentID = enrollmentID;
    }
    public Student(String name, String enrollmentID, String street, String city,
                   String zipCode){
        super(name);
        this.enrollmentID= enrollmentID;
        this.address = new Address(street,city,zipCode);
    }
    public Student() {

    }

    @Override
    public void getReport() {
        System.out.println("Report for student: " + this.getName());
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }


    /*public int getId() {
        return id;
    }*/



    @Override
    public String toString() {
        return this.getName() + " lives at: " + address;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return enrollmentID.equals(student.enrollmentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentID);
    }
}
