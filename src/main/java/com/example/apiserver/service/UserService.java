package com.example.apiserver.service;

import com.example.apiserver.entity.User;
import com.example.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        log.info( "fetching all User" );
        return userRepository.findAll();
    }

    public User findById(Long id) {
        log.info( "Fetching User id{}", id );
        return userRepository.findById( id ).orElse( null );
    }

    public User save(User user) {
        log.info( "save user {}", user );
        return userRepository.save( user );
    }

    public void deleteById(Long id) {
        log.info( "Deleting user with id {}", id );
        userRepository.deleteById( id );
    }
}
