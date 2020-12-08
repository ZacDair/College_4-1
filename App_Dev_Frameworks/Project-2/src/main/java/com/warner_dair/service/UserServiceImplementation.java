package com.warner_dair.service;

import com.warner_dair.dao.UserDao;
import com.warner_dair.entities.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserServiceImplementation implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public CustomUser save(CustomUser newCustomUser){
        if(userDao.existsByUserEmail(newCustomUser.getUserEmail())){
            System.out.println("ERROR: An Account with that email already exists");
            return null;
        }
        return userDao.save(newCustomUser);
    }
}
