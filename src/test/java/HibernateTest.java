
import jakarta.persistence.*;
import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;

public class HibernateTest {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // TASK 1: QUERY USING MEMBER OF
        //Write a query to get the name of all students whose tutor can teach science.
        Subject science = em.find(Subject.class, 2);
        TypedQuery<Student> query = em.createQuery("select student from Student student, Tutor tutor where student member of tutor.teachingGroup and :subject member of tutor.subjectsToTeach", Student.class);
        query.setParameter("subject", science);
        List<Student> studentsInSubject = query.getResultList();
        for (Student student : studentsInSubject) {
            System.out.println(student);
        }

        //TASK 2: QUERY USING JOIN
        //Write a query to retrieve the name of all the students and the name of their tutor.
        Query queryJoin = em.createQuery("select tutor, student from Tutor as tutor inner join tutor.teachingGroup as student");
        List<Object[]> results = queryJoin.getResultList();
        for (Object[] item : results) {
            System.out.println(item[0] + "-------------------- " + item[1]);
        }

        //TASK 3: REPORT QUERY- AGGREGATION
        //Use aggregation to get the average semester length for the subjects.
        double avgSemesterLength = (double) em.createQuery("select avg(subject.numberOfSemesters)from Subject subject").getSingleResult();
        System.out.println("The average semester length is: " + avgSemesterLength);

        //TASK 4: QUERY WITH AGGREGATION
        //Write a query that can return the max salary from the tutor table.

        int maxSalary = (int) em.createQuery("select max(tutor.salary) from Tutor tutor").getSingleResult();
        System.out.println("The highest salary: " + maxSalary);


        //TASK 5: NAMED QUERY
        //Write a named query that can return all the tutors that have a salary higher than 10000.
        List<Tutor> resultsSalary = em.createNamedQuery("searchSalaryHigherThanTenK").setParameter("salary", 10000).getResultList();
        System.out.println("TUTORS WITH SALARY HIGHER THAN 10000");
        for(Tutor tutor: resultsSalary) {
            System.out.println(tutor);
        }
        tx.commit();
        em.close();

    }

    public static void setUpData() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Subject mathematics = new Subject("Mathematics", 2);
        Subject science = new Subject("Science", 2);
        Subject programming = new Subject("Programming", 3);
        em.persist(mathematics);
        em.persist(science);
        em.persist(programming);

        Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
        t1.addSubjectsToTeach(mathematics);
        t1.addSubjectsToTeach(science);


        Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
        t2.addSubjectsToTeach(mathematics);
        t2.addSubjectsToTeach(science);


        Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
        t3.addSubjectsToTeach(programming);

        em.persist(t1);
        em.persist(t2);
        em.persist(t3);


        t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 2", "1212");
        t1.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
        t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");

        tx.commit();
        em.close();
    }


}
