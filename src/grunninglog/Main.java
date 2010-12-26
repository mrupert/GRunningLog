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


    private static void connect(String user)  {
        try {
            SpreadsheetService service = new SpreadsheetService("svc");
            service.setUserCredentials("matthew.t.rupert@gmail.com", "Hejkaj$308");
URL feedUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full");
SpreadsheetQuery qry = new SpreadsheetQuery(feedUrl);
String spreadsheetName = "mspreadsheet";
qry.setTitleQuery(spreadsheetName);
qry.setTitleExact(false);
SpreadsheetFeed feed = service.query(qry,SpreadsheetFeed.class);

System.out.println(user + " has " + feed.getEntries().size() + " spreadsheets with name " + spreadsheetName);
List<SpreadsheetEntry> runningLog = feed.getEntries();

List<SpreadsheetEntry> spreadsheets = feed.getEntries();
for (int i = 0; i < spreadsheets.size(); i++) {
  SpreadsheetEntry entry = spreadsheets.get(i);
  System.out.println("\t Title: " + entry.getTitle().getPlainText());
//entry.getTitle();
entry.setCanEdit(true);
//entry.setEdited(new DateTime());
//entry.setTitle(new TextConstruct("changed"));

  System.out.println("auth " + entry.getAuthors());
  //System.out.println("Entry title " + entry.getTitle().);
  entry.setTitle(new PlainTextConstruct("MyTitle" + i));
  entry.update();

}

        } catch (AuthenticationException ex) {
            System.out.println("Could not authenticate user: " + user);
        } catch (MalformedURLException ex) {
            
        } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServiceException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedOperationException ex) {
                ex.printStackTrace();
            }


        
        //service.setUserCredentials("jo@gmail.com", "mypassword");

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        connect("matt");
    }

}
