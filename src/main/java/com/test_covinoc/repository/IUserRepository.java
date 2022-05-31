package com.test_covinoc.repository;

import com.test_covinoc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Integer> {

}
