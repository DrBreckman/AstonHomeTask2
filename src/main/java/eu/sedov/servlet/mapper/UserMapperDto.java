package eu.sedov.servlet.mapper;

import eu.sedov.model.User;
import eu.sedov.servlet.dto.InUserDTO;
import eu.sedov.servlet.dto.OutUserDTO;

public interface UserMapperDto {
    User map(InUserDTO incomingDto);
    OutUserDTO map(User entity);
}
