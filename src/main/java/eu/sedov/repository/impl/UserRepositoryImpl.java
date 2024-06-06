package eu.sedov.repository.impl;

import eu.sedov.dao.EntityDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.model.User;

import eu.sedov.model.UserBook;
import eu.sedov.model.UserReview;
import eu.sedov.repository.EntityRepositoryClass;
import eu.sedov.repository.mapper.EntityResultSetMapper;

import java.io.IOException;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends EntityRepositoryClass<User> {
    public UserRepositoryImpl(EntityResultSetMapper<User> mapper, ConnectionManager manager, EntityDAO dao) {
        super(mapper, manager, dao);
    }

    public List<UserBook> getUsersBooks(){
        List<UserBook> userBooks = new ArrayList<>();
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement("""
                    SELECT ticket.idUser, ticket.idBook
                    FROM ticket
                """)
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    userBooks.add(new UserBook(
                            resultSet.getInt(1),
                            resultSet.getInt(2)
                    ));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return userBooks;
    }

    public List<UserReview> getUsersReview(){
        List<UserReview> userReviews = new ArrayList<>();
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement("""
                    SELECT user.id, review.id
                    FROM review
                    INNER JOIN user ON review.userId = user.id
                """)
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    userReviews.add(new UserReview(
                            resultSet.getInt(1),
                            resultSet.getInt(2)
                    ));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return userReviews;
    }

    @Override
    protected PreparedStatement updatePreparedStatementSet(PreparedStatement state, User entity) throws SQLException {
        state.setString(1, entity.getName());
        state.setInt(2, entity.getAge());
        state.setString(3, entity.getAddress());
        state.setInt(4, entity.getId());
        return state;
    }

    @Override
    protected PreparedStatement insertPreparedStatementSet(PreparedStatement state, User entity) throws SQLException {
        state.setString(1, entity.getName());
        state.setInt(2, entity.getAge());
        state.setString(3, entity.getAddress());
        return state;
    }


}
