package com.example.daoTest;

import com.example.dao.OwnerDao;
import com.example.model.Owner;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.time.LocalDate;

public class OwnerDaoTest {
    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static SessionFactory sessionFactory;
    private OwnerDao ownerDao;

    @BeforeClass
    public static void setup() {
        Configuration config = new Configuration()
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true")
                .addAnnotatedClass(Owner.class);

        sessionFactory = config.buildSessionFactory();
    }

    @Before
    public void init() {
        ownerDao = new OwnerDao(sessionFactory);
    }

    @Test
    public void testSaveAndGetOwner() {
        Owner owner = new Owner();
        owner.setName("Иван Петров");
        owner.setBirthDate(LocalDate.of(1990, 5, 15));

        Owner savedOwner = ownerDao.save(owner);
        Owner foundOwner = ownerDao.getById(savedOwner.getId());

        Assert.assertEquals(owner.getName(), foundOwner.getName());
        Assert.assertEquals(owner.getBirthDate(), foundOwner.getBirthDate());
    }

    @Test
    public void testUpdateOwner() {
        Owner owner = new Owner();
        owner.setName("Original Name");
        owner = ownerDao.save(owner);

        owner.setName("Updated Name");
        ownerDao.update(owner);

        Owner updated = ownerDao.getById(owner.getId());
        Assert.assertEquals("Updated Name", updated.getName());
    }

    @Test
    public void testDeleteOwner() {
        Owner owner = new Owner();
        owner.setName("To be deleted");
        owner = ownerDao.save(owner);

        ownerDao.deleteById(owner.getId());
        Owner deleted = ownerDao.getById(owner.getId());

        Assert.assertNull(deleted);
    }

    @AfterClass
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}