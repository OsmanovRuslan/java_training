package org.example.dataclasses;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PurchaseListKey implements Serializable {
    @Column(name = "student_name")
    private String studentName;

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;
}
