package org.pdf4j.util.print;

import javax.print.CancelablePrintJob;
import javax.print.PrintException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>The <code>PrinterTask</code> class is responsible for starting a
 * PrinterJob's print function in a new thread.  This class assumes that the
 * PrinterJob is pre-configured and ready for its print() method to be called.</p>
 *
 * @author Uma
 */
public class PrinterTask implements Runnable {

    private static final Logger logger =
            Logger.getLogger(PrinterTask.class.toString());

    // PrinterJob to print
    private PrintHelper printHelper;
    private CancelablePrintJob cancelablePrintJob;

    /**
     * Create a new instance of a PrinterTask.
     *
     * @param printHelper print helper
     */
    public PrinterTask(PrintHelper printHelper) {
        this.printHelper = printHelper;
    }

    /**
     * Threads Runnable method.
     */
    @Override
    public void run() {
        try {
            if (printHelper != null) {
                cancelablePrintJob = printHelper.cancelablePrint();
            }
        } catch (PrintException ex) {
            logger.log(Level.FINE, "Error during printing.", ex);
        }
    }

    /**
     * Cancel the PrinterTask by calling the PrinterJob's cancel() method.
     */
    public void cancel() {
        try {
            if (cancelablePrintJob != null) {
                cancelablePrintJob.cancel();
            }
        } catch (PrintException ex) {
            logger.log(Level.FINE, "Error during printing, {0}", ex.getMessage());
        }
    }
}
