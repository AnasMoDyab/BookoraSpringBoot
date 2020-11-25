package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Role;
import com.tietoevry.bookorabackend.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This repository provides create, read, update and delete operations for role.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Provides a Optional Role object.
     *
     * @param name A RoleEnum
     * @return A Optional Role object
     */
    Optional<Role> findByName(RoleEnum name);
}

