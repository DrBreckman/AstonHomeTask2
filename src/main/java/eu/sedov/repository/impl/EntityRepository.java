package eu.sedov.repository.impl;

import eu.sedov.dao.EntityDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.model.User;
import eu.sedov.repository.mapper.EntityResultSetMapper;
import eu.sedov.repository.mapper.UserResultSetMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityRepository<T> implements eu.sedov.repository.EntityRepository<T> {

    private final EntityResultSetMapper<T> mapper;
    private final ConnectionManager manager;
    private final EntityDAO dao;

    protected EntityRepository(EntityResultSetMapper<T> mapper, ConnectionManager manager, EntityDAO dao){
        this.mapper = mapper;
        this.manager = manager;
        this.dao = dao;
        createTableIfNotExist();
    }

    private void createTableIfNotExist(){
        try (Connection conn = this.manager.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(dao.createTableIfNotExist())
            ){
                statement.execute();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(dao.getAll())
            ){
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    entities.add(mapper.map(resultSet));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return entities;
    }

    @Override
    public T get(int id) {
        T entity = null;
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement(dao.getById())
            ){
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    entity = mapper.map(resultSet);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public int delete(int id) {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement(dao.delete())
            ){
                preparedStatement.setInt(1, id);
                return  preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(T entity) {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement(dao.update())
            ){
                return updatePreparedStatementSet(preparedStatement).executeUpdate();
               /* preparedStatement.setString(1, entity.getName());
                preparedStatement.setInt(2, entity.getAge());
                preparedStatement.setString(3, entity.getAddress());
                preparedStatement.setInt(4, entity.getId());
                return  preparedStatement.executeUpdate();*/
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int insert(T entity) {
        try (Connection conn = manager.getConnection()){
            try(PreparedStatement preparedStatement = conn.prepareStatement(dao.insert())
            ){
                return insertPreparedStatementSet(preparedStatement).executeUpdate();
                /*preparedStatement.setString(1, entity.getName());
                preparedStatement.setInt(2, entity.getAge());
                preparedStatement.setString(3, entity.getAddress());
                return  preparedStatement.executeUpdate();*/
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract PreparedStatement updatePreparedStatementSet(PreparedStatement state);
    protected abstract PreparedStatement insertPreparedStatementSet(PreparedStatement state);


}
