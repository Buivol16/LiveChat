package pl.denys.notification_service.event.userjoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserJoinEvent implements Serializable, Cloneable{
    private String correlationId;
    private Member member;
    private List<String> userDestinations;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
