package uz.pdp.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RoleDao roleDao;

    public User getUserByUsername(final String username) {
        try {
            User user = jdbcTemplate.queryForObject(
                    "SELECT * FROM USERS WHERE username=? limit 1",
                    BeanPropertyRowMapper.newInstance(User.class),
                    username
            );
            user.setRoles(roleDao.getAllByUserId(user.getId()));
            return user;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(final String username, String password) {
//        PreparedStatementCreator preparedStatementCreator = con -> {
//            final var preparedStatement = con
//                    .prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)");
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//            return preparedStatement;
//        };
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        jdbcTemplate.update("INSERT INTO users(username, password) VALUES(?, ?)", username, password);
        Long id = jdbcTemplate.queryForObject("SELECT id FROM users WHERE username=? LIMIT 1", Long.class, username);
        jdbcTemplate.update(
                "INSERT INTO user_role(user_id, role_id) " +
                        "VALUES(?, 1)",
                id
        );
    }
}
