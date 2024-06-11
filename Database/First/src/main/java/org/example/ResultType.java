package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultType {
    private String courseName;
    private Long totalSub;
    private Integer totalMonth;
}
