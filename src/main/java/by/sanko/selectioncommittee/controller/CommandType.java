package by.sanko.selectioncommittee.controller;

import by.sanko.selectioncommittee.controller.impl.GetAllFacultiesCommand;
import by.sanko.selectioncommittee.controller.impl.WrongAction;

public enum  CommandType {
    WRONG_COMMAND(new WrongAction()),
    GETFACULTIES(new GetAllFacultiesCommand());

    private Command command;
    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
