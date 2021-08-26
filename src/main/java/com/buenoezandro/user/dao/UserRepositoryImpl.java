package com.buenoezandro.user.dao;

import com.buenoezandro.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String INSERT_USER_QUERY = "INSERT INTO user(firstName, lastName, email) VALUES(?, ?, ?)";
    private static final String UPDATE_USER_BY_ID_QUERY = "UPDATE user SET firstName = ?, lastName = ?, email = ? WHERE id = ?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String DELETE_USER_BY_ID_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String GET_USERS_QUERY = "SELECT * FROM user";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User saveUser(User user) {
        var params = new Object[]{user.getFirstName(), user.getLastName(), user.getEmail()};
        this.jdbcTemplate.update(INSERT_USER_QUERY, params);
        return user;
    }

    @Override
    public User updateUser(User user) {
        var params = new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getId()};
        this.jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY, params);
        return user;
    }

    @Override
    public User getById(int id) {
        return this.jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, (rs, rowNum) -> {
            return new User(rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"));
        }, id);
    }

    @Override
    public String deleteById(int id) {
        this.jdbcTemplate.update(DELETE_USER_BY_ID_QUERY, id);
        return "User got deleted with ID: " + id;
    }

    @Override
    public List<User> getAllUsers() {
        return this.jdbcTemplate.query(GET_USERS_QUERY, (rs, rowNum) -> {
            return new User(rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"));
        });
    }
}