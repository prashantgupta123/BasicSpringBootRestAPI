package com.basicSpringBootRestAPI.repository;

import com.basicSpringBootRestAPI.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByAuthority(String authority);
}
