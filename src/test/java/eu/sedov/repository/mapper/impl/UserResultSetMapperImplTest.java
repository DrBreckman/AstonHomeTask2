package eu.sedov.repository.mapper.impl;

import eu.sedov.dao.EntityDAO;
import eu.sedov.dao.impl.UserDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.db.impl.ConnectionSQL;
import eu.sedov.model.User;
import eu.sedov.repository.EntityRepositoryClass;
import eu.sedov.repository.impl.UserRepositoryImpl;
import eu.sedov.repository.mapper.UserResultSetMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserResultSetMapperImplTest {
    private UserResultSetMapper mapper;
    private ConnectionManager manager;
    private EntityDAO dao;
    private final List<User> users = new ArrayList<>(){{
        add(new User(1, "Nikita", 25, "Moscow"));
        add(new User(2, "Masha", 19, "Italy"));
        add(new User(3, "Oleg", 26, "Belarus"));
        add(new User(4, "Vlad", 74, "Vasuki-17"));
    }};

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
        manager = new ConnectionSQL(
                mySQLcontainer.getJdbcUrl(),
                mySQLcontainer.getUsername(),
                mySQLcontainer.getPassword()
        );
        mapper = new UserResultSetMapperImpl();
        dao = new UserDAO();
        EntityRepositoryClass<User> repository = new UserRepositoryImpl(mapper, manager, dao);

        for(var user : users)
            repository.insert(user);
    }

    @Test
    void map() {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(dao.getAll())
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    assertTrue(users.contains(mapper.map(resultSet)));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
