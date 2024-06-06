package eu.sedov.repository.impl;

import eu.sedov.db.ConnectionManager;
import eu.sedov.model.User;
import eu.sedov.repository.UserRepository;
import eu.sedov.repository.mapper.UserResultSetMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserRepositoryImpl implements UserRepository {
    private final UserResultSetMapper mapper;
    private final ConnectionManager manager;

    public UserRepositoryImpl(UserResultSetMapper mapper, ConnectionManager manager){
        this.mapper = mapper;
        this.manager = manager;
        createTableIfNotExist();
    }

    private void createTableIfNotExist(){
        try (Connection conn = this.manager.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS user (
                    `id` INT NOT NULL AUTO_INCREMENT,
                    `name` VARCHAR(45) NOT NULL,
                    `age` INT NOT NULL,
                    `address` VARCHAR(45) NOT NULL,
                    PRIMARY KEY (`id`),
                    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
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
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement("""
                  SELECT user.id, user.name, user.age, user.address
                  FROM user;
               """
                )
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    users.add(mapper.map(resultSet));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User get(int id) {
        User user = null;
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement("""
                        SELECT user.id, user.name, user.age, user.address
                        FROM user
                        WHERE id = ?
                    """)
            ){
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    user = mapper.map(resultSet);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public int delete(int id) {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement("""
                        DELETE FROM user
                        WHERE id = ?
                    """)
            ){
                preparedStatement.setInt(1, id);
                return  preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement("""
                        UPDATE user
                        SET name = ?, age = ?, address = ?
                        WHERE id = ?
                    """)
            ){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setInt(2, user.getAge());
                preparedStatement.setString(3, user.getAddress());
                preparedStatement.setInt(4, user.getId());
                return  preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int insert(User user) {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement("""
                        INSERT INTO user (name, age, address)
                        Values (?, ?, ?)
                    """)
            ){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setInt(2, user.getAge());
                preparedStatement.setString(3, user.getAddress());
                return  preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
