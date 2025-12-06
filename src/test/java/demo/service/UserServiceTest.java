package demo.service;

import demo.entity.Country;
import demo.entity.User;
import demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final List<User> expectedUsers = List.of(
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
    void findAllUsers_shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> result = userService.findAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedUsers);
        verify(userRepository).findAll();
    }

    @Test
    void saveUser_shouldSaveAndReturnUser() {
        User userToSave = User.builder()
                .firstName("Kirill")
                .age(21)
                .country(Country.BELARUS)
                .build();
        User savedUser = User.builder()
                .id(1L)
                .firstName("Kirill")
                .age(21)
                .country(Country.BELARUS)
                .build();
        when(userRepository.save(userToSave)).thenReturn(savedUser);

        User result = userService.saveUser(userToSave);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Kirill");
        verify(userRepository).save(userToSave);
    }


    @Test
    void findByAgeGreaterThanEqual_shouldReturnFilteredUsers() {
        Integer age = 25;
        when(userService.findByAgeGreaterThanEqualOrderByFirstNameAsc(age))
                .thenReturn(expectedUsers);

        List<User> result = userService.findByAgeGreaterThanEqualOrderByFirstNameAsc(age);

        assertThat(result).hasSize(2);
        verify(userRepository).findByAgeGreaterThanEqualOrderByFirstNameAsc(age);
    }

}
