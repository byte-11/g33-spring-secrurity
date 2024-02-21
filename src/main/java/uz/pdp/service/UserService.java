package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.dao.UserDao;
import uz.pdp.domain.User;
import uz.pdp.dto.UserSignUpDto;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(final UserSignUpDto dto){
        userDao.save(
                dto.username(),
                passwordEncoder.encode(dto.password())
        );
    }
}
