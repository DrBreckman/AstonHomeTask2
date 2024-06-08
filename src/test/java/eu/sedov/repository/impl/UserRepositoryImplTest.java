package eu.sedov.repository.impl;

import java.util.ArrayList;
import java.util.List;

import eu.sedov.dao.impl.UserDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.db.impl.ConnectionSQL;
import eu.sedov.model.User;
import eu.sedov.repository.mapper.impl.UserResultSetMapperImpl;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    private UserRepositoryImpl repository;

    static MySQLContainer<?> mySQLcontainer = new MySQLContainer<>(
            "mysql:8.4.0-oracle"
    );

    @BeforeAll
    static void beforeAll() {
        mySQLcontainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLcontainer.stop();
    }

    @BeforeEach
    void setUp() {
        ConnectionManager manager = new ConnectionSQL(
                mySQLcontainer.getJdbcUrl(),
                mySQLcontainer.getUsername(),
                mySQLcontainer.getPassword()
        );
        repository = new UserRepositoryImpl(new UserResultSetMapperImpl(), manager, new UserDAO());
    }

    @Test
    void insert() {
        int currentSize = repository.getAll().size();
        final List<User> addedUsers = new ArrayList<>(){{
            add(new User(1, "Nikita", 25, "Moscow"));
            add(new User(2, "Masha", 19, "Italy"));
            add(new User(3, "Oleg", 26, "Belarus"));
            add(new User(4, "Vlad", 74, "Vasuki-17"));
        }};
        for(var user : addedUsers)
            repository.insert(user);

        List<User> dbUsers = repository.getAll();
        assertEquals(currentSize + addedUsers.size(), dbUsers.size());
    }

    @Test
    void get(){
        final List<User> addedUsers = new ArrayList<>(){{
            add(new User(5, "Max", 31, "Japan"));
            add(new User(6, "Misha", 25, "Germany"));
            add(new User(7, "Oleg", 24, "Poland"));
            add(new User(8, "Denis", 32, "Russia"));
        }};
        for(var user : addedUsers)
            repository.insert(user);

        User dbUser = repository.get(-1);
        assertNull(dbUser);

        User randomUser = repository.getAll().get(2);
        dbUser = repository.get(randomUser.getId());
        assertEquals(randomUser, dbUser);
    }

    @Test
    void update(){
        final List<User> addedUsers = new ArrayList<>(){{
            add(new User(9, "Kiril", 15, "Turkey"));
            add(new User(10, "Dasha", 28, "London"));
            add(new User(11, "Masha", 35, "Berlin"));
            add(new User(12, "Kaska", 42, "Gomel"));
        }};
        for(var user : addedUsers)
            repository.insert(user);

        User updatedUser = repository.getAll().get(3);
        updatedUser.setName("Glasha");
        updatedUser.setAge(18);
        updatedUser.setAddress("Gomel_upd");

        int updatedUsersCount = repository.update(new User(-1, "Masha", 35, "Berlin"));
        assertEquals(0, updatedUsersCount);

        updatedUsersCount = repository.update(updatedUser);
        assertEquals(1, updatedUsersCount);

        User dbUser = repository.get(updatedUser.getId());
        assertEquals(updatedUser, dbUser);
    }

    @Test
    void delete(){
        final List<User> addedUsers = new ArrayList<>(){{
            add(new User(13, "Olejka", 11, "Paris"));
            add(new User(14, "Vitalic", 12, "Sicilia"));
            add(new User(15, "Anastasiya", 13, "Budapesht"));
        }};
        for(var user : addedUsers)
            repository.insert(user);

        User deletedUser = repository.getAll().get(2);
        int dbCapacity = repository.getAll().size();
        int deletedLines = repository.delete(-1);
        assertEquals(0, deletedLines);

        deletedLines = repository.delete(deletedUser.getId());
        assertEquals(1, deletedLines);

        List<User> dbUsers = repository.getAll();
        assertEquals(dbCapacity - 1, dbUsers.size());

        for(var user : dbUsers)
            assertNotEquals(user, deletedUser);

        User dbUser = repository.get(deletedUser.getId());
        assertNull(dbUser);
    }
}
