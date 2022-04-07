package com.dendrit.bookshop.internalauthapi.repositories;

import com.dendrit.bookshop.internalauthapi.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByName(String name);

}
