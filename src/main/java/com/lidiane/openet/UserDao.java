package com.lidiane.openet;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private static Queue<User> q = new LinkedList<>();

    public UserDao() {
       
    }

    public void addUser(User user){
    
        q.add(user);
    }

    public  User getUser() {
        return q.peek();
    }

    public  User delUser() {
        return q.poll();
    }

    public Queue<User> verUsers() {
        return q;
    }
}
