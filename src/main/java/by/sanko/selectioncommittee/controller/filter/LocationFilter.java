package by.sanko.selectioncommittee.controller.filter;

import by.sanko.selectioncommittee.util.security.JWTUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocationFilter implements Filter {
    private static final String LOCATION_ATTRIBUTE = "location";
    private static final String DEFAULT_LOCATION_ATTRIBUTE = "en_US";
    private static final String code = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(code);
        servletResponse.setCharacterEncoding(code);
        String location = JWTUtil.getInstance().getCook(servletRequest, LOCATION_ATTRIBUTE);
        if(location == null || location.isEmpty()){
            location = DEFAULT_LOCATION_ATTRIBUTE;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setAttribute(LOCATION_ATTRIBUTE, location);
        filterChain.doFilter(request, response);
    }
}
