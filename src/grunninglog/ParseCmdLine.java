/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grunninglog;

/**
 *
 * @author mrupert
 */
public class ParseCmdLine {

    private char flag;
    private boolean vflag = false;
    private String miles = "";
    private String pace = "";
    private String notes = "";
    private String temperature = "";
    private String date = "";
    private String spreadsheetName = "runninglog";
    private String username = "";
    private String password = "";

    public String getMiles() {
        return miles;
    }

    public String getPace() {
        return pace;
    }

    public String getNotes() {
        return notes;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDateString() {
        return date;
    }

    public void parse(String[] args) {
        int i = 0, j;
        String arg;


        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            // use this type of check for "wordy" arguments
            if (arg.equals("-verbose")) {
                System.out.println("verbose mode on");
                vflag = true;
            } // use this type of check for arguments that require arguments
            else if (arg.equals("-miles")) {
                if (i < args.length) {
                    miles = args[i++];
                } else {
                    System.err.println("-miles requires a value");
                }
            } // use this type of check for a series of flag arguments
            else if (arg.equals("-pace")) {
                if (i < args.length) {
                    miles = args[i++];
                } else {
                    System.err.println("-pace requires a value");
                }
            } else if (arg.equals("-temperature")) {
                if (i < args.length) {
                    miles = args[i++];
                } else {
                    System.err.println("-temperature requires a value");
                }
            } else if (arg.equals("-notes")) {
                if (i < args.length) {
                    miles = args[i++];
                } else {
                    System.err.println("-notes requires a value");
                }
            } // u
        }
        if (i == args.length) {
            System.err.println("Usage: GRunLog ");
        } else {
            System.out.println("Success!");
        }
    }
}
