package demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.entity.Country;
import demo.entity.User;
import demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<User> users = List.of(
            User.builder()
                    .firstName("John")
                    .age(25)
                    .country(Country.USA)
                    .build(),
            User.builder()
                    .firstName("Anna")
                    .age(30)
                    .country(Country.RUSSIA)
                    .build()
    );

    @Test
    void getAllUsers_shouldReturnUsers() throws Exception {
        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user-api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].country").value("USA"))
                .andExpect(jsonPath("$[1].firstName").value("Anna"))
                .andExpect(jsonPath("$[1].country").value("RUSSIA"));

        verify(userService).findAllUsers();
    }

    @Test
    void getUserByAge_shouldReturnFilteredUsers() throws Exception {

        Integer age = 25;
        when(userService.findByAgeGreaterThanEqualOrderByFirstNameAsc(age))
                .thenReturn(users.reversed());

        mockMvc.perform(get("/user-api/v1/additional-info")
                        .param("age", age.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Anna"))
                .andExpect(jsonPath("$[1].firstName").value("John"));

        verify(userService).findByAgeGreaterThanEqualOrderByFirstNameAsc(age);
    }

    @Test
    void addUser_shouldCreateNewUser() throws Exception {
        User newUser = User.builder()
                .firstName("Michael")
                .age(28)
                .country(Country.USA)
                .build();

        User savedUser = User.builder()
                .id(1L)
                .firstName("Michael")
                .age(28)
                .country(Country.USA)
                .build();

        when(userService.saveUser(newUser)).thenReturn(savedUser);

        mockMvc.perform(post("/user-api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Michael"))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.country").value("USA"));

        verify(userService).saveUser(newUser);
    }
}