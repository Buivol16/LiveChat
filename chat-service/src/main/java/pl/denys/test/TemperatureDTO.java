package pl.denys.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TemperatureDTO {
    private LocalDateTime time;
    /**
     * {@code temperature}
     * value must be provided in Celsius **/
    private Float temperature;
}
