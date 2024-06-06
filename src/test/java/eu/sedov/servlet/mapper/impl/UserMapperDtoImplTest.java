package eu.sedov.servlet.mapper.impl;

import eu.sedov.model.User;
import eu.sedov.servlet.dto.InUserDTO;
import eu.sedov.servlet.dto.OutUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperDtoImplTest {

    @Mock
    private UserMapperDtoImpl mapper;

    @Test
    void map(){
        User user = new User(1, "Nikita", 25, "Belarus");
        InUserDTO inDto = new InUserDTO(user.getId(), user.getName(), user.getAge(), user.getAddress());
        OutUserDTO outDto = new OutUserDTO(user.getName(), user.getAddress());

        Mockito.when(mapper.map(inDto)).thenReturn(user);
        Mockito.when(mapper.map(user)).thenReturn(outDto);

        assertEquals(user, mapper.map(new InUserDTO(1, "Nikita", 25, "Belarus")));
        assertEquals(outDto, mapper.map(new User(1, "Nikita", 25, "Belarus")));
    }
}
