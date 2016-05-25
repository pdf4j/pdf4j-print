package org.pdf4j.util.itext2;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Uma
 */
public class ItextPdfAppender {
    public ItextPdfAppender(String filename) throws FileNotFoundException , PdfDocumentException {
        super();
        this.pdfDoc = new Document();
        this.outputStream = new FileOutputStream(filename);
        try {
            this.pdfFile = new PdfSmartCopy(pdfDoc, outputStream);
            this.pdfDoc.open();
        } catch (DocumentException ex) {
            throw new PdfDocumentException(ex.getMessage());
        }
    }
    public ItextPdfAppender(OutputStream outStream) throws IOException, PdfDocumentException {
        super();
        this.pdfDoc = new Document();
        if (outStream == null){
            throw new IOException("OutputStream cannot be null");
        }
        this.outputStream = outStream;
        try {
            this.pdfFile = new PdfSmartCopy(pdfDoc, outputStream);
            this.pdfDoc.open();
        } catch (DocumentException ex) {
            throw new PdfDocumentException(ex.getMessage());
        }        
    }
    public void appendDocument(InputStream pdfStream) throws PdfDocumentException {
//        if (pdfFile == null) {
//            try {
//                OutputStream baos;
//                baos = new FileOutputStream(config.getOutputFile() + config.getReportId() + "_1.pdf");
//                pdfFile = new PdfSmartCopy(new Document(), baos);
//            } catch (FileNotFoundException | DocumentException ex) {
//                Logger.getLogger(TrialExpProcessReport0.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        if (pdfFile == null) {
            System.out.println("pdfFile object is null");
            return;
        }
        PdfImportedPage page;
        if (pdfStream != null) {
            try {
                PdfReader reader1 = new PdfReader(pdfStream);
                pdfPageCount = 0;
                while (pdfPageCount < reader1.getNumberOfPages()) {
                    pdfFile.newPage();
                    page = pdfFile.getImportedPage(reader1,
                            ++pdfPageCount);
                    if (page != null)
                        pdfFile.addPage(page);
                }

            } catch (IOException | BadPdfFormatException ex) {
                throw new PdfDocumentException(ex.getMessage());
            }
        } else {
            System.out.println("pdfFile object is null");
            //return;
        }
    }
    
    public void close() {
        //TODO: can PdfSmartCopy.close() is good enough?
        // check iText2 API
        try {
            if (pdfDoc != null) {
                pdfDoc.close();
            }
            if (pdfFile != null) {
                pdfFile.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e) {
            // go quitely
        }
    }
    private PdfReader reader;
    private int pdfPageCount;
    private PdfSmartCopy pdfFile = null;
    private PdfImportedPage page1;  
    private Document pdfDoc;
    private OutputStream outputStream;
}
