package ee.edisoft.edi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.edisoft.edi.dao.InvoiceDao;
import ee.edisoft.edi.model.Invoice;

/**
 * Servlet implementation class InvoiceServlet.
 * Its main purpose is to fetch all Invoice records from Data Tier
 * by help of DAO object and forward it to the concrete View.
 * The specified View will use fetched data to output all Invoices.
 */

public class InvoiceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private InvoiceDao invoiceDao;

	/**
	 * @see Servlet#init()
	 */
	public void init() throws ServletException {
		invoiceDao = (InvoiceDao) getServletContext().getAttribute("invoiceDao");
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Invoice>invoices = invoiceDao.readAll();
		request.setAttribute("invoices", invoices);
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/view/invoices.jsp");
		view.forward(request, response);
	}
	
}
