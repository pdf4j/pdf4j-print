package org.pdf4j.util.print.icepdf;

import org.pdf4j.util.print.PrintJobWatcher;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.Defs;

import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaName;
import javax.print.attribute.standard.Sides;

/**
 * <p>The IcePdfPrintService class is an utility program to print PDF file to a
 specific printer using ICEpdf. If specified printer is not available, program
 will error out. </p>
 * 
 * <p>As of now IcePdfPrintHelper class called print given PDF file using print
 * services. This examples show how the to configure and print using the
 * PrintHelper class.  Generally the page settings are defined with the page
 * constructor followed by a call to printHelper.setupPrintService(...) to
 * setup the printing job.  The Print helper can be used in a headless mode
 * or in a GUI.</p>
 * <p>A PDF documents full path must be specified when the application starts.
 * The following is an example of how the applications is started</p>
 * 
 * @author Uma
 */
public class IcePdfPrintService {

    private static final Logger logger =
            Logger.getLogger(IcePdfPrintService.class.toString());

    static {
        Defs.setProperty("java.awt.headless", "true");
        Defs.setProperty("org.icepdf.core.scaleImages", "false");
        Defs.setProperty("org.icepdf.core.print.disableAlpha", "true");
        Defs.setProperty("javax.print.attribute.standard.Sides", "two-sided");
        // set the graphic rendering hints for speed, we loose quite a bit of quality
        // when converting to TIFF, so no point painting with the extra quality
        Defs.setProperty("org.icepdf.core.print.alphaInterpolation", "VALUE_ALPHA_INTERPOLATION_SPEED");
        Defs.setProperty("org.icepdf.core.print.antiAliasing", "VALUE_ANTIALIAS_ON");
        Defs.setProperty("org.icepdf.core.print.textAntiAliasing", "VALUE_TEXT_ANTIALIAS_OFF");
        Defs.setProperty("org.icepdf.core.print.colorRender", "VALUE_COLOR_RENDER_SPEED");
        Defs.setProperty("org.icepdf.core.print.dither", "VALUE_DITHER_DEFAULT");
        Defs.setProperty("org.icepdf.core.print.fractionalmetrics", "VALUE_FRACTIONALMETRICS_OFF");
        Defs.setProperty("org.icepdf.core.print.interpolation", "VALUE_INTERPOLATION_NEAREST_NEIGHBOR");
        Defs.setProperty("org.icepdf.core.print.render", "VALUE_RENDER_SPEED");
        Defs.setProperty("org.icepdf.core.print.stroke", "VALUE_STROKE_PURE");
    }

    /**
     * Attempts to Print PDF documents which are specified as application
     * arguments.
     *
     * @param filePath
     * @param printerName
     */
    public void printPDFFile(String filePath, String printerName) {
        if (filePath == null || filePath.trim().length() == 0) {
            logger.log(Level.WARNING, "Input file not found: {0}", filePath);
            return;
        }
        if (printerName == null || printerName.trim().length() == 0) {
            logger.log(Level.WARNING, "PrinterName not found: {0}", printerName);
            return;
        }

        /**
         * Find Available printers
         */
        PrintService[] services =
                PrintServiceLookup.lookupPrintServices(
                        DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);


        int selectedPrinter = 0;
        if (services != null && services.length > 0) {
            for (int i = 0, max = services.length - 1; i <= max; i++) {
                if (services[i].getName().equalsIgnoreCase(printerName))
                {
                    logger.log(Level.WARNING, "Printer ''{0}'' index is {1}", new Object[]{printerName, i});
                    selectedPrinter = i + 1;
                } 
            }            
        } 
        if (!(services != null && selectedPrinter > 0 && selectedPrinter <= services.length)) {
            logger.log(Level.WARNING, "Printer ''{0}'' not found, exit form program ...!", printerName);
        } else {
        PrintService selectedService = services[selectedPrinter - 1];

        // Open the document, create a PrintHelper and finally print the document
        Document pdf = new Document();

        try {
            pdf.setFile(filePath);
            // create a new print helper with a specified paper size and print
            // quality
            HashPrintRequestAttributeSet hp = new HashPrintRequestAttributeSet();
            hp.add(Sides.TWO_SIDED_LONG_EDGE);
            hp.add(new Copies(1));
            hp.add(MediaSizeName.NA_LETTER);
            hp.add(PrintQuality.NORMAL);
            hp.add(new JobName("AcmsDedWeekly", Locale.getDefault()));
            HashDocAttributeSet hd = new HashDocAttributeSet();
            hd.add(MediaName.NA_LETTER_WHITE);
            hd.add(Sides.TWO_SIDED_LONG_EDGE);

            IcePdfPrintHelper printHelper = new IcePdfPrintHelper(null, pdf.getPageTree(), 0f,
                    hd, hp);
            printHelper.setupPrintService(selectedService, hp, true);
            printHelper.print(new PrintJobWatcher());

        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "PDF file not found.", e);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error loading PDF file", e);
        } catch (PDFSecurityException e) {
            logger.log(Level.WARNING,
                    "PDF security exception, unspported encryption type.", e);
        } catch (PDFException e) {
            logger.log(Level.WARNING, "Error loading PDF document.", e);
        } catch (PrintException e) {
            logger.log(Level.WARNING, "Error Printing document.", e);
        } finally {
            pdf.dispose();
        }
    }
    }
}
