package org.example;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="purchaselist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseList {
    @Id
    @Column(name="student_name")
    private String studentName;

    @Column(name="course_name")
    private String courseName;

    @Column(name="price")
    private Integer price;

    @Column(name="subscription_date")
    private Date subscriptionDate;
}
