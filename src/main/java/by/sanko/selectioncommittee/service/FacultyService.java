package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.ServiceException;

import java.util.List;

public interface FacultyService {
    public List getAllFaculties() throws ServiceException;
    public String transformListToString(List<Faculty> faculties);
}
