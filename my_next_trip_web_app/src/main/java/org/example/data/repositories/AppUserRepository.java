package org.example.data.repositories;

import org.example.data.mappers.AppUserMapper;
import org.example.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public AppUser findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = "SELECT app_user_id, username, password_hash, enabled "
                + "FROM app_user "
                + "WHERE username = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);
    }

    @Transactional
    public AppUser create(AppUser user) {

        final String sql = "INSERT INTO app_user (username, password_hash) VALUES (?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }

    @Transactional
    public void update(AppUser user) {

        final String sql = "UPDATE app_user SET "
                + "username = ?, "
                + "enabled = ? "
                + "where app_user_id = ?";

        jdbcTemplate.update(sql,
                user.getUsername(), user.isEnabled(), user.getAppUserId());

        updateRoles(user);
    }

    private void updateRoles(AppUser user) {
        // delete all roles, then re-add
        jdbcTemplate.update("DELETE FROM app_user_role WHERE app_user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (GrantedAuthority role : authorities) {
            String sql = "INSERT INTO app_user_role (app_user_id, app_role_id) "
                    + "select ?, app_role_id FROM app_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role.getAuthority());
        }
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "SELECT r.name "
                + "FROM app_user_role ur "
                + "INNER JOIN app_role r on ur.app_role_id = r.app_role_id "
                + "INNER JOIN app_user au on ur.app_user_id = au.app_user_id "
                + "WHERE au.username = ?";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }
}