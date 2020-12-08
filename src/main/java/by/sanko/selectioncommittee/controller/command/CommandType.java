package by.sanko.selectioncommittee.controller.command;

import by.sanko.selectioncommittee.controller.command.impl.*;

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
    LOGOUT(new LogOutCommand()),
    UPDATEINFO(new UpdateProfileCommand()),
    UPDATEPASSWORD(new UpdatePasswordCommand()),
    CHANGEPASS(new ChangePasswordCommand()),
    CHANGINGPASSWORD(new ChangingPasswordCommand()),
    SETNEWPASS(new SetNewPasswordCommand()),
    ADDEXAMTOFACULTY(new AddExamToFacultyCommand()),
    ADDFACULTY(new AddFacultyCommand()),
    ADDSTATEMENT(new AddStatementCommand()),
    DELETEEXAMFROMFACULTY(new DeleteExamFromFaculty()),
    DELETESTATEMENT(new DeleteStatementCommand()),
    DELETEEXAMFROMENROLLEE(new DeleteExamFromEnrolleeCommand()),
    GETFACULTIES(new GetAllFacultiesCommand()),
    SWITCHLOCATION(new SwitchLocationCommand()),
    GETALLEXAMSFROMFACULTY(new GetAllExamsOfFacultyCommand()),
    GETALLUSERSEXAM(new GetAllExamsOfUserCommand());

    private Command command;
    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
