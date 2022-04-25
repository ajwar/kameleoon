package com.yandex.ajwar.kameleoon.configs.repositories;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aivar on 11.02.2019
 */

public class CustomSimpleJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID> {

    private EntityManager entityManager;
    private JpaEntityInformation<T, ?> entityInformation;

    public CustomSimpleJpaRepository(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JpaEntityInformation<T, ?> entityInformation,
                                     EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public <S extends T> void updateAndFlush(S s) {
        final Session session = getCurrentSession();
        session.update(s);
        session.flush();
    }

    @Transactional
    @Override
    public <S extends T> void update(S s) {
        getCurrentSession().update(s);
    }

    @Transactional
    @Override
    public <S extends T> S insertAndFlush(S s) {
        final Session session = getCurrentSession();
        session.persist(s);
        session.flush();
        return s;
    }

    @Transactional
    @Override
    public <S extends T> S insert(S s) {
        getCurrentSession().persist(s);
        return s;
    }

    @Transactional
    @Override
    public <S extends T> List<S> insertAll(Iterable<S> entities) {
        Assert.notNull(entities, "Entities must not be null!");

        final List<S> result = new ArrayList<>();

        for (S entity : entities) {
            result.add(insert(entity));
        }

        return result;
    }

    @Override
    @Transactional
    public void deleteByIdFlush(ID id) {
        deleteById(id);
        entityManager.flush();
    }

    private Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }
}
