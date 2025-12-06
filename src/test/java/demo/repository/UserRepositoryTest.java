package demo.repository;

import demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByAgeGreaterThanEqualOrderByFirstNameAsc_shouldReturnCorrectUsers() {
        List<User> result = userRepository.findByAgeGreaterThanEqualOrderByFirstNameAsc(25);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("Chen");
        assertThat(result.get(1).getFirstName()).isEqualTo("George");
        assertThat(result).extracting(User::getAge).containsExactly(25, 30);
    }
}
