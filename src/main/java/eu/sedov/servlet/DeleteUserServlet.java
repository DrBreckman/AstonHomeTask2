package eu.sedov.servlet;

import eu.sedov.model.User;
import eu.sedov.service.UserService;
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
    private UserService service;
    private UserMapperDto mapper;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        User user = service.getById(id);
        int deletedUsersCount = service.delete(id);

        if (deletedUsersCount != 0){
            OutUserDTO deletedUserDto = mapper.map(user);
            req.setAttribute("deletedUser", deletedUserDto);
        }

        req.getRequestDispatcher("/delete").forward(req, resp);
    }
}