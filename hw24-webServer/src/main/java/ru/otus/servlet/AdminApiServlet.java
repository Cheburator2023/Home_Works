package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.dao.UserDao;

import java.io.IOException;

public class AdminApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;
    private final UserDao userDao;
    private final Gson gson;

    public AdminApiServlet(UserDao userDao, Gson gson) {
        this.userDao = userDao;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().println(gson.toJson(userDao.findById(extractIdFromRequest(req)).orElse(null)));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    private long extractIdFromRequest(HttpServletRequest req) {
        String[] path = req.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }
}