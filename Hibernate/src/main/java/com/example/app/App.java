package com.example.app;

import com.example.dao.OwnerDao;
import com.example.dao.PetDao;
import com.example.model.Owner;
import com.example.model.Pet;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class App {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        try {
            PetDao petDao = new PetDao(sessionFactory);
            OwnerDao ownerDao = new OwnerDao(sessionFactory);

            Owner owner = new Owner();
            owner.setName("Доктор Ватсон");
            ownerDao.save(owner);

            Pet pet = new Pet();
            pet.setName("Базилио");
            pet.setOwner(owner);
            petDao.save(pet);

        } finally {
            sessionFactory.close();
        }
    }
}