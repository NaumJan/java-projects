package com.example.daoTest;

import com.example.dao.PetDao;
import com.example.model.Pet;
import com.example.model.Color;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PetDaoTest {
    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static SessionFactory sessionFactory;
    private PetDao petDao;

    @BeforeClass
    public static void setup() {
        Configuration config = new Configuration()
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(Pet.class)
                .addAnnotatedClass(com.example.model.Owner.class);

        sessionFactory = config.buildSessionFactory();
    }

    @Before
    public void init() {
        petDao = new PetDao(sessionFactory);
    }

    @Test
    public void testCreatePet() {
        Pet pet = new Pet();
        pet.setName("Мурзик");
        pet.setBreed("Персидский");
        pet.setColor(Color.BLACK);

        Pet saved = petDao.save(pet);
        Assert.assertNotNull(saved.getId());
    }

    @Test
    public void testAddFriends() {
        Pet pet1 = new Pet();
        pet1.setName("Барсик");
        pet1 = petDao.save(pet1);

        Pet pet2 = new Pet();
        pet2.setName("Мурка");
        pet2 = petDao.save(pet2);

        petDao.addFriends(pet1, pet2);

        Pet updated1 = petDao.getById(pet1.getId());
        Pet updated2 = petDao.getById(pet2.getId());

        Assert.assertEquals(1, updated1.getFriends().size());
        Assert.assertEquals(1, updated2.getFriends().size());
    }

    @AfterClass
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}