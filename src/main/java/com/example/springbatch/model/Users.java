package com.example.springbatch.model;

// import javax.persistence.Entity;
// import javax.persistence.Id;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Users {

    @Id
    private Long id;
    private String name;
    private String email;

}
