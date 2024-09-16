package com.example.AccountOfExpenses.repository;

import com.example.AccountOfExpenses.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    // RowMapper for User table
    public static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    };

    // Find all users
    public List<User> findAll() {
        String sql = "SELECT id, username, email, password FROM Users ORDER BY id";
        List<User> userList = template.query(sql, USER_ROW_MAPPER);
        return userList;
    }

    // Insert user
    public void insert(User user) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());

        String sql = "INSERT INTO Users(username, email, password) VALUES(:username, :email, :password)";
        template.update(sql, param);
    }

    // Find by email and password
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, username, email, password FROM Users WHERE email=:email AND password=:password";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password);

        List<User> usersList = template.query(sql, param, USER_ROW_MAPPER);
        return usersList.isEmpty() ? null : usersList.get(0);
    }

    // Find by email
    public User findByEmail(String email) {
        String sql = "SELECT id, username, email, password FROM Users WHERE email=:email";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);

        List<User> usersList = template.query(sql, param, USER_ROW_MAPPER);
        return usersList.isEmpty() ? null : usersList.get(0);
    }

    // Delete user
    public void delete(Integer id) {
        String sql = "DELETE FROM Users WHERE id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(sql, param);
    }
}
