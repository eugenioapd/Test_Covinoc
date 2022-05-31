package com.test_covinoc.service.implemet;

import com.test_covinoc.models.User;
import com.test_covinoc.repository.IUserRepository;
import com.test_covinoc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImple implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
     userRepository.deleteById(id);
    }

}
