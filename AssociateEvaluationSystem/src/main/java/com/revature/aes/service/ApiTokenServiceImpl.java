package com.revature.aes.service;

import com.revature.aes.beans.ApiToken;
import com.revature.aes.dao.ApiTokenDao;
import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Created by mpski on 2/17/17.
 */

@Service
@Transactional
public class ApiTokenServiceImpl implements ApiTokenService {

    @Autowired
    ApiTokenDao apiTokenDao;

    @Override
    public ApiToken findApiTokenById(Integer id) {
        return apiTokenDao.findOne(id);
    }

    @Override
    public ApiToken findApiTokenByToken(String token) {
        return apiTokenDao.findApiTokenByToken(token);
    }

}
