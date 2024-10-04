package com.example.springbatch.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/*
* 데이터베이스에 삽입할 유저 데이터를 위한 JPA 엔티티를 정의
*/
@Entity
@Getter
@Setter
@ToString
public class Users {

    @Id
    private String id;
    private String name;
    private String email;

}
