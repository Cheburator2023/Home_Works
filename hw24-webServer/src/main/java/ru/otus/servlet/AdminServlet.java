package ru.otus.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private final TemplateProcessor templateProcessor;

    private final DBServiceClient dbServiceClient;

    public AdminServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/admin/login");
        } else {
            Map<String, Object> params = new HashMap<>();
            List<Client> clients = dbServiceClient.findAll();
            if (!clients.isEmpty()) {
                Client firstClient = clients.get(0);
                params.put("client", firstClient);
            }

            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, params));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}