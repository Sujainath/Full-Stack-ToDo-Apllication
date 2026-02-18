package spring.boot.Practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.Practice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User>  findByEmail(String email);
}
