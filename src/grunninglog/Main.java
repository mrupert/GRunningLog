/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grunninglog;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.*;
import com.google.gdata.data.dublincore.Date;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mrupert
 */
public class Main {

    private static List<WorksheetEntry> getWorksheets(SpreadsheetEntry e) throws IOException, ServiceException {
        return e.getWorksheets();
    }

    private static WorksheetEntry getUpdateWorksheet(SpreadsheetEntry runningLog, String month) throws IOException, ServiceException {
         List<WorksheetEntry> workSheets = runningLog.getWorksheets();
            WorksheetEntry updateSheet = new WorksheetEntry();
            boolean sheetFound = false;
           // String month = "December 2010";
            for (int j = 0; j < workSheets.size(); j++) {
                if(workSheets.get(j).getTitle().getPlainText().equalsIgnoreCase(month)) {
                    updateSheet = workSheets.get(j);
                    sheetFound = true;
                    System.out.println("FOUND IT!");
                    break;
                }
            }
        return updateSheet;
    }

    private static void setCellContent(SpreadsheetService service, URL url, String value, int row, int col) {
        try {
            CellQuery query = new CellQuery(url);
            query.setMinimumRow(row);
            query.setMaximumRow(row);
            query.setMinimumCol(col);
            query.setMaximumCol(col);
            
            CellFeed feed = service.query(query, CellFeed.class);
            feed.setCanPost(true);
            // Only querying for a single cell
            System.out.println("query result size: " + feed.getEntries().size());
            CellEntry ce = feed.getEntries().get(0);
            System.out.println("content=== " + ce.getContent().toString());
            ce.changeInputValueLocal(value);
           // ce.setContent(new PlainTextConstruct(value));
            ce.update();
            //ce.setContent(new PlainTextConstruct(value));
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void connect(String user) {
        try {
            SpreadsheetService service = new SpreadsheetService("svc");
            service.setUserCredentials("matthew.t.rupert@gmail.com", "blah");
            URL feedUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full");
            SpreadsheetQuery qry = new SpreadsheetQuery(feedUrl);
            String spreadsheetName = "mspreadsheet";
            qry.setTitleQuery(spreadsheetName);
            qry.setTitleExact(false);
            SpreadsheetFeed feed = service.query(qry, SpreadsheetFeed.class);

            System.out.println(user + " has " + feed.getEntries().size() + " spreadsheets with name " + spreadsheetName);

            // There can be only one running log spreadsheet
            SpreadsheetEntry runningLog = feed.getEntries().get(0);


            System.out.println("\t Title: " + runningLog.getTitle().getPlainText());
//entry.getTitle();
            runningLog.setCanEdit(true);
//entry.setEdited(new DateTime());
//entry.setTitle(new TextConstruct("changed"));

            // System.out.println("\tauth " + entry.getAuthors());
            runningLog.setCanEdit(true);
            //DateTime dt = new DateTime();
            // dt.setValue(new Date());
            // entry.setUpdated(new DateTime());
            //System.out.println("Entry title " + entry.getTitle().);
            System.out.println("entry class::::: " + runningLog.getClass().toString());
            List<WorksheetEntry> workSheets = runningLog.getWorksheets();
            WorksheetEntry updateSheet = new WorksheetEntry();
            boolean sheetFound = false;
            String month = "December 2010";
            for (int j = 0; j < workSheets.size(); j++) {
                if(workSheets.get(j).getTitle().getPlainText().equalsIgnoreCase(month)) {
                    updateSheet = workSheets.get(j);
                    sheetFound = true;
                    System.out.println("FOUND IT!");
                    break;
                }
            }
            if(!sheetFound) {
                System.out.println("Didn't find it!");
                updateSheet = new WorksheetEntry();
                updateSheet.setTitle(new PlainTextConstruct(month));
                updateSheet.setRowCount(200);
                updateSheet.setColCount(30);     
                service.insert(runningLog.getWorksheetFeedUrl(), updateSheet);
                //updateSheet.update();
                //runningLog.update();
            }
            
            //runningLog.update();
            //updateSheet.update();
                System.out.println("Setting worksheet: " + updateSheet.getTitle().getPlainText());
                //workSheets.get(j).setTitle(new PlainTextConstruct(month));
                //entry.update();
                //System.out.println("now title is: " + workSheets.get(j).getTitle().getPlainText());

                URL cellFeedUrl = updateSheet.getCellFeedUrl();

               setCellContent(service, cellFeedUrl, "test11111", 1, 1);

               // updateSheet.update();
               // runningLog.update();
               
            
            //runningLog.update();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            System.out.println("Could not authenticate user: " + user);
        } catch (MalformedURLException ex) {
        } catch (ServiceException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }



        //service.setUserCredentials("jo@gmail.com", "mypassword");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ParseCmdLine parse = new ParseCmdLine();
        parse.parse(args);
        // TODO code application logic here
        connect("matthew.t.rupert");
    }
}
