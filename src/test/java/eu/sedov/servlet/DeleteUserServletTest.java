package eu.sedov.servlet;

import eu.sedov.model.User;
import eu.sedov.repository.mapper.impl.UserEnumMap;
import eu.sedov.service.impl.LibraryUserServiceImpl;
import eu.sedov.servlet.dto.OutUserDTO;
import eu.sedov.servlet.mapper.impl.UserMapperDtoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class DeleteUserServletTest {
    static String path = "/delete";
    @InjectMocks
    private DeleteUserServlet servlet;

    @Mock
    private LibraryUserServiceImpl service;
    @Mock
    private UserMapperDtoImpl mapper;
    @Mock
    private UserEnumMap userEnum;

    @Test
    void doPost() throws ServletException, IOException {
        User user = new User(1, "Nikita", 25, "Moscow");
        OutUserDTO dto = new OutUserDTO(user.getName(), user.getAddress());

        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(userEnum.getMap()).thenReturn(new UserEnumMap().getMap());
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(service.getById(1)).thenReturn(user);
        Mockito.when(service.delete(1)).thenReturn(1);
        Mockito.when(mapper.map(user)).thenReturn(dto);
        Mockito.when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher(path);
        Mockito.verify(request, Mockito.never()).getSession();
        Mockito.verify(dispatcher).forward(request, response);
    }
}