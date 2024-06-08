package eu.sedov.repository.mapper.impl;

import eu.sedov.dao.EntityDAO;
import eu.sedov.dao.impl.ReviewDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.db.impl.ConnectionSQL;
import eu.sedov.model.Review;
import eu.sedov.repository.EntityRepositoryClass;
import eu.sedov.repository.impl.ReviewRepositoryImpl;
import eu.sedov.repository.mapper.ReviewResultSetMapper;
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

class ReviewResultSetMapperImplTest {

   private ReviewResultSetMapper mapper;
   private ConnectionManager manager;
   private EntityDAO dao;

   private final List<Review> reviewList = new ArrayList<>(){{
       add(new Review(1, 4, "very good but not 5"));
       add(new Review(2, 5, "perfect... so perfect"));
       add(new Review(3, 3, "so so"));
       add(new Review(4, 2, "omg so bad"));
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
        mapper = new ReviewResultSetMapperImpl();
        dao = new ReviewDAO();
        EntityRepositoryClass<Review> repository = new ReviewRepositoryImpl(mapper, manager, dao);

        for(var review : reviewList)
            repository.insert(review);
    }

    @Test
    void map() {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(dao.getAll())
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    assertTrue(reviewList.contains(mapper.map(resultSet)));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}