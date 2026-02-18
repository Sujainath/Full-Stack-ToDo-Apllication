package spring.boot.Practice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.Practice.model.ToDo;

// CRUD - Create Read Update Delete
public interface ToDoRepository extends JpaRepository<ToDo, Integer> {


}
