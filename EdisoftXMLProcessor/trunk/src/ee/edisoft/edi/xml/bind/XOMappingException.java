package ee.edisoft.edi.xml.bind;

/**
 * Custom exception class for use within {@link XOMapper} objects.
 *
 */
public class XOMappingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public XOMappingException(String message) {
		super(message);
	}

	public XOMappingException(Throwable cause) {
		super(cause);
	}

	public XOMappingException(String message, Throwable cause) {
		super(message, cause);
	}
}
