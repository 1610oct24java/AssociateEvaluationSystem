package com.revature.aes.dao;

import com.revature.aes.beans.ApiToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mpski on 2/20/17.
 */

@Repository
public interface ApiTokenDao extends JpaRepository<ApiToken, Integer> {
    ApiToken findOne(Integer id);
    ApiToken findApiTokenByToken(String token);
}
