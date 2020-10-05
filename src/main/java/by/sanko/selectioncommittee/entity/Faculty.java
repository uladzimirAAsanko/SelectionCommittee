package by.sanko.selectioncommittee.entity;

import java.util.Objects;

public class Faculty {
    private int facultyID;
    private String facultyName;
    private String facultySite;

    public Faculty(int facultyID,String facultyName, String facultySite) {
        this.facultyID = facultyID;
        this.facultySite = facultySite;
        this.facultyName = facultyName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
