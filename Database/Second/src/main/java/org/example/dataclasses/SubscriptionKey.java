package org.example.dataclasses;


import jakarta.persistence.Column;
import lombok.Data;
import java.io.Serializable;


@Data
public class  SubscriptionKey implements Serializable {
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "course_id")
    private int courseId;

}
