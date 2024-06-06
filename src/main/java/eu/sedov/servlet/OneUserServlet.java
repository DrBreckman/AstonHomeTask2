package eu.sedov.servlet;

import eu.sedov.model.User;
import eu.sedov.repository.mapper.impl.UserEnumMap;
import eu.sedov.service.LibraryUserService;
import eu.sedov.servlet.dto.InUserDTO;
import eu.sedov.servlet.dto.OutUserDTO;
import eu.sedov.servlet.mapper.UserMapperDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class OneUserServlet extends HttpServlet {
    private LibraryUserService service;
    private UserMapperDto mapper;
    private UserEnumMap map;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringId = req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.ID));
        if (stringId != null){
            Integer id = Integer.parseInt(stringId);
            final User user = service.getById(id);

            if (user != null){
                final OutUserDTO dto = mapper.map(user);
                req.setAttribute("user", dto);
            }
        }

        req.getRequestDispatcher("/user").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringId = req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.ID));
        String stringName = req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.NAME));
        String stringAge = req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.AGE));
        String stringAddress = req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.ADDRESS));

        if (stringId != null){
            InUserDTO incomingDTO = new InUserDTO(
                    Integer.parseInt(stringId),
                    stringName,
                    stringAge == null ? null : Integer.parseInt(stringAge),
                    stringAddress
            );

            User user = mapper.map(incomingDTO);
            service.insert(user);
            OutUserDTO outGoingDto = mapper.map(user);
            req.setAttribute("addedUser", outGoingDto);
        }
        req.getRequestDispatcher("/user").forward(req, resp);
    }
}
