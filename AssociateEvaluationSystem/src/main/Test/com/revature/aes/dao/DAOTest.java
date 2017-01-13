package com.revature.aes.dao;

import com.revature.aes.Application;
import com.revature.aes.beans.Role;
import com.revature.aes.beans.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Nick on 1/12/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ContextConfiguration(locations = {"classpath:log4j2.xml"})
@Transactional
public class DAOTest {

    @Autowired
    DAO<User, Integer> UserDao;

    List<User> userList;

    @Before
    public void setUp(){

        userList = new ArrayList<>();

        User user1 = new User();
        user1.setFirstName("Nick");
        user1.setLastName("Jurczak");
        user1.setEmail("nickolas.jurczak@gmail.com");
        Role user1Role = new Role();
        user1Role.setRoleId(4);
        user1.setRole(user1Role);

        userList.add(user1);

    }

    @Test
    public void testUserDao(){



    }


}