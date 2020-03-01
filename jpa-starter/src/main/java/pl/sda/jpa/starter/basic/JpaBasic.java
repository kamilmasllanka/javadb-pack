/*
3.  Znajdź klasę ​ JpaBasic ​ uruchom i sprawdź czy dane zapisały się w bazie. Dodaj kilku
    dodatkowych trenerów do bazy danych. Usuń ostatniego trenera z listy za pomocą
    metody: ​ entityManager.remove(coachEntity)
 */

package pl.sda.jpa.starter.basic;

import pl.sda.jpa.starter.inheritance.Student;

import javax.persistence.*;
import java.util.List;

public class JpaBasic {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            /**
             * Tworzymy nowy obiekt EntityManagerFactory z konfiguracją Persistence Unit o nazwie: "pl.sda.jpa.starter"
             */
            entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
            /**
              * tworzymy nową instancję EntityManager
              */
            entityManager = entityManagerFactory.createEntityManager();  //pojedyńcze

            /**
             * Do pracy z bazą danych potrzebujemy transakcji
             */
            EntityTransaction transaction = entityManager.getTransaction();
            /**
             * Zaczynamy nową transakcję, każda operacja na bazie danych musi być "otoczona" transakcją
             */
            transaction.begin();

            /**
             * Zapisujemy encję w bazie danych
             */
//            CoachEntity coachEntity = new CoachEntity("Vlad Mihalcea");
//            CoachEntity coachEntity1 = new CoachEntity("Mihalcea Vlad");
//            CoachEntity coachEntity2 = new CoachEntity("Marcin Cool");
//     //       entityManager.remove(coachEntity);
//     //       entityManager.persist(coachEntity1);
//     //       entityManager.persist(coachEntity2);
//            CoachEntity ent = entityManager.find(CoachEntity.class, 3);
//            entityManager.remove(ent);

            StudentEntity studentEntity = new StudentEntity("Vlad Mihalcea","1990","qwert");
            StudentEntity studentEntity1 = new StudentEntity("Michała Tracz","2020","qwert");
            StudentEntity studentEntity2 = new StudentEntity("Arek Żurek","2019","qwert");
            StudentEntity studentEntity3 = new StudentEntity("Tomasz Sołtys","2020","qwert");
            entityManager.persist(studentEntity);
            entityManager.persist(studentEntity1);
            entityManager.persist(studentEntity2);
            entityManager.persist(studentEntity3);

////
//            "id=" + id +
//                    ", name='" + name + '\'' +
//                    ", yearOfStudy=" + yearOfStudy +
//                    ", adnotations='" + adnotations + '\'' +
//                    '}';

            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */
            TypedQuery<CoachEntity> query = entityManager.createQuery("from CoachEntity", CoachEntity.class);
            List<CoachEntity> coaches = query.getResultList();
            System.out.println("coaches = " + coaches);

            TypedQuery<StudentEntity> queryStudent = entityManager.createQuery("from StudentEntity", StudentEntity.class);
            List<StudentEntity> student = queryStudent.getResultList();
            System.out.println("school = " + student);

            /**
             * Kończymy (commitujemy) transakcję - wszystkie dane powinny być zapisane w bazie
             */
            transaction.commit();
        } finally {
            /**
             * Kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
             * Czemu EntityManager nie implementuje AutoClosable? https://github.com/javaee/jpa-spec/issues/77
             */

            if (entityManager != null) {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }
}
