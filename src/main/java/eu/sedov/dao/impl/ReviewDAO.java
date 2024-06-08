package eu.sedov.dao.impl;

import eu.sedov.dao.EntityDAO;

public class ReviewDAO implements EntityDAO {
    @Override
    public String createTableIfNotExist() {
        return """
            CREATE TABLE IF NOT EXISTS review  (
            `id` int NOT NULL AUTO_INCREMENT,
            `mark` int,
            `description` varchar(45),
            `bookId` int,
            `userId` int,
            PRIMARY KEY (`id`));
        """;
    }

    @Override
    public String getAll() {
        return """
            SELECT review.id, review.mark, review.description
            FROM review;
        """;
    }

    @Override
    public String getById() {
        return """
            SELECT review.id, review.mark, review.description
            FROM review
            WHERE id = ?
        """;
    }

    @Override
    public String insert() {
        return """
            INSERT INTO review (mark, description)
            Values (?, ?)
        """;
    }

    @Override
    public String delete() {
        return """
            DELETE FROM review
            WHERE id = ?
        """;
    }

    @Override
    public String update() {
        return """
            UPDATE review
            SET mark = ?, description = ?
            WHERE id = ?
        """;
    }
}
