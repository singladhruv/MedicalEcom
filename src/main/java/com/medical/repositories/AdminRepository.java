package com.medical.repositories;

import org.springframework.data.repository.CrudRepository;
import com.medical.entities.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
}
