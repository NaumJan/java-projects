package com.example.dao;

import com.example.model.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class OwnerDao implements Dao<Owner> {
    private final SessionFactory sessionFactory;

    public OwnerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Owner save(Owner entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        }
    }

    @Override
    public void deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Owner owner = session.get(Owner.class, id);
            if (owner != null) {
                session.delete(owner);
            }
            tx.commit();
        }
    }

    @Override
    public void deleteByEntity(Owner entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM Owner").executeUpdate();
            tx.commit();
        }
    }

    @Override
    public Owner update(Owner entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Owner merged = (Owner) session.merge(entity);
            tx.commit();
            return merged;
        }
    }

    @Override
    public Owner getById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Owner.class, id);
        }
    }

    @Override
    public List<Owner> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Owner", Owner.class).list();
        }
    }
}