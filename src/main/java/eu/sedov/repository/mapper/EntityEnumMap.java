package eu.sedov.repository.mapper;

import java.util.Map;

public interface EntityEnumMap<T extends Enum<T>> {
    Map<T, String> getMap();
}
