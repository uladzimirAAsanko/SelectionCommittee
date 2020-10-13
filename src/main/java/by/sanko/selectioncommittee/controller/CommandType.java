package by.sanko.selectioncommittee.controller;

import by.sanko.selectioncommittee.controller.impl.AuthorizationCommand;
import by.sanko.selectioncommittee.controller.impl.GetAllFacultiesCommand;
import by.sanko.selectioncommittee.controller.impl.RegistrationCommand;
import by.sanko.selectioncommittee.controller.impl.WrongAction;

public enum  CommandType {
    WRONG_COMMAND(new WrongAction()),
    LOGIN(new AuthorizationCommand()),
    REGISTRATION(new RegistrationCommand()),
    GETFACULTIES(new GetAllFacultiesCommand());

    private Command command;
    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
