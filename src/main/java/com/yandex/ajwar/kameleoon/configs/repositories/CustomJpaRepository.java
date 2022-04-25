package com.yandex.ajwar.kameleoon.configs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aivar on 11.02.2019
 */
@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    <S extends T> void updateAndFlush(S s);

    <S extends T> void update(S s);

    <S extends T> S insertAndFlush(S s);

    <S extends T> S insert(S s);

    <S extends T> List<S> insertAll(Iterable<S> entities);

    void deleteByIdFlush(ID id);
}
