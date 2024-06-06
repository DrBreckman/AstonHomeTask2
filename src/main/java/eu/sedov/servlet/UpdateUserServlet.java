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

@WebServlet("/update")
public class UpdateUserServlet extends HttpServlet {
    private LibraryUserService service;
    private UserMapperDto mapper;
    private UserEnumMap map;

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
            int updatedUsersCount = service.update(user);

            if (updatedUsersCount != 0){
                OutUserDTO outGoingDto = mapper.map(user);
                req.setAttribute("updatedUser", outGoingDto);
            }
        }

        req.getRequestDispatcher("/update").forward(req, resp);
    }
}
