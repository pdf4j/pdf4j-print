package org.pdf4j.util.print;

import java.util.Date;
import org.pdf4j.util.print.icepdf.IcePdfPrintService;

/**
 *
 * @author Uma
 */
public class IcePrintPDFMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(new Date());
        IcePdfPrintService pdf = new IcePdfPrintService();
//        pdf.printPDFFile("/Users/srbala/Downloads/PdfPrintSample2.pdf", "Brother HL-2270DW series");
        pdf.printPDFFile("/Tmp/test.pdf", "\\\\ttn08itostor1\\BTR_3RD_HP_LJ_4350_Miller");
        System.out.println(new Date());
    }
    /**
     * This will print the usage requirements and exit.
     */
    private static void usage() {
        String message = "Usage: java -jar pdf-util-x.y.z.jar PrintPDF [options] <inputfile>\n"
                + "\nOptions:\n"
                + "  -printer  <printerName> : Printer name\n"
                ;

        System.err.println(message);
        System.exit(1);
    }

    private String inputfile;
    private String printername;
}
