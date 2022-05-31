package com.test_covinoc.service;

import com.test_covinoc.models.User;

import java.util.List;

public interface IUserService {

    public User save(User user);

    public List<User> findAll();

    public User findById(Integer id);

    public void delete(Integer id);
}
