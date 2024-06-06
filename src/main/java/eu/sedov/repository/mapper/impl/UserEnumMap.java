package eu.sedov.repository.mapper.impl;

import eu.sedov.repository.mapper.EntityEnumMap;

import java.util.EnumMap;
import java.util.Map;

public class UserEnumMap implements EntityEnumMap<UserEnumMap.UserResultSetParams> {
    public enum UserResultSetParams {
        ID,
        NAME,
        AGE,
        ADDRESS
    }

    @Override
    public Map<UserResultSetParams, String> getMap() {
        Map<UserResultSetParams, String> map = new EnumMap<>(UserResultSetParams.class);
        map.put(UserResultSetParams.ID, "id");
        map.put(UserResultSetParams.NAME, "name");
        map.put(UserResultSetParams.AGE, "age");
        map.put(UserResultSetParams.ADDRESS, "address");
        return map;
    }
}
