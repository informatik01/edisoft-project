package ee.edisoft.edi.xml;

/**
 * Custom exception class for use within {@link XMLProcessor} objects.
 *
 */
public class XMLProcessingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public XMLProcessingException(String message) {
		super(message);
	}

	public XMLProcessingException(Throwable cause) {
		super(cause);
	}

	public XMLProcessingException(String message, Throwable cause) {
		super(message, cause);
	}
}
