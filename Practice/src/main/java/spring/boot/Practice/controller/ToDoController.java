package spring.boot.Practice.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.Practice.service.ToDoService;
import spring.boot.Practice.model.ToDo;

import java.util.List;

@RestController
@RequestMapping("/todo")
@Slf4j
public class ToDoController {
    @Autowired
    private ToDoService toDoService;
//    @GetMapping("/get")
//    String ToDo(){
//
//        return "ToDO";
//    }
//    //path Variable
//    @GetMapping("/{ids}")
//    String getToDobyId(@PathVariable int id){
//        return "ToDO with ID "+ id;
//    }
//
//    //Request Param
//    @GetMapping("/")
//    String getToDoId(@RequestParam("todo") int id){
//        return "ToDO with ID "+ id;
//    }
//
//    @GetMapping("/created")
//    String created(@RequestParam String userid,@RequestParam String password){
//        return " Username: " +userid+" " +"Password:  " + password;
//    }

    // To create a username and password using POSTMAPPING
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Successfully"),
            @ApiResponse(responseCode = "500", description = "Missing some details")
    })
    @PostMapping("/create")
    public ResponseEntity<ToDo> createsUser(@RequestBody ToDo todo) {
        ToDo savedTodo = toDoService.createtodo(todo);

        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

//    // TO Update a username and password using PUTMAPPING
//    @PutMapping("/{id}")
//    String update(@PathVariable int id){
//        return "Update todo with id "+id;
//    }
//
//   @DeleteMapping("/{id}")
//    String delete(@PathVariable int id){
//        return "Delete todo with id "+id;
//   }

    // Specific id to get
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Id Successfully"),
            @ApiResponse(responseCode = "404", description = "Id is Not found!")
    })
   @GetMapping("/{id}")
   ResponseEntity<ToDo> getUserById(@PathVariable Integer id){
       try{
           ToDo readTodo = toDoService.getUserById(id);
           return  new ResponseEntity<>(readTodo, HttpStatus.OK);
       } catch (RuntimeException exception) {
           log.info("Error");
          return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
   }
    // Get all the id
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All ToDos")

    })
    @GetMapping
    ResponseEntity<List<ToDo>> getTodos(){
        return new ResponseEntity<List<ToDo>>(toDoService.getTodos(),HttpStatus.OK);
    }

    // Update Todo
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Successfully"),
            @ApiResponse(responseCode = "500", description = "Missing some details")
    })
    @PutMapping("/update")
    ResponseEntity<ToDo> updateTodo(@RequestBody ToDo todo){
        ToDo updated = toDoService.updateTodo(todo);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //Delete todo by id
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Successfully")

    })
    @DeleteMapping("/{id}")
   void deleteTodo(@PathVariable Integer id){
        toDoService.deleteTodo(id);
    }

    //List of Pages
    @GetMapping("/page")
    ResponseEntity<Page<ToDo>> getPages(@RequestParam int page,@RequestParam int size){
        return new ResponseEntity<>(toDoService.setPage(page, size),HttpStatus.OK);
    }
}

