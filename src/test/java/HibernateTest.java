
import jakarta.persistence.*;
import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;
import java.util.Set;

public class HibernateTest {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // TASK: QUERY USING MEMBER OF
        Subject science = em.find(Subject.class, 2);
        TypedQuery<Student> query = em.createQuery("select student from Student student, Tutor tutor where student member of tutor.teachingGroup and :subject member of tutor.subjectsToTeach",Student.class);
        query.setParameter("subject", science);
        List<Student> studentsInSubject = query.getResultList();
        for (Student student : studentsInSubject) {
            System.out.println(student);
        }

        //TASK: QUERY USING JOIN
        Query queryJoin = em.createQuery("from Tutor as tutor inner join tutor.teachingGroup as student");
        List<Object[]> results = queryJoin.getResultList();
        for (Object[] item : results) {
            System.out.println(item[0] + "-------------------- "+ item[1]);
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
