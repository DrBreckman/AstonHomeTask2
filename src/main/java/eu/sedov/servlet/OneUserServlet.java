package eu.sedov.servlet;

import eu.sedov.model.User;
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

@WebServlet("/user")
public class OneUserServlet extends HttpServlet {
    private UserService service;
    private UserMapperDto mapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = service.getById(Integer.parseInt(req.getParameter("id")));
        final OutUserDTO dto = mapper.map(user);

        req.setAttribute("user", dto);
        req.getRequestDispatcher("/user").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InUserDTO incomingDTO = new InUserDTO(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"),
                Integer.parseInt(req.getParameter("age")),
                req.getParameter("address"));
        User user = mapper.map(incomingDTO);
        service.insert(user);
        OutUserDTO outGoingDto = mapper.map(user);

        req.setAttribute("addedUser", outGoingDto);
        req.getRequestDispatcher("/user").forward(req, resp);
    }
}
