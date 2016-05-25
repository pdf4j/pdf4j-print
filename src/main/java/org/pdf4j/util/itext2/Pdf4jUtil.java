package org.pdf4j.util.itext2;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pdf4jUtil {

    public static void concatPDFs(List<InputStream> streamOfPDFFiles,
            OutputStream outputStream, boolean paginate) {

        Document document = new Document();
        try {
            List<PdfReader> readers = new ArrayList<PdfReader>();
            for (InputStream pdf : streamOfPDFFiles) {
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
            }
            // Create a writer for the outputstream
            PdfSmartCopy smartCopy = new PdfSmartCopy(document, outputStream);

            document.open();

            PdfImportedPage page;
            int pageOfCurrentReaderPDF = 0;
            for (PdfReader pdfReader : readers) {
                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    page = smartCopy.getImportedPage(pdfReader,
                            ++pageOfCurrentReaderPDF);
                    smartCopy.addPage(page);

                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (DocumentException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void concatPdfUsingPdfBox(List<InputStream> streamOfPDFFiles, String outFileName) {
//        try {
//            PDFMergerUtility merger = new PDFMergerUtility();
//            merger.setDestinationFileName(outFileName);
//            merger.addSources(streamOfPDFFiles);
//            merger.mergeDocuments();
//        } catch (IOException ex) {
//            Logger.getLogger(Pdf4jUtil.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (COSVisitorException ex) {
//            Logger.getLogger(Pdf4jUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public void concatPDFsx(PdfReader[] readers,
            OutputStream outputStream) {

        Document document = new Document();
        try {
            // Create a writer for the outputstream
            PdfSmartCopy smartCopy = new PdfSmartCopy(document, outputStream);

            document.open();

            PdfImportedPage page;
            int pdfPageCount = 0;
            for (PdfReader pdfReader : readers) {
                // Create a new page in the target for each source page.
                if (pdfReader != null) {
                    while (pdfPageCount < pdfReader.getNumberOfPages()) {
                        document.newPage();
                        page = smartCopy.getImportedPage(pdfReader,
                                ++pdfPageCount);
                        smartCopy.addPage(page);

                    }
                }
                pdfPageCount = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (DocumentException | IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    static final Logger LOG = Logger.getLogger(Pdf4jUtil.class.getName());
}
