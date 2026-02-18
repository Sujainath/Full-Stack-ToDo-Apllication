package spring.boot.Practice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.boot.Practice.model.ToDo;
import spring.boot.Practice.repository.ToDoRepository;

import java.util.List;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;


    public ToDo createtodo(ToDo todo){
        return toDoRepository.save(todo);
    }
  // Get method
 // Find specific todo by id
    public ToDo getUserById(Integer id){
        return toDoRepository.findById(id).orElseThrow(()-> new RuntimeException("Id is not found"));
    }

    // Find all todo
    public List<ToDo> getTodos(){
        return  toDoRepository.findAll();
    }

    // Update method

    public ToDo updateTodo(ToDo todo){
        return toDoRepository.save(todo);
    }

    // Delete Todo by id

    public void deleteTodo(Integer id){
        toDoRepository.delete(getUserById(id));
    }

    // Delete todo by todos

    public void deleteTodos(ToDo todo){
         toDoRepository.delete(todo);
    }


    // List of Pages
    public Page<ToDo> setPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return  toDoRepository.findAll(pageable);
    }


}
