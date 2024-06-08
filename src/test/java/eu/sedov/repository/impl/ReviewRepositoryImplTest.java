package eu.sedov.repository.impl;

import eu.sedov.dao.impl.ReviewDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.db.impl.ConnectionSQL;
import eu.sedov.model.Review;
import eu.sedov.repository.mapper.impl.ReviewResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReviewRepositoryImplTest {
    private ReviewRepositoryImpl repository;

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
        repository = new ReviewRepositoryImpl(new ReviewResultSetMapperImpl(), manager, new ReviewDAO());
    }

    @Test
    void insert(){
        int currentRepositorySize = repository.getAll().size();
        final List<Review> reviewList = new ArrayList<>() {{
            add(new Review(1, 4, "very good but not 5"));
            add(new Review(2, 5, "perfect... so perfect"));
            add(new Review(3, 3, "so so"));
            add(new Review(4, 2, "omg so bad"));
        }};
        for(var review : reviewList)
            repository.insert(review);

        List<Review> dbReviewList = repository.getAll();
        assertEquals(currentRepositorySize + reviewList.size(), dbReviewList.size());
    }

    @Test
    void get(){
        final List<Review> reviewList = new ArrayList<>(){{
            add(new Review(4, 2, "very bad comedy"));
            add(new Review(5, 1, "my eyes!"));
            add(new Review(6, 2, "dont look at this pls"));
        }};
        for(var review : reviewList)
            repository.insert(review);

        Review dbReview = repository.get(-1);
        assertNull(dbReview);

        Review randomReview = repository.getAll().get(2);
        dbReview = repository.get(randomReview.getId());
        assertEquals(randomReview, dbReview);
    }

    @Test
    void update(){
        final List<Review> reviewList = new ArrayList<>(){{
            add(new Review(0, 3, "try better next time"));
            add(new Review(0, 4, "exactly perfect"));
            add(new Review(0, 3, "better then zero"));
        }};
        for(var review : reviewList)
            repository.insert(review);

        Review updatedReview = repository.getAll().get(1);
        updatedReview.setMark(5);
        updatedReview.setDescription("i was wrong, this is perf");

        int updatedLines = repository.update(new Review(-1, 1, "desc"));
        assertEquals(0, updatedLines);

        updatedLines = repository.update(updatedReview);
        assertEquals(1, updatedLines);

        Review dbReview = repository.get(updatedReview.getId());
        assertEquals(updatedReview, dbReview);
    }

    @Test
    void delete(){
        final List<Review> reviewList = new ArrayList<>(){{
            add(new Review(0, 5, "best of the best"));
            add(new Review(0, 1, "worst of the worst"));
        }};
        for(var review : reviewList)
            repository.insert(review);

        Review deletedReview = repository.getAll().get(0);
        int dbSize = repository.getAll().size();
        int deletedLines = repository.delete(-1);
        assertEquals(0, deletedLines);

        deletedLines = repository.delete(deletedReview.getId());
        assertEquals(1, deletedLines);

        List<Review> dbReviewList = repository.getAll();
        assertEquals(dbSize - 1, dbReviewList.size());

        for(var review : dbReviewList)
            assertNotEquals(review, deletedReview);

        Review dbReview = repository.get(deletedReview.getId());
        assertNull(dbReview);
    }


























}