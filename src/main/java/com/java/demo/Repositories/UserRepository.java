package com.java.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.demo.Entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    
} 
