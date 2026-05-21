package com.exam.repository;
import com.exam.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
    public Users findByUserName(String userName);
}
