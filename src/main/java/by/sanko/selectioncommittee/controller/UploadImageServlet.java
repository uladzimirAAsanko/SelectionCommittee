package by.sanko.selectioncommittee.controller;

import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/uploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadImageServlet  extends HttpServlet {

    public static final String SAVE_DIRECTORY = "uploadDir";
    private static final String LOGIN_PARAMETER = "userLogin";
    private static final String ID_PARAMETER = "userID";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            String appPath = req.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');

            // Creates the save directory if it does not exists
            String fullSavePath = null;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + SAVE_DIRECTORY;
            } else {
                fullSavePath = appPath + File.separator + SAVE_DIRECTORY;
            }
            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            String fileName = "";
            for (Part part : req.getParts()) {
                fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    fileName = (String) req.getAttribute(LOGIN_PARAMETER)+"AVATAR.jpg";
                    String filePath = fullSavePath + File.separator + fileName;
                    // Write to file
                    part.write(filePath);
                }
            }
            fileName = SAVE_DIRECTORY + File.separator + fileName;
            UserService service = ServiceFactory.getInstance().getUserService();
            int userID = (Integer) req.getAttribute(ID_PARAMETER);
            service.addPhotoToUser(userID,fileName);
            // Upload successfully!.
            resp.sendRedirect(MappingJSP.SUCCESS_LOGIN);
        } catch (ServiceException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsps/uploadFile.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private String extractFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }
}
