package com.yandex.ajwar.kameleoon.services;

import org.springframework.data.domain.Pageable;

public interface CommonService {
    Pageable checkPageSize(Pageable pageable);
}
