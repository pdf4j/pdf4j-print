package org.pdf4j.util.itext2;

/**
 * Class representing a document exception. This exception may be thrown while
 * handling a subclass of Document interface.
 *
 * @author Uma
 */
public class PdfDocumentException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3773482793220746656L;

    public PdfDocumentException() {
        super();
    }

    public PdfDocumentException(String message) {
        super(message);
    }

    public PdfDocumentException(Throwable cause) {
        super(cause);
    }

    public PdfDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
