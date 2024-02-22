package uz.pdp.config.sercurity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.domain.Role;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UserContext {
    private Long id;
    private String username;
    private List<Role> roles;
}
