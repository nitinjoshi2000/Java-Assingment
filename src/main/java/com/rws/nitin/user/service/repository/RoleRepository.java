package com.rws.nitin.user.service.repository;

import com.rws.nitin.user.service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Integer> {
}
