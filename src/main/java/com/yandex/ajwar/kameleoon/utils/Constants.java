package com.yandex.ajwar.kameleoon.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    //The name of the generator so that you can inherit from base classes in essence and use different sequences
    public static final String NAME_GENERATOR_SEQUENCE = "parent_seq";
    public static final int DEFAULT_SEQUENCE_ALLOCATION_SIZE = 100;

    public static final int SIZE_PER_PAGE_QUOTE = 10;
    public static final int MAX_SIZE_PER_PAGE = 50;
}
