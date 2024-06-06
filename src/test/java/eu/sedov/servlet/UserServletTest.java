package eu.sedov.servlet;

import eu.sedov.model.User;
import eu.sedov.service.impl.UserServiceImpl;
import eu.sedov.servlet.dto.InUserDTO;
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
class UserServletTest {
    static String path = "/user";

    @InjectMocks
    static OneUserServlet servlet;

    @Mock
    static UserServiceImpl service;
    @Mock
    static UserMapperDtoImpl mapper;

    @Test
    void doGet() throws ServletException, IOException {
        User user = new User(1, "Nikita", 25, "Moscow");
        OutUserDTO dto = new OutUserDTO(user.getName(), user.getAddress());

        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(service.getById(1)).thenReturn(user);
        Mockito.when(mapper.map(user)).thenReturn(dto);
        Mockito.when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher(path);
        Mockito.verify(request, Mockito.never()).getSession();
        Mockito.verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        User user = new User(1, "Nikita", 25, "Moscow");
        InUserDTO inDto = new InUserDTO(user.getId(), user.getName(), user.getAge(), user.getAddress());
        OutUserDTO outDto = new OutUserDTO(user.getName(),user.getAddress());

        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        Mockito.when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        Mockito.when(request.getParameter("name")).thenReturn(user.getName());
        Mockito.when(request.getParameter("age")).thenReturn(String.valueOf(user.getAge()));
        Mockito.when(request.getParameter("address")).thenReturn(user.getAddress());
        Mockito.when(mapper.map(inDto)).thenReturn(user);
        Mockito.when(service.insert(user)).thenReturn(1);
        Mockito.when(mapper.map(user)).thenReturn(outDto);

        servlet.doPost(request, response);

        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/user");
        Mockito.verify(request, Mockito.never()).getSession();
        Mockito.verify(dispatcher).forward(request, response);
    }
}
