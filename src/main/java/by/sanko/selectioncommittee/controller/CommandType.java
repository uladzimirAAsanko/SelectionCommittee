package by.sanko.selectioncommittee.controller;

import by.sanko.selectioncommittee.controller.impl.*;

public enum  CommandType {
    //TODO Make Session
    WRONG_COMMAND(new WrongAction()),
    AUTHORIZATION(new AuthorizationCommand()),
    AUTOLOGGING(new AutoLoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    REGISTRATION_ADMIN(new RegistrationAdminCommand()),
    REGISTRATIONENROLLEE(new RegistrationEnrolleeCommand()),
    ADDADMINCODE(new AddAdminCodeCommand()),
    ADDEXAM(new AddExamCommand()),
    GETFACULTIES(new GetAllFacultiesCommand());

    private Command command;
    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
