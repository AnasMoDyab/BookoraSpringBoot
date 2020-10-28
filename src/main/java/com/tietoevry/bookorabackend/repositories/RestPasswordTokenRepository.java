package com.tietoevry.bookorabackend.repositories;
import com.tietoevry.bookorabackend.model.RestPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestPasswordTokenRepository  extends JpaRepository<RestPasswordToken , Long> {
    RestPasswordToken findByRestPasswordToken(String restPasswordToken);
}
