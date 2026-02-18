package spring.boot.Practice.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity // Do create a table
@Data   // Insert a Data is like a getter and setter
@JsonPropertyOrder({ "id", "title", "isCompleted" })
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //To Unique values
    Integer id;
    @NotNull
    @NotBlank
    String Title;
    Boolean isCompleted;

}
