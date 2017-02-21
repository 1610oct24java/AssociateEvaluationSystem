package com.revature.aes.service;

import com.revature.aes.beans.ApiToken;

/**
 * Created by mpski on 2/17/17.
 */

public interface ApiTokenService {

    ApiToken findApiTokenById(Integer id);
    ApiToken findApiTokenByToken(String token);

}
