package org.example.dataclasses;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "purchaseList")
@Data
public class PurchaseList{
    @EmbeddedId
    private PurchaseListKey id;

    @Column(name = "student_name", insertable = false, updatable = false)
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    private int price;

    @Column(name = "subscription_date", insertable = false, updatable = false)
    private LocalDate subscriptionDate;
}
