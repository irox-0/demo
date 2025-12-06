package demo.service;

import demo.entity.User;
import demo.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findByAgeGreaterThanEqualOrderByFirstNameAsc(Integer age){
        return userRepository.findByAgeGreaterThanEqualOrderByFirstNameAsc(age);
    };
}
