package com.example.apiserver.controller;

import com.example.apiserver.entity.User;
import com.example.apiserver.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //추가
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("모든 사용자 목록을 조회할 수 있어야 한다.")
    void getAllUsers() throws Exception {
        // given
        User user1 = new User(1L, "John Doe", "john.doe@example.com");
        User user2 = new User(2L, "Jane Doe", "jane.doe@example.com");
        List<User> users = Arrays.asList(user1, user2);

        // when & then
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform( MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("ID로 사용자를 조회할 수 있어야 한다.")
    void getUserById() throws Exception {
        //given
        User user = new User(1L, "Taewoo", "Taewoo@Taewoo");
        Mockito.when(userService.findById(1L)).thenReturn(user);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.id").value(1))
                .andExpect( jsonPath("$.name").value("Taewoo"))
                .andExpect( jsonPath("$.email").value("Taewoo@Taewoo"));

        Mockito.when(userService.findById(999L)).thenReturn(null);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("새로운 사용자를 생성할 수 있어야 한다.")
    void createUser() throws Exception {
        //given
        User userToCreate = new User(null, "Taewoo", "Teawoo@Taewoo");
        User createdUser = new User(1L, "Taewoo", "Taewoo@Taewoo");
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(createdUser);
        String userJson = objectMapper.writeValueAsString(userToCreate);

        // when
        ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders.post( "/users" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( userJson ) )
                //then
                .andExpect( status().isCreated() )
                .andExpect( jsonPath( "$.id" ).value( 1 ) )
                .andExpect( jsonPath( "$.name" ).value( "Taewoo" ) )
                .andExpect( jsonPath( "$.email" ).value( "Taewoo@Taewoo" ) );
    }
}