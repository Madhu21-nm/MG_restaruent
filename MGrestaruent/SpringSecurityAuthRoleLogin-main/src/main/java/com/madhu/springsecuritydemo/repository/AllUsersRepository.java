package com.madhu.springsecuritydemo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.madhu.springsecuritydemo.model.AllUsers;

public interface AllUsersRepository extends JpaRepository <AllUsers, Long>{
    Optional<AllUsers> findByUsername(String username);
}