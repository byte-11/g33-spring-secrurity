package uz.pdp.config.sercurity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.pdp.dao.UserDao;
import uz.pdp.domain.Role;
import uz.pdp.domain.User;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final User user = userDao.getUserByUsername(username);
            return user;
            /*return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities("ADMIN")
                    .roles(extractUserRoles(user))
                    .build();*/
        } catch (DataAccessException e) {
            log.error("{}", e.getMessage());
        throw new UsernameNotFoundException("User not found with username - " + username);
        }
    }

    private static String[] extractUserRoles(User user) {
        String[] rolesArray = new String[user.getRoles().size()];
        for (int i = 0; i < rolesArray.length; i++) {
            rolesArray[i] = user.getRoles().get(i).getCode();
        }
        return rolesArray;
    }
}
