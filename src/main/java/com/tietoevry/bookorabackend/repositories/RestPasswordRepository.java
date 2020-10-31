package com.tietoevry.bookorabackend.repositories;
import com.tietoevry.bookorabackend.model.RestPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RestPasswordRepository extends JpaRepository<RestPassword, Long> {
    RestPassword findByConfirmationCode(String confirmationCode);

}
