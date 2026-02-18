package spring.boot.Practice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloAPIcontroller {
    @GetMapping("/hello")
    String sayHello(){
     return " Hello World..!";
    }
}
