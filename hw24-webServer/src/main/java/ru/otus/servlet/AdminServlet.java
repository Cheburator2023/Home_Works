package ru.otus.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.otus.dao.UserDao;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private final TemplateProcessor templateProcessor;

    private final UserDao userDao;

    public AdminServlet(TemplateProcessor templateProcessor, UserDao userDao) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/adminLogin.html");
            return;
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, null));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}