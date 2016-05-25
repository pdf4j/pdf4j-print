package org.pdf4j.util.itext2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Interface defining a document that can be handled by the library.
 *
 * @author Uma
 */
public interface IDocument {

    public static final String TYPE_POSTSCRIPT = "PostScript";
    public static final String TYPE_PDF = "PDF";

    /**
     * Load document from a File.
     *
     * @param file File.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void load(File file) throws FileNotFoundException, IOException;

    /**
     * Load document from an InputStream.
     *
     * @param inputStream
     * @throws IOException
     */
    public void load(InputStream inputStream) throws IOException;

    /**
     * Write document to a File.
     *
     * @param file File.
     * @throws IOException
     */
    public void write(File file) throws IOException;

    /**
     * Write document to an OutputStream
     *
     * @param outputStream
     * @throws IOException
     */
    public void write(OutputStream outputStream) throws IOException;

    /**
     * Return document page count
     *
     * @return Number of pages.
     * @throws org.pdf4j.util.itext2.PdfDocumentException
     */
    public int getPageCount() throws PdfDocumentException;

    /**
     * Return the type of the document.
     *
     * @return A String representing the document type name.
     */
    public String getType();

    /**
     * Return document size
     *
     * @return IDocument size in bytes.
     */
    public int getSize();

    /**
     * Return document content as a byte array
     *
     * @return Byte array
     */
    public byte[] getContent();

    /**
     * Return a new document containing pages of a given range. Note : begin and
     * end indicies start at 1
     *
     * @param begin Index of the first page to extract
     * @param end Index of the last page to extract
     * @return A new document.
     * @throws org.pdf4j.util.itext2.PdfDocumentException
     */
    public IDocument extract(int begin, int end) throws PdfDocumentException;

    /**
     * Append pages of another document to the current document.
     *
     * @param document IDocument ot append
     * @throws PdfDocumentException
     */
    public void append(IDocument document) throws PdfDocumentException;

    /**
     * Separate each pages to a new document.
     *
     * @return A list of IDocument.
     * @throws PdfDocumentException
     */
    public List<IDocument> explode() throws PdfDocumentException;
}
