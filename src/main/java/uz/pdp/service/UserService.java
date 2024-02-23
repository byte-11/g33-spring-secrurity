package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.dao.UserDao;
import uz.pdp.domain.User;
import uz.pdp.dto.UserSignUpDto;
import uz.pdp.dto.UserUpdateDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(final UserSignUpDto dto){
        return userDao.save(
                User.builder()
                        .name(dto.username())
                        .password(passwordEncoder.encode(dto.password()))
                        .build()
        );
    }

    public User findById(final long id){
        return userDao.getById(id);
    }

    public User updateUser(final UserUpdateDto updateDto){
        User user = userDao.getById(updateDto.getId());
        user.setName(updateDto.getUsername());
        return userDao.update(user);
    }

    public void delete(final long id){
        userDao.delete(id);
    }

    public List<User> getAll(){
        return userDao.getAll();
    }

}
