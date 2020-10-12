package by.sanko.selectioncommittee.controller;

import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Controller extends HttpServlet {
    private static final String COMMAND = "command";
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    public void destroy() {
        ConnectionPool.getINSTANCE().destroyPool();
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String commandName = req.getParameter(COMMAND);
        logger.log(Level.INFO, "Command name - {}", commandName);
        CommandProvider provider = CommandProvider.getInstance();
        Command command = provider.defineCommand(commandName);
        command.execute(req,resp);
    }
}
