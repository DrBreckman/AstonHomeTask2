package eu.sedov.repository.mapper.impl;

import eu.sedov.model.User;
import eu.sedov.repository.mapper.UserResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapperImpl implements UserResultSetMapper {
    private final UserEnumMap map = new UserEnumMap();
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt(map.getMap().get(UserEnumMap.UserResultSetParams.ID)),
                resultSet.getString(map.getMap().get(UserEnumMap.UserResultSetParams.NAME)),
                resultSet.getInt(map.getMap().get(UserEnumMap.UserResultSetParams.AGE)),
                resultSet.getString(map.getMap().get(UserEnumMap.UserResultSetParams.ADDRESS))
        );
    }
}
