package ee.edisoft.edi.dao;

import org.apache.log4j.Logger;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public static DaoException logAndThrow(Logger logger, String message, Throwable cause) {
		DaoException e = new DaoException(cause);
		logger.error(message, e);
		throw e;
	}
}
