package eu.sedov.repository.impl;

import eu.sedov.dao.EntityDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.model.Book;
import eu.sedov.repository.EntityRepositoryClass;
import eu.sedov.repository.mapper.EntityResultSetMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookRepositoryImpl extends EntityRepositoryClass<Book> {
    public BookRepositoryImpl(EntityResultSetMapper<Book> mapper, ConnectionManager manager, EntityDAO dao) {
        super(mapper, manager, dao);
    }

    @Override
    protected PreparedStatement updatePreparedStatementSet(PreparedStatement state, Book entity) throws SQLException {
        state.setString(1, entity.getName());
        state.setString(2, entity.getAuthor());
        state.setInt(3, entity.getId());
        return state;
    }

    @Override
    protected PreparedStatement insertPreparedStatementSet(PreparedStatement state, Book entity) throws SQLException {
        state.setString(1, entity.getName());
        state.setString(2, entity.getAuthor());
        return state;
    }
}
