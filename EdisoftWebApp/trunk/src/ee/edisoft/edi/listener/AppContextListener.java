package ee.edisoft.edi.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import ee.edisoft.edi.dao.DaoFactory;
import ee.edisoft.edi.dao.InvoiceDao;
import ee.edisoft.edi.dao.UserDao;

/**
 * Application Lifecycle Listener implementation class AppContextListener
 *
 */
@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
    public void contextInitialized(ServletContextEvent event) {
        InvoiceDao invoiceDao = DaoFactory.getDaoFactory().getInvoiceDao();
        UserDao userDao = DaoFactory.getDaoFactory().getUserDao();
        
        ServletContext sc = event.getServletContext();
        sc.setAttribute("invoiceDao", invoiceDao);
        sc.setAttribute("userDao", userDao);
    }

	@Override
    public void contextDestroyed(ServletContextEvent event) {
		
    }
	
}
