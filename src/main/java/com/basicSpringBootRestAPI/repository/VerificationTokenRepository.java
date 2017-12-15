package com.basicSpringBootRestAPI.repository;

import com.basicSpringBootRestAPI.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Integer> {
    VerificationToken findByToken(String token);
}
