package com.example.apiserver.service;

import com.example.apiserver.entity.User;
import com.example.apiserver.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void settingUp() {
        MockitoAnnotations.openMocks( this );
    }

    @Test
    @DisplayName("모든 사용자를 조회한다.")
    void testFindAll() {
        //given
        User user = new User( 1L, "taewoo", "taewoo@taewoo.com" );
        when( userRepository.findAll() ).thenReturn( Collections.singletonList( user ) );

        //when
        List<User> users = userService.findAll();

        //then
        assertThat( users ).hasSize( 1 );
        //실패케이스 작성
//        assertThat( users.get( 0 ).getName() ).isEqualTo( "taewoewoe" );
        assertThat( users.get( 0 ).getName() ).isEqualTo( "taewoo" );
    }

    @Test
    @DisplayName("ID로 조회")
    void testFindId() {
        //given
        User user = new User( 1L, "Taewoo", "Teawoo@Taewoo" );
        when( userRepository.findById( 1L ) ).thenReturn( Optional.of( user ) );

        //when
        User findId = userService.findById( 1L );

        //then
        assertThat( findId ).isNotNull();
    }

    @Test
    @DisplayName("사용자 저장을 성공해야 한다.")
    void testSaveUser() {
        //given
        User user = new User( 1L, "Taewoo", "Taewoo@Taewoo" );
        when( userRepository.save( user ) ).thenReturn( user );

        //when
        User saveUser = userService.save( user );

        //then
        assertThat( saveUser ).isNotNull();
        assertThat( saveUser.getName() ).isEqualTo( "Taewoo" );

    }
}