package com.example.serviceTest;

import com.example.dao.PetDao;
import com.example.model.*;
import com.example.service.PetService;
import org.hibernate.Session;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PetServiceTest {
    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    private static SessionFactory sessionFactory;
    private PetService petService;
    private PetDao petDao;

    @BeforeClass
    public static void setup() {
        Configuration config = new Configuration()
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                .addAnnotatedClass(Pet.class)
                .addAnnotatedClass(Owner.class);

        sessionFactory = config.buildSessionFactory();
    }

    @Before
    public void init() {
        petDao = new PetDao(sessionFactory);
        petService = new PetService(petDao);
    }

    @Test
    public void testGetPetsByColor() {
        Pet blackPet = new Pet();
        blackPet.setName("Черныш");
        blackPet.setColor(Color.BLACK);
        petDao.save(blackPet);

        Pet whitePet = new Pet();
        whitePet.setName("Беляш");
        whitePet.setColor(Color.WHITE);
        petDao.save(whitePet);

        List<Pet> blackPets = petService.getPetsByColor(Color.BLACK);
        Assert.assertEquals(1, blackPets.size());
        Assert.assertEquals("Черныш", blackPets.get(0).getName());
    }

    @Test
    public void testTransferPet() {
        Owner oldOwner = new Owner();
        oldOwner.setName("Старый владелец");

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(oldOwner);
            session.getTransaction().commit();
        }

        Owner newOwner = new Owner();
        newOwner.setName("Новый владелец");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(newOwner);
            session.getTransaction().commit();
        }

        Pet pet = new Pet();
        pet.setName("Переезжающий питомец");
        pet.setOwner(oldOwner);
        petDao.save(pet);

        petService.transferPetToNewOwner(pet, newOwner);

        Pet updated = petDao.getById(pet.getId());
        Assert.assertEquals(newOwner.getId(), updated.getOwner().getId());
    }

    @AfterClass
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}