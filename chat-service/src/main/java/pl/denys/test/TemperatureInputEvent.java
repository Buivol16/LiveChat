package pl.denys.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TemperatureInputEvent {
    private String correlationId;
    private TemperatureDTO temperatureDTO;
    private String createdBy;
}
