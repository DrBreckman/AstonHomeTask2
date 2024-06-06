package eu.sedov.dao.impl;

import eu.sedov.dao.EntityDAO;

public class UserDAO implements EntityDAO {
    @Override
    public String createTableIfNotExist() {
        return """
                    CREATE TABLE IF NOT EXISTS user (
                    `id` INT NOT NULL AUTO_INCREMENT,
                    `name` VARCHAR(45) NOT NULL,
                    `age` INT NOT NULL,
                    `address` VARCHAR(45) NOT NULL,
                    PRIMARY KEY (`id`),
                    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
                """;
    }

    @Override
    public String getAll() {
        return """
                  SELECT user.id, user.name, user.age, user.address
                  FROM user;
               """;
    }

    @Override
    public String getById() {
        return """
                        SELECT user.id, user.name, user.age, user.address
                        FROM user
                        WHERE id = ?
                    """;
    }

    @Override
    public String insert() {
        return """
                        INSERT INTO user (name, age, address)
                        Values (?, ?, ?)
            """;
    }

    @Override
    public String delete() {
        return """
                        DELETE FROM user
                        WHERE id = ?
                    """;
    }

    @Override
    public String update() {
        return """
                        UPDATE user
                        SET name = ?, age = ?, address = ?
                        WHERE id = ?
                    """;
    }
}
