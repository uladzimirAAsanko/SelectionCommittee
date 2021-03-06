package by.sanko.selectioncommittee.entity;

import java.io.Serializable;
import java.util.Objects;

public class Faculty implements Serializable {
    private int facultyID;
    private String facultyName;
    private String facultySite;
    private String facultyAvatar;

    public Faculty(int facultyID,String facultyName, String facultySite, String facultyAvatar) {
        this.facultyID = facultyID;
        this.facultySite = facultySite;
        this.facultyName = facultyName;
        this.facultyAvatar = facultyAvatar;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public int getFacultyID() {
        return facultyID;
    }

    public String getFacultySite() {
        return facultySite;
    }

    public String getFacultyAvatar() {
        return facultyAvatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return getFacultyID() == faculty.getFacultyID() &&
                Objects.equals(getFacultySite(), faculty.getFacultySite()) &&
                Objects.equals(getFacultyName(), faculty.getFacultyName()
                );
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + facultyID;
        result = 31 * result + facultySite.hashCode();
        result = 31 * result + facultyName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Faculty{");
        stringBuilder.append("facultyID=").append(facultyID);
        stringBuilder.append(", facultyName='").append(facultyName).append('\'');
        stringBuilder.append(", facultySite='").append(facultySite).append('\'').append("}");
        return stringBuilder.toString();
    }
}
