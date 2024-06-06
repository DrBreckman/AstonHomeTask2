package eu.sedov.servlet;

import eu.sedov.model.User;
import eu.sedov.repository.mapper.impl.UserEnumMap;
import eu.sedov.service.UserService;
import eu.sedov.servlet.dto.InUserDTO;
import eu.sedov.servlet.dto.OutUserDTO;
import eu.sedov.servlet.mapper.UserMapperDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class UpdateUserServlet extends HttpServlet {
    private UserService service;
    private UserMapperDto mapper;
    private UserEnumMap map;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InUserDTO incomingDTO = new InUserDTO(
                Integer.parseInt(req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.ID))),
                req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.NAME)),
                Integer.parseInt(req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.AGE))),
                req.getParameter(map.getMap().get(UserEnumMap.UserResultSetParams.ADDRESS)));
        User user = mapper.map(incomingDTO);
        int updatedUsersCount = service.update(user);

        if (updatedUsersCount != 0){
            OutUserDTO outGoingDto = mapper.map(user);
            req.setAttribute("updatedUser", outGoingDto);
        }

        req.getRequestDispatcher("/update").forward(req, resp);
    }
}
