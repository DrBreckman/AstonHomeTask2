package eu.sedov.service.impl;

import eu.sedov.model.User;
import eu.sedov.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private static UserServiceImpl service;

    @Mock
    private static UserRepositoryImpl repository;

    @Test
    void getById(){
        User user = new User(1, "Nikita", 25, "Gomel");
        Mockito.when(repository.get(-1)).thenReturn(null);
        Mockito.when(repository.get(1)).thenReturn(user);

        User dbUser = service.getById(-1);
        assertNull(dbUser);
        dbUser = service.getById(1);
        assertEquals(user, dbUser);
    }

    @Test
    void insert(){
        User user = new User(1, "Nikita", 25, "Gomel");
        Mockito.when(repository.insert(user)).thenReturn(1);

        assertEquals(1, service.insert(user));
    }
}
