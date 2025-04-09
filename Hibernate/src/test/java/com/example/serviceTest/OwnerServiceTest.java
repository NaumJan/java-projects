package com.example.serviceTest;
import com.example.dao.OwnerDao;
import com.example.model.Owner;
import com.example.model.Pet;
import com.example.service.OwnerService;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.time.LocalDate;

public class OwnerServiceTest {
    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    private static SessionFactory sessionFactory;
    private OwnerService ownerService;

    @BeforeClass
    public static void setup() {
        Configuration config = new Configuration()
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                .addAnnotatedClass(Owner.class)
                .addAnnotatedClass(Pet.class);

        sessionFactory = config.buildSessionFactory();
    }

    @Before
    public void init() {
        OwnerDao ownerDao = new OwnerDao(sessionFactory);
        ownerService = new OwnerService(ownerDao);
    }

    @Test
    public void testCreateOwner() {
        Owner owner = ownerService.createOwner("Тестовый Владелец", LocalDate.of(1985, 5, 10));
        Assert.assertNotNull(owner.getId());
    }

    @Test
    public void testAddPetToOwner() {
        Owner owner = ownerService.createOwner("Владелец", LocalDate.now());
        Pet pet = new Pet();
        pet.setName("Питомец");

        ownerService.addPetToOwner(owner, pet);

        Assert.assertEquals(1, ownerService.getOwnerPets(owner.getId()).size());
    }

    @AfterClass
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}