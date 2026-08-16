package pl.denys.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.model.member.Member;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserJoinEvent implements Serializable {
    private String correlationId;
    private Member member;
    private List<String> userDestinations;
}