package eu.sedov.servlet;

import eu.sedov.model.User;
import eu.sedov.repository.mapper.impl.UserEnumMap;
import eu.sedov.service.LibraryUserService;
import eu.sedov.servlet.dto.OutUserDTO;
import eu.sedov.servlet.mapper.UserMapperDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {
    private LibraryUserService service;
    private UserMapperDto mapper;
    private UserEnumMap userEnum;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var stringId = req.getParameter(userEnum.getMap().get(UserEnumMap.UserResultSetParams.ID));
        if (stringId != null){
            Integer id =Integer.parseInt(stringId);
            User user = service.getById(id);
            int deletedUsersCount = service.delete(id);

            if (deletedUsersCount != 0){
                OutUserDTO deletedUserDto = mapper.map(user);
                req.setAttribute("deletedUser", deletedUserDto);
            }
        }

        req.getRequestDispatcher("/delete").forward(req, resp);
    }
}
