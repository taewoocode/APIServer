package com.example.apiserver.controller;

import com.example.apiserver.entity.User;
import com.example.apiserver.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //추가
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

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
        User user1 = new User(1L, "Taewoo", "Taewoo@Gmail.com");
        User user2 = new User(2L, "Hello", "Hello@Gmail.com");
        List<User> users = Arrays.asList(user1, user2);

        // when
        when(userService.findAll()).thenReturn(users);

        // then
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));

    }

    @Test
    @DisplayName("ID로 사용자를 조회시 사용자가 없으면 상태코드 404를 반환한다.")
    void getUserByIdNotFind() throws Exception {
        //given
        when( userService.findById( 1L ) ).thenReturn( null );

        //when
        ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders.get( "/users/{id}", 1 )
                .contentType( MediaType.APPLICATION_JSON ) );

        //then
        resultActions
                .andExpect( status().isNotFound() )
                .andExpect( result -> logger.info( "Response: {}", result.getResponse().getContentAsString() ) );
    }

    @Test
    @DisplayName("사용자 정보를 생성할 때 잘못된 요청 상태코드 400를 반환한다.")
    void createUser_BadRequest() throws Exception {
        // given
        User invalidUser = new User(null, "", "asldkj");
        String userJson = objectMapper.writeValueAsString(invalidUser);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName("ID로 사용자를 조회할 수 있어야 한다.")
    void getUserById() throws Exception {

        // given
        User user = new User( 1L, "Taewoo", "Taewoo@Taewoo" );
        when( userService.findById( 1L ) ).thenReturn( user );

        // when
        ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders.get( "/users/{id}", 1 )
                .contentType( MediaType.APPLICATION_JSON ) );

        //then
        resultActions.andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" ).value( 1 ) )
                .andExpect( jsonPath( "$.name" ).value( "Taewoo" ) )
                .andExpect( jsonPath( "$.email" ).value( "Taewoo@Taewoo" ) )
                .andDo( result -> logger.info( "Response: {}", result.getResponse().getContentAsString() ) );

    }

    @Test
    @DisplayName("새로운 사용자를 생성할 수 있어야 한다.")
    void createUser() throws Exception {
        // given
        User userToCreate = new User(null, "Taewoo", "Taewoo@Taewoo");
        User createdUser = new User(1L, "Taewoo", "Taewoo@Taewoo");
        when(userService.save(Mockito.any(User.class))).thenReturn(createdUser);
        String userJson = objectMapper.writeValueAsString(userToCreate);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Taewoo"))
                .andExpect(jsonPath("$.email").value("Taewoo@Taewoo"))
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName("사용자 정보를 수정할 수 있어야 한다.")
    void updateUser() throws Exception {
        // given
        User user = new User(1L, "Taewoo", "TaeWoo@TaeWoo");
        when(userService.save(Mockito.any(User.class))).thenReturn(user);
        String userJson = objectMapper.writeValueAsString(new User(1L, "Taewoo", "Taewoo@Taewoo"));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Taewoo"))
                .andExpect(jsonPath("$.email").value("TaeWoo@TaeWoo"))
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName("사용자를 삭제할 수 있어야 한다.")
    void deleteUser() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isNoContent())
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));
    }
}