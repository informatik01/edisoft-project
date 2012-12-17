package ee.edisoft.edi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.edisoft.edi.dao.InvoiceDao;
import ee.edisoft.edi.model.Invoice;

/**
 * Servlet implementation class InvoiceDetailsServlet
 */
public class InvoiceDetailsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private InvoiceDao invoiceDao;
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		invoiceDao = (InvoiceDao) getServletContext().getAttribute("invoiceDao");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		Invoice invoice = invoiceDao.read(uid);
		request.setAttribute("invoice", invoice);
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/view/invoice-details.jsp");
		view.forward(request, response);
	}

}
