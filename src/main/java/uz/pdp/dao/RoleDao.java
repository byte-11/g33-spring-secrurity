package uz.pdp.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.domain.Permission;
import uz.pdp.domain.Role;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Role> getAllByUserId(final long userId) {
        try {
            List<Role> roles = jdbcTemplate.query(
                    "SELECT r.id, r.name, r.code FROM role  r INNER JOIN user_role ur ON ur.role_id = r.id WHERE ur.user_id = ?",
                    BeanPropertyRowMapper.newInstance(Role.class),
                    userId
            );
            roles.forEach(role -> role.setPermissions(getAllPermissionByRoleId(role.getId())));
            return roles;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Permission> getAllPermissionByRoleId(final Long roleId) {
        List<Permission> permissions = jdbcTemplate.query(
                "SELECT p.id, p.name, p.code FROM permission p INNER JOIN role_permission rp on p.id = rp.permission_id WHERE rp.role_id = ?",
                BeanPropertyRowMapper.newInstance(Permission.class),
                roleId
        );
        return permissions;
    }
}
