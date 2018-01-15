package com.basicSpringBootRestAPI.repository;

import com.basicSpringBootRestAPI.entity.User;
import com.basicSpringBootRestAPI.entity.UserAuthenticationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationTokenRepository extends CrudRepository<UserAuthenticationToken, Integer> {
    UserAuthenticationToken findByToken(String token);

    UserAuthenticationToken findByUser(User user);
}
