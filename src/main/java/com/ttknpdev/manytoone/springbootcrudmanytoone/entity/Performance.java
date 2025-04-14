package com.ttknpdev.manytoone.springbootcrudmanytoone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
     With @OneToMany, we need to declare a collection inside parent class, we cannot limit the size of that collection
     With @ManyToOne, you can modify Repository:
* */
@Data
@NoArgsConstructor

@Entity
@Table(name = "performances")
public class Performance {
    @Id
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "project_charge")
    private Double projectCharge;
    @Column(name = "member_amount")
    private Short memberAmount;
    // By default, the @ManyToOne association uses FetchType.EAGER for fetch type but, it is bad for performance
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "project_pgm_id") //  @JoinColumn annotation to specify the foreign key column
    // @JsonIgnore is used to ignore the logical property used in serialization and deserialization.
    @JsonIgnore
    private Programmer programmer;

    public Performance(String projectName, Double projectCharge, Short memberAmount) {
        this.projectName = projectName;
        this.projectCharge = projectCharge;
        this.memberAmount = memberAmount;
    }
}
