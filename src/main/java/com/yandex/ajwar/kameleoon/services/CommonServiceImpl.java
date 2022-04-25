package com.yandex.ajwar.kameleoon.services;

import com.yandex.ajwar.kameleoon.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.yandex.ajwar.kameleoon.utils.Constants.MAX_SIZE_PER_PAGE;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    /**
     * Check for limiting the maximum number of elements in 1 query to the database
     * and change to {@link Constants#MAX_SIZE_PER_PAGE}, if more
     */
    @Override
    public Pageable checkPageSize(Pageable pageable) {
        if (pageable.getPageSize() > MAX_SIZE_PER_PAGE) {
            pageable = PageRequest.of(pageable.getPageNumber(), MAX_SIZE_PER_PAGE, pageable.getSort());
        }
        return pageable;
    }
}
