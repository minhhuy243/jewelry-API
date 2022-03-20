package com.jewelry.api.user.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {

//    @Override
//    @Query("SELECT c FROM User c JOIN FETCH c.roles")
//    List<User> findAll();

    int countByPhoneNumber(String phoneNumber);

    int countByEmail(String email);
    <T> Optional<T> findByEmail(String email, Class<T> type);
}
