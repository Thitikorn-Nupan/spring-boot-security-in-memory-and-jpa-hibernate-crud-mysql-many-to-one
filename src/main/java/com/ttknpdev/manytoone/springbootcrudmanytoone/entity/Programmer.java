package com.ttknpdev.manytoone.springbootcrudmanytoone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//  Define Data Model for <<JPA One to Many mapping>>
@NoArgsConstructor
@Entity
@Table(name = "programmers")
@Setter
@Getter
public class Programmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pgm_id")
    private Long pgmId;
    @Column(name = "pgm_fullname")
    private String pgmFullname;
    @Column(name = "pgm_salary")
    private Double pgmSalary;
    @Column(name = "pgm_level")
    private Character pgmLevel ;
    @Column(name = "pgm_experience")
    private Short pgmExperience;
    @Column(name = "currentdatetime")
    private String currentDatetime = String.format(dateFormat().format(new Date()));

    public Programmer(String pgmFullname, Double pgmSalary, Character pgmLevel, Short pgmExperience) {
        this.pgmFullname = pgmFullname;
        this.pgmSalary = pgmSalary;
        this.pgmLevel = pgmLevel;
        this.pgmExperience = pgmExperience;
    }

    private DateFormat dateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat;
    }


    /*
    public static void main(String[] args) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));

    }*/
}
