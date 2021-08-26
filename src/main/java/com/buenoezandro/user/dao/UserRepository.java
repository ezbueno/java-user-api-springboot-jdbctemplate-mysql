package com.buenoezandro.user.dao;

import com.buenoezandro.user.entity.User;

import java.util.List;

public interface UserRepository {

    User saveUser(User user);

    User updateUser(User user);

    User getById(int id);

    String deleteById(int id);

    List<User> getAllUsers();

}