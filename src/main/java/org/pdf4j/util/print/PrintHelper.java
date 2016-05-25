package org.pdf4j.util.print;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import javax.print.CancelablePrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;

/**
 *
 * @author Uma
 */
public interface PrintHelper extends Printable {

    CancelablePrintJob cancelablePrint() throws PrintException;

    /**
     * Gets the page number of the page currently being spooled by the Printable
     * interface.
     *
     * @return current page being spooled by printer.
     */
    int getCurrentPage();

    /**
     * Gets the document attributes currently in use.
     *
     * @return current document attributes.
     */
    HashDocAttributeSet getDocAttributeSet();

    /**
     * Number of total pages being printed.
     *
     * @return total pages being printed.
     */
    int getNumberOfPages();

    /**
     * Gets the print request attribute sets.
     *
     * @return attribute set
     */
    HashPrintRequestAttributeSet getPrintRequestAttributeSet();

    /**
     * Gets the currently assigned print service.
     *
     * @return current print service, can be null.
     */
    PrintService getPrintService();

    /**
     * Users rotation specified for the print job.
     *
     * @return float value representing rotation, 0 is 0 degrees.
     */
    float getUserRotation();

    /**
     * Are page annotations going to be printed?
     *
     * @return true if annotation are to be printed, false otherwise
     */
    boolean isPaintAnnotation();

    /**
     * Are page search highlight's going to be printed?
     *
     * @return true if highlights are to be printed, false otherwise
     */
    boolean isPaintSearchHighlight();

    /**
     * Gets the fit to margin property.  If enabled the page is scaled to fit
     * the paper size maxing out on the smallest paper dimension.
     *
     * @return true if fit to margin is enabled.
     */
    boolean isPrintFitToMargin();

    /**
     * Prints the page at the specified index into the specified
     * java.awt.Graphics context in the specified format.
     *
     * @param printGraphics paper graphics context.
     * @param pageFormat    print attributes translated from PrintService
     * @param pageIndex     page to print, zero based.
     * @return A status code of Printable.NO_SUCH_PAGE or Printable.PAGE_EXISTS
     */
    int print(Graphics printGraphics, PageFormat pageFormat, int pageIndex);

    /**
     * Print a range of pages from the document as specified by #setupPrintService.
     *
     * @throws PrintException if a default printer could not be found or some
     *                        other printing related error.
     */
    void print() throws PrintException;

    void print(PrintJobWatcher printJobWatcher) throws PrintException;

    /**
     * Manually enable or disable the printing of annotation for a print job
     *
     * @param paintAnnotation true to paint annotation; otherwise false.
     */
    void setPaintAnnotation(boolean paintAnnotation);

    /**
     * Manually enable or disable the printing of search highlights for a print job
     *
     * @param paintSearchHighlight true to paint search highlights; otherwise false.
     */
    void setPaintSearchHighlight(boolean paintSearchHighlight);

    /**
     * Configures the PrinterJob instance with the specified parameters.
     *
     * @param startPage             start of page range, zero-based index.
     * @param endPage               end of page range, one-based index.
     * @param copies                number of copies of pages in print range.
     * @param shrinkToPrintableArea true, to enable shrink to fit printable area;
     *                              false, otherwise.
     * @param showPrintDialog       true, to display a print setup dialog when this method
     *                              is initiated; false, otherwise.  This dialog will be shown after the
     *                              page dialog if it is visible.
     * @return true if print setup should continue, false if printing was cancelled
     * by user interaction with optional print dialog.
     */
    boolean setupPrintService(int startPage, int endPage, int copies, boolean shrinkToPrintableArea, boolean showPrintDialog);

    /**
     * Configures the PrinterJob instance with the specified parameters.
     *
     * @param printService          print service to print document to.
     * @param startPage             start of page range, zero-based index.
     * @param endPage               end of page range, one-based index.
     * @param copies                number of copies of pages in print range.
     * @param shrinkToPrintableArea true, to enable shrink to fit printable area;
     *                              false, otherwise.
     */
    void setupPrintService(PrintService printService, int startPage, int endPage, int copies, boolean shrinkToPrintableArea);

    /**
     * Configures the PrinterJob instance with the specified parameters.  this
     * method should only be used by advanced users.
     *
     * @param printService             print service to print document to.
     * @param printRequestAttributeSet print jobt attribute set.
     * @param shrinkToPrintableArea    true, to enable shrink to fit printable area;
     *                                 false, otherwise.
     */
    void setupPrintService(PrintService printService, HashPrintRequestAttributeSet printRequestAttributeSet, boolean shrinkToPrintableArea);

    /**
     * Utility for showing print dialog for the current printService.  If no
     * print service is assigned the first print service is used to create
     * the print dialog.
     */
    void showPrintSetupDialog();
    
}
