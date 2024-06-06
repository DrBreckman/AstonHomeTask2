package eu.sedov.servlet.mapper.impl;

import eu.sedov.model.User;
import eu.sedov.servlet.dto.InUserDTO;
import eu.sedov.servlet.dto.OutUserDTO;
import eu.sedov.servlet.mapper.UserMapperDto;

public class UserMapperDtoImpl implements UserMapperDto {
    @Override
    public User map(InUserDTO incomingDto) {
        return new User(
                incomingDto.id(),
                incomingDto.name(),
                incomingDto.age(),
                incomingDto.address()
        );
    }

    @Override
    public OutUserDTO map(User entity) {
        return new OutUserDTO(
                entity.getName(),
                entity.getAddress()
        );
    }
}
