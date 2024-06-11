package org.example.dataclasses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationsWithoutDepth {
    private String name;
    private String line;
    private String date;
}