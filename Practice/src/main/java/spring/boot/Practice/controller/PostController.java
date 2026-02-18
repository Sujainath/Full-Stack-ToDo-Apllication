package spring.boot.Practice.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo/")
public class PostController {
    @PostMapping("/post")
    String createsuser(@RequestBody String body) {
        return body;
    }

}
