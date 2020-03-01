/*
 Stwórz klasę ​ StudentEntity ​ (z polami: ​ id, name,
    yearOfStudy ​ + adnotacje)​ ​ obok klasy ​ CoachEntity.
 */

package pl.sda.jpa.starter.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "school")

    public class StudentEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(name = "name")
        private String name;

        @Column(name = "yearOfStudy")
        private String yearOfStudy;

        @Column(name = "adnotations")
        private String adnotations;

        StudentEntity() {}

        public StudentEntity(String name, String yearOfStudy, String adnotations) {
            this.name = name;
            this.yearOfStudy = yearOfStudy;
            this.adnotations = adnotations;
        }

        public int getId() {
            return id;
        }

    public String getName() {
        return name;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public String getAdnotations() {
        return adnotations;
    }

    @Override
        public String toString() {
            return "StudentEntity{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", yearOfStudy=" + yearOfStudy +
                    ", adnotations='" + adnotations + '\'' +
                    '}';
        }
    }

