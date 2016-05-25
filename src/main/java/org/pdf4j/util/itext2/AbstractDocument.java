package org.pdf4j.util.itext2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.pdf4j.util.io.IOUtils;

/**
 * Abstract document implementation. Contains methods that are common to the
 * different document types
 *
 * @author Uma
 */
public abstract class AbstractDocument implements IDocument, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7160779330993730486L;

    /**
     * Buffer size used while reading (loading) document content.
     */
    public static final int READ_BUFFER_SIZE = 1024;

    /**
     * Content of the document.
     */
    protected byte[] content;

    public void load(File file) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream(file);
        load(fis);
        IOUtils.closeQuietly(fis);
    }

    public void load(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[READ_BUFFER_SIZE];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int readCount = 0;
        while ((readCount = inputStream.read(buffer)) > 0) {
            baos.write(buffer, 0, readCount);
        }
        content = baos.toByteArray();

        IOUtils.closeQuietly(baos);
    }

    public void write(File file) throws IOException {

        FileOutputStream fos = new FileOutputStream(file);
        write(fos);
        IOUtils.closeQuietly(fos);

    }

    public void write(OutputStream outputStream) throws IOException {

        outputStream.write(content);

    }

    public int getSize() {

        if (content == null) {
            return 0;
        } else {
            return content.length;
        }
    }

    public byte[] getContent() {
        return content;
    }

    /**
     * Assert the given page index is valid for the current document.
     *
     * @param index Index to check
     * @throws PdfDocumentException Thrown if index is not valid
     */
    protected void assertValidPageIndex(int index) throws PdfDocumentException {

        if (content == null || index > this.getPageCount()) {
            throw new PdfDocumentException("Invalid page index: " + index);
        }
    }

    /**
     * Assert the given page range is valid for the current document.
     *
     * @param begin Range begin index
     * @param end Range end index
     * @throws PdfDocumentException
     */
    protected void assertValidPageRange(int begin, int end)
            throws PdfDocumentException {

        this.assertValidPageIndex(begin);
        this.assertValidPageIndex(end);

        if (begin > end) {
            throw new PdfDocumentException("Invalid page range: " + begin + " - "
                    + end);
        }
    }

    public void append(IDocument document) throws PdfDocumentException {

        if (document == null || !this.getType().equals(document.getType())) {
            throw new PdfDocumentException(
                    "Cannot append document of different types");
        }
    }

    public List<IDocument> explode() throws PdfDocumentException {

        List<IDocument> result = new ArrayList<IDocument>();

        int pageCount = this.getPageCount();

        for (int i = 0; i < pageCount; i++) {
            result.add(this.extract(i + 1, i + 1));
        }

        return result;
    }

}
