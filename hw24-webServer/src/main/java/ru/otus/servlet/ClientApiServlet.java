package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.model.User;

import java.io.IOException;

@WebServlet("/api/client/*")
public class ClientApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;
    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = gson.fromJson(req.getReader(), Client.class);
        Client savedClient = dbServiceClient.saveClient(client);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print(gson.toJson(savedClient));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length != 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        long clientId = Long.parseLong(pathParts[1]);
        Client clientToUpdate = gson.fromJson(req.getReader(), Client.class);
        clientToUpdate.setId(clientId);
        Client updatedClient = dbServiceClient.saveClient(clientToUpdate);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print(gson.toJson(updatedClient));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = dbServiceClient.getClient(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }
}