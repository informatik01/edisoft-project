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
 * Servlet implementation class InvoiceDetailsServlet.
 * Its main purpose is to fetch concrete Invoice record from Data Tier
 * by help of DAO object and forward it to the concrete View.
 * The specified View will use fetched data to output concrete Invoice's details.
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
