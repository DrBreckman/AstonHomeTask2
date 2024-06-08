package eu.sedov.repository.impl;

import eu.sedov.dao.impl.BookDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.db.impl.ConnectionSQL;
import eu.sedov.model.Book;
import eu.sedov.repository.mapper.impl.BookResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryImplTest {

    private BookRepositoryImpl repository;
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
        repository = new BookRepositoryImpl(new BookResultSetMapperImpl(), manager, new BookDAO());
    }

    @Test
    void insert(){
        int currentSize = repository.getAll().size();
        final List<Book> bookList = new ArrayList<>(){{
            add(new Book(0, "Hamlet", "William Shakespeare"));
            add(new Book(0, "Jane Eyre", "Charlotte Brontë"));
            add(new Book(0, "Great Expectations", "Great Expectations"));
            add(new Book(0, "Dubliners", "James Joyce"));
        }};
        for(var book : bookList)
            repository.insert(book);

        List<Book> dbBooks = repository.getAll();
        assertEquals(currentSize + bookList.size(), dbBooks.size());
    }

    @Test
    void get(){
        final List<Book> bookList = new ArrayList<>(){{
            add(new Book(0, "Wuthering Heights", "Emily Brontë"));
            add(new Book(0, "Pride and Prejudice", "Jane Austen"));
            add(new Book(0, "Tess of the d'Urbervilles ", "Thomas Hardy"));
        }};
        for(var book : bookList)
            repository.insert(book);

        Book dbBook = repository.get(-1);
        assertNull(dbBook);

        Book randomBook = repository.getAll().get(2);
        dbBook = repository.get(randomBook.getId());
        assertEquals(randomBook, dbBook);
    }

    @Test
    void update(){
        final List<Book> bookList = new ArrayList<>(){{
            add(new Book(0, "Hamlet", "William Shakespeare"));
            add(new Book(0, "Jane Eyre", "Charlotte Brontë"));
            add(new Book(0, "Great Expectations", "Great Expectations"));
            add(new Book(0, "Dubliners", "James Joyce"));
        }};
        for(var book : bookList)
            repository.insert(book);

        Book updateBook = repository.getAll().get(3);
        updateBook.setName("AuthoBio");
        updateBook.setAuthor("Nikita");

        int updatedLines = repository.update(new Book(-1, "Test", "Zero"));
        assertEquals(0, updatedLines);

        updatedLines = repository.update(updateBook);
        assertEquals(1, updatedLines);

        Book dbBook = repository.get(updateBook.getId());
        assertEquals(updateBook, dbBook);
    }

    @Test
    void delete(){
        final List<Book> bookList = new ArrayList<>(){{
            add(new Book(0, "Wuthering Heights", "Emily Brontë"));
            add(new Book(0, "Pride and Prejudice", "Jane Austen"));
            add(new Book(0, "Tess of the d'Urbervilles ", "Thomas Hardy"));
        }};
        for(var book : bookList)
            repository.insert(book);

        Book deletedBook = repository.getAll().get(0);
        int dbCapacity = repository.getAll().size();
        int deletedLines = repository.delete(-1);
        assertEquals(0, deletedLines);

        deletedLines = repository.delete(deletedBook.getId());
        assertEquals(1, deletedLines);

        List<Book> dbBooks = repository.getAll();
        assertEquals(dbCapacity - 1, dbBooks.size());

        for(var book : dbBooks)
            assertNotEquals(book, deletedBook);

        Book dbBook = repository.get(deletedBook.getId());
        assertNull(dbBook);
    }



























}