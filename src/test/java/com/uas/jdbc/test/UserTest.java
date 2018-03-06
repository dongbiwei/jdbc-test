package com.uas.jdbc.test;

import com.uas.jdbc.entity.User;
import com.uas.jdbc.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pro1 on 2018/3/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testBatchSave() {
        List<User> users = new ArrayList<>();
        for(int i = 0;i < 5;i++) {
            users.add(User.randomUser());
        }
        List<Long> ids = userRepository.save(users);
        ids.forEach(System.out::println);
    }

}
