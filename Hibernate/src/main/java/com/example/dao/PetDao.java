package com.example.dao;

import com.example.model.Pet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class PetDao implements Dao<Pet> {
    private final SessionFactory sessionFactory;

    public PetDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Pet save(Pet entity) {
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
            Pet pet = session.get(Pet.class, id);
            if (pet != null) {
                session.createNativeQuery(
                                "DELETE FROM pet_friends WHERE pet_id = :id OR friend_id = :id")
                        .setParameter("id", id)
                        .executeUpdate();

                session.delete(pet);
            }
            tx.commit();
        }
    }

    @Override
    public void deleteByEntity(Pet entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("DELETE FROM pet_friends").executeUpdate();
            session.createQuery("DELETE FROM Pet").executeUpdate();
            tx.commit();
        }
    }

    @Override
    public Pet update(Pet entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
            return entity;
        }
    }

    @Override
    public Pet getById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Pet.class, id);
        }
    }

    @Override
    public List<Pet> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Pet", Pet.class).list();
        }
    }
    public void addFriends(Pet pet1, Pet pet2) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            pet1 = (Pet) session.merge(pet1);
            pet2 = (Pet) session.merge(pet2);

            pet1.getFriends().add(pet2);
            pet2.getFriends().add(pet1);

            session.update(pet1);
            session.update(pet2);
            tx.commit();
        }
    }
}