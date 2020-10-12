package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WrongAction implements Command {
    public static final String MESSAGE = "message";
    private static final String COMMAND_DO_NOT_UNDERSTAND = "Command do not understand";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute(MESSAGE, COMMAND_DO_NOT_UNDERSTAND);
    }
}
