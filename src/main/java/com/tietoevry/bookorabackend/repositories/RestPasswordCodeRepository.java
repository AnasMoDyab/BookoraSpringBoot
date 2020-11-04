package com.tietoevry.bookorabackend.repositories;
import com.tietoevry.bookorabackend.model.RestPasswordCode;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestPasswordCodeRepository extends JpaRepository<RestPasswordCode, Long> {
    RestPasswordCode findByConfirmationCode(String confirmationCode);

}
