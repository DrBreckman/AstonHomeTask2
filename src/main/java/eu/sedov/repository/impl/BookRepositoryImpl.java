package eu.sedov.repository.impl;

import eu.sedov.db.ConnectionManager;
import eu.sedov.model.Book;
import eu.sedov.model.User;
import eu.sedov.repository.BookRepository;
import eu.sedov.repository.mapper.BookResultSetMapper;
import eu.sedov.repository.mapper.UserResultSetMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    private final BookResultSetMapper mapper;
    private final ConnectionManager manager;

    public BookRepositoryImpl(BookResultSetMapper mapper, ConnectionManager manager){
        this.mapper = mapper;
        this.manager = manager;
        createTableIfNotExist();
    }

    private void createTableIfNotExist(){
        try (Connection conn = this.manager.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS book (
                    `id` int NOT NULL AUTO_INCREMENT,
                    `name` varchar(45) NOT NULL,
                    `author` varchar(45) NOT NULL,
                    PRIMARY KEY (`id`),
                    UNIQUE KEY `id_UNIQUE` (`id`) );
                """
            )
            ){
                statement.execute();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement("""
                  SELECT book.id, book.name, book.author
                  FROM book;
               """
            )
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    books.add(mapper.map(resultSet));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book get(int id) {
        return null;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(Book entity) {
        return 0;
    }

    @Override
    public int insert(Book entity) {
        return 0;
    }
}
