package eu.sedov.repository.mapper.impl;

import eu.sedov.dao.EntityDAO;
import eu.sedov.dao.impl.BookDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.db.impl.ConnectionSQL;
import eu.sedov.model.Book;
import eu.sedov.repository.EntityRepositoryClass;
import eu.sedov.repository.impl.BookRepositoryImpl;
import eu.sedov.repository.mapper.BookResultSetMapper;
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

class BookResultSetMapperImplTest {

    private BookResultSetMapper mapper;
    private ConnectionManager manager;
    private EntityDAO dao;

    private final List<Book> bookList = new ArrayList<>(){{
        add(new Book(1, "book1", "author1"));
        add(new Book(2, "book2", "author2"));
        add(new Book(3, "book3", "author3"));
        add(new Book(4, "book4", "author4"));
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
        mapper = new BookResultSetMapperImpl();
        dao = new BookDAO();
        EntityRepositoryClass<Book> repository = new BookRepositoryImpl(mapper, manager, dao);

        for(var book : bookList)
            repository.insert(book);
    }

    @Test
    void map() {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(dao.getAll())
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    assertTrue(bookList.contains(mapper.map(resultSet)));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}