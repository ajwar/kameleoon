package com.yandex.ajwar.kameleoon.services;

import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.UserDto;
import com.yandex.ajwar.kameleoon.entities.sql.User;
import com.yandex.ajwar.kameleoon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public IdDto create(UserDto dto) {
        var user = mapper.map(dto, User.class);
        user = userRepository.insert(user);
        return new IdDto(user.getId());
    }
}
