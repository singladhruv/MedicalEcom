package com.medical.repositories;

import org.springframework.data.repository.CrudRepository;
import com.medical.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
