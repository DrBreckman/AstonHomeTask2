package eu.sedov.repository.impl;

import eu.sedov.dao.EntityDAO;
import eu.sedov.db.ConnectionManager;
import eu.sedov.model.Review;
import eu.sedov.repository.EntityRepositoryClass;
import eu.sedov.repository.mapper.EntityResultSetMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewRepositoryImpl extends EntityRepositoryClass<Review> {
    public ReviewRepositoryImpl(EntityResultSetMapper<Review> mapper, ConnectionManager manager, EntityDAO dao) {
        super(mapper, manager, dao);
    }

    @Override
    protected PreparedStatement updatePreparedStatementSet(PreparedStatement state, Review entity) throws SQLException {
        state.setInt(1, entity.getMark());
        state.setString(2, entity.getDescription());
        state.setInt(3, entity.getId());
        return state;
    }

    @Override
    protected PreparedStatement insertPreparedStatementSet(PreparedStatement state, Review entity) throws SQLException {
        state.setInt(1, entity.getMark());
        state.setString(2, entity.getDescription());
        return state;
    }
}
