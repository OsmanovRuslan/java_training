package org.example.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depths {
    @JsonProperty("station_name")
    private String stationName;
    @JsonProperty("depth")
    private String depth;
}
