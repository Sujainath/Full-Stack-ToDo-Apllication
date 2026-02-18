package spring.boot.Practice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.Practice.model.User;
import spring.boot.Practice.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository UserRepository;


    public User createUser(User user){
        return UserRepository.save(user);
    }
  // Get method
 // Find specific todo by id
    public User getUserById(Integer id){
        return UserRepository.findById(id).orElseThrow(()-> new RuntimeException("Id is not found"));
    }



}
