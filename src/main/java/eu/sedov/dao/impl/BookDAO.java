package eu.sedov.dao.impl;

import eu.sedov.dao.EntityDAO;

public class BookDAO implements EntityDAO {
    @Override
    public String createTableIfNotExist() {
        return """
            CREATE TABLE IF NOT EXISTS book (
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(45),
            `author` varchar(45),
            PRIMARY KEY (`id`),
            UNIQUE KEY `id_UNIQUE` (`id`) );
        """;
    }

    @Override
    public String getAll() {
        return """
            SELECT book.id, book.name, book.author
            FROM book;
        """;
    }

    @Override
    public String getById() {
        return """
            SELECT book.id, book.name, book.author
            FROM book
            WHERE id = ?
        """;
    }

    @Override
    public String insert() {
        return """
            INSERT INTO book (name, author)
            Values (?, ?)
        """;
    }

    @Override
    public String delete() {
        return """
            DELETE FROM book
            WHERE id = ?
        """;
    }

    @Override
    public String update() {
        return """
            UPDATE book
            SET name = ?, author = ?
            WHERE id = ?
        """;
    }
}
