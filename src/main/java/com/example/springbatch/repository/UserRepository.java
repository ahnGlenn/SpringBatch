package com.example.springbatch.repository;

import com.example.springbatch.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/*
* User 데이터를 DB에 저장할 수 있도록 JPA 레포지토리를 생성
*/
public interface UserRepository extends JpaRepository<Users, Long> {

}
