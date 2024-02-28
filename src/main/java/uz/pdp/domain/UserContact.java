package uz.pdp.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserContact {
    private String email;
    private String phoneNumber;
    private String faxNumber;
}
