package ee.edisoft.edi.xml.bind;

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
