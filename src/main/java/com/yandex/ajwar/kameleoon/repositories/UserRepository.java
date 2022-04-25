package com.yandex.ajwar.kameleoon.repositories;

import com.yandex.ajwar.kameleoon.configs.repositories.CustomJpaRepository;
import com.yandex.ajwar.kameleoon.entities.sql.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {
}
