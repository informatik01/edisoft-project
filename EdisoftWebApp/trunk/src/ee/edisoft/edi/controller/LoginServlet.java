package ee.edisoft.edi.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.edisoft.edi.dao.UserDao;
import ee.edisoft.edi.model.User;
import ee.edisoft.security.SecureEncoder;

/**
 * Servlet implementation class LoginServlet.
 * Its purpose is to control log in process.
 */
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserDao userDao;
	
	/**
	 * @see Servlet#init()
	 */
	public void init() throws ServletException {
		userDao = (UserDao) getServletContext().getAttribute("userDao");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("username").trim();
		String submittedPassword = request.getParameter("password");
		String submittedPasswordHash = null;
		String passwordHash = null;
		
		User user = userDao.read(email);
		
		if (user != null) {
			String salt = user.getSalt();
			passwordHash = user.getPassword();
			
			try {
				submittedPasswordHash = SecureEncoder.computeHash(submittedPassword, salt);
			} catch (NoSuchAlgorithmException e) {
				throw new ServletException(e);
			}
		}
		
		if (submittedPasswordHash != null && submittedPasswordHash.equalsIgnoreCase(passwordHash)) {
			request.getSession().setAttribute("loggedInUser", user);
			request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
		} else {
			request.setAttribute("errorMessage", "Invalid username or password");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
