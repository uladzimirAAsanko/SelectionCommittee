package by.sanko.selectioncommittee.entity;

import java.io.Serializable;
import java.util.Objects;

public class Enrollee extends User implements Serializable {
    private int certificate;
    private String additionalInfo;

    public Enrollee(int userID, String firstName, String lastName, String fathersName, String login, String email, int certificate,String additionalInfo) {
        super(userID, firstName, lastName, fathersName, login, email, UsersRole.ENROLLEE);
         this.additionalInfo = additionalInfo;
         this.certificate = certificate;
    }

    public int getCertificate() {
        return certificate;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Enrollee enrollee = (Enrollee) o;
        return getCertificate() == enrollee.getCertificate() &&
                Objects.equals(getAdditionalInfo(), enrollee.getAdditionalInfo());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + certificate;
        result = 31 * result + additionalInfo.hashCode();
        return result;
    }

    //TODO FINISH UP
    @Override
    public String toString() {
        return "Enrollee{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
