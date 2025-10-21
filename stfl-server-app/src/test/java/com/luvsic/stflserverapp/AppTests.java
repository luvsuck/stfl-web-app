package com.luvsic.stflserverapp;

import com.luvsic.stflserverapp.entity.User;
import com.luvsic.stflserverapp.mapper.UserDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTests {

    @Autowired
    UserDAO userDAO;

    @Test
    public void sqlTest() {
        userDAO.delete(null);
        userDAO.insert(new User("a", "bc"));
        userDAO.selectList(null).forEach(System.out::println);
    }

}
