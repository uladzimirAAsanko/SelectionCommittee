package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SwitchLocationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOCATION_ATTRIBUTE = "location";
    private static final String ENGLISH_LOCATION_ATTRIBUTE = "en_US";
    private static final String RUSSIAN_LOCATION_ATTRIBUTE = "ru_RU";
    private static final String CURRENT_SITE_ATTRIBUTE = "currentSite";
    private static final String STAY_PART = "/jsp/";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String location = (String) req.getAttribute(LOCATION_ATTRIBUTE);
        String newLocation = "";
        if(location.equals(ENGLISH_LOCATION_ATTRIBUTE)){
            newLocation = RUSSIAN_LOCATION_ATTRIBUTE;
        }else{
            newLocation = ENGLISH_LOCATION_ATTRIBUTE;
        }
        Cookie locationCook = new Cookie(LOCATION_ATTRIBUTE,newLocation);
        resp.addCookie(locationCook);
        String currSiteAttr = req.getParameter(CURRENT_SITE_ATTRIBUTE);
        String currSite = currSiteAttr.substring(currSiteAttr.indexOf(STAY_PART));
        try {
            req.getRequestDispatcher(currSite).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
