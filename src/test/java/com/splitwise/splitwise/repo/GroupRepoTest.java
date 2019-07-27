package com.splitwise.splitwise.repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.splitwise.splitwise.model.entity.GroupEntity;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepoTest {


    @Autowired
    private GroupRepo groupRepo;

    @Before
    public void setUp() throws Exception {
        final GroupEntity groupEmtity = new GroupEntity();
        groupEmtity.setName("Nirma");
        groupRepo.save(groupEmtity);
    }

    @Test
    public void test_1() {
        final GroupEntity groupEmtity = groupRepo.findByName("Nirma");

    }
//
//    @Test
//    public void test_2() {
//
//        final GroupEntity gg = groupRepo.findById(1l).get();
//        System.out.println(gg);
//
//    }

}