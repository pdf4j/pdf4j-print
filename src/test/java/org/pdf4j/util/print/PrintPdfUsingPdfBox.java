package org.pdf4j.util.print;

/**
 *
 * @author Uma
 */
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.pdf4j.util.print.pdfbox.PdfBoxPageable;

/**
 * This is a command line program that will print a PDF document.
 *
 * @author Ben Litchfield
 */
public final class PrintPdfUsingPdfBox {

    private static final String PASSWORD = "-password";
    private static final String SILENT = "-silentPrint";
    private static final String PRINTER_NAME = "-printerName";

    /**
     * private constructor.
     */
    private PrintPdfUsingPdfBox() {
        // static class
    }

    /**
     * Infamous main method.
     *
     * @param args Command line arguments, should be one and a reference to a
     * file.
     * @throws PrinterException if the specified service cannot support the
     * Pageable and Printable interfaces.
     * @throws IOException if there is an error parsing the file.
     */
    public static void main(String[] args) throws PrinterException, IOException {
        // suppress the Dock icon on OS X
        System.setProperty("apple.awt.UIElement", "true");

        String password = "";
//        String pdfFile = "/Users/srbala/Downloads/PdfPrintSample2.pdf";
        String pdfFile = "/Tmp/test.pdf";
        boolean silentPrint = true;
        String printerName = "\\\\ttn08itostor1\\BTR_3RD_HP_LJ_4350_Miller";
//        for (int i = 0; i < args.length; i++)
//        {
//            if (args[i].equals(PASSWORD))
//            {
//                i++;
//                if (i >= args.length)
//                {
//                    usage();
//                }
//                password = args[i];
//            }
//            else if (args[i].equals(PRINTER_NAME))
//            {
//                i++;
//                if (i >= args.length)
//                {
//                    usage();
//                }
//                printerName = args[i];
//            }
//            else if (args[i].equals(SILENT))
//            {
//                silentPrint = true;
//            }
//            else
//            {
//                pdfFile = args[i];
//            }
//        }
//
//        if (pdfFile == null)
//        {
//            usage();
//        }

        PDDocument document = null;
        try {
            document = PDDocument.load(new File(pdfFile), password);

            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(new File(pdfFile).getName());

            if (printerName != null) {
                PrintService[] printService = PrinterJob.lookupPrintServices();
                boolean printerFound = false;
                for (int i = 0; !printerFound && i < printService.length; i++) {
                    if (printService[i].getName().contains(printerName)) {
                        printJob.setPrintService(printService[i]);
                        printerFound = true;
                    }
                }
            }
            printJob.setPageable(new PdfBoxPageable(document));

            if (silentPrint || printJob.printDialog()) {
                printJob.print();
            }
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    /**
     * This will print the usage requirements and exit.
     */
    private static void usage() {
        String message = "Usage: java -jar pdfbox-app-x.y.z.jar PrintPDF [options] <inputfile>\n"
                + "\nOptions:\n"
                + "  -password  <password> : Password to decrypt document\n"
                + "  -silentPrint          : Print without prompting for printer info\n";

        System.err.println(message);
        System.exit(1);
    }
}
