package org.example.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stations {
    private String name;
    private String line;
    private String date;
    private double depth;
}
