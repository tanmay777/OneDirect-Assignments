package com.tanmay.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author tanmaysowdhaman
 * Dec 12, 2017
 */
@Component
public class ErrorHandler implements AccessDeniedHandler {

	private static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException arg2)
			throws IOException, ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
		    logger.info("User '" + auth.getName()
			            + "' attempted to access the protected URL: "
			            + request.getRequestURI());
		}
			
		response.sendRedirect(request.getContextPath() + "/403");

	}

}
