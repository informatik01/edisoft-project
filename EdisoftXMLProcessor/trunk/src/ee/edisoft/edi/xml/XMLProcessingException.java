package ee.edisoft.edi.xml;

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
