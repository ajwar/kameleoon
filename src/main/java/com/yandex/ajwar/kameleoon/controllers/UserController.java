package com.yandex.ajwar.kameleoon.controllers;

import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.UserDto;
import com.yandex.ajwar.kameleoon.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/users", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public IdDto create(@Valid @RequestBody UserDto dto) {
        return userService.create(dto);
    }
}
