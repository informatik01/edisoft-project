package ee.edisoft.edi.dao;

import org.apache.log4j.Logger;

/**
 * Custom exception class for use within DAO objects.
 * 
 */
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

	/**
	 * Helper method to ease logging and rethrowing an exception
	 * 
	 * @param logger The Logger to be used for logging
	 * @param message The message that contains exception details
	 * @param cause The cause of an exception
	 * @return DaoException that encapsulates the appropriate cause
	 */
	public static DaoException logAndThrow(Logger logger, String message, Throwable cause) {
		DaoException e = new DaoException(cause);
		logger.error(message, e);
		throw e;
	}
}
