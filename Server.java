/*
Filename: Server.java
Author: Daemon Macklin
*/

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.Date;

/*
Classname: Server
Purpose: The RMI server.
*/
public class Server extends UnicastRemoteObject implements ServerInterface {

    // Create global variables
    DecimalFormat df = new DecimalFormat("#.####");
    Server obj;
    JTextArea display;

    /**
     * Server constructor
     * @param display the text area we want to write messages to
     * @throws RemoteException
     */
    public Server(JTextArea display) throws RemoteException {
        super();
        obj = this;
        this.display = display;
    }

    /**
     * Welcome function used to welcome user to app and test connections
     * @return
     */
    public String welcome() {
        // System.out.println("Invocation to helloWorld was successful!");
        writeMessage("Welcome", "Welcoming user to server");
        return "Welcome! Calculator Server!";
    }

    /**
     * Method to add two given numbers
     * @param x
     * @param y
     * @return
     */
    public double add(double x, double y) {
        writeMessage("Calculation", "Performing Calculation: " + x + "+" + y);
        Double result = x + y;
        return Double.parseDouble(df.format(result));
    }

    /**
     * Method to subtract two given numbers
     * @param x
     * @param y
     * @return
     */
    public double subtract(double x, double y) {
        writeMessage("Calculation", "Performing Calculation: " + x + "-" + y);
        Double result = x - y;
        return Double.parseDouble(df.format(result));
    }

    /**
     * Method to multiply two given numbers
     * @param x
     * @param y
     * @return
     */
    public double multiply(double x, double y) {
        writeMessage("Calculation", "Performing Calculation: " + x + "x" + y);
        Double result = x * y;
        return Double.parseDouble(df.format(result));
    }

    /**
     * Method to divide two given numbers
     * @param x
     * @param y
     * @return
     */
    public double divide(double x, double y) {
        writeMessage("Calculation", "Performing Calculation: " + x + "/" + y);

        if(y == 0)
            return Double.POSITIVE_INFINITY;
        Double result = x / y;
        return Double.parseDouble(df.format(result));
    }

    /**
     * Method to start the server
     */
    public void startServer() {

        try {

            // Try to create the application running on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Calculator", obj);

            // Inform user of success
            writeMessage("Bind","Calculator Application Server bound in registry");
            writeMessage("Start", "Stated Server");
        }
        catch (Exception e) {

            // If failed tell users
            System.out.println("Calculator Server error: " + e.getMessage());
            writeMessage("Error", "Error Starting Server");
            e.printStackTrace();
        }
    }

    /**
     * Helper function for writing messages to text area
     */
    private void writeMessage(String type, String message) {
        display.append(type + ":" + new Date() + ":" + message + "\n");
    }
}

/*
Classname: ServerGUI
Purpose: Create front end for server and start server.
*/
class ServerGUI extends JFrame {

    // Text area to display incoming requests and out going responses
    private static JTextArea display = new JTextArea();
    private static JButton clearDisplayButton = new JButton("Clear");
    private static JButton stopServerButton = new JButton("Stop");

    public static void main(String args[]){
    	
       ServerGUI gui = new ServerGUI();
       gui.createGUI();
       Server server;
       try {
           server = new Server(display);
       } catch (RemoteException e){
           System.out.println(e);
           return;
       }
       server.startServer();
    }

    private void createGUI() {

        JPanel displayPanel = new JPanel();
        JPanel controlPanel = new JPanel();

        displayPanel.setLayout(new BoxLayout(displayPanel,BoxLayout.PAGE_AXIS));
        //display.setSize(new Dimension(800, 450));
        displayPanel.add(display);
        displayPanel.setSize(new Dimension( 800, 600));
        displayPanel.add(Box.createVerticalStrut(10)); // Fixed width invisible separator.

        controlPanel.add(clearDisplayButton);
        controlPanel.add(stopServerButton);

        // Setup Action listeners for button
        // When the clear button is pressed clear the logs
        clearDisplayButton.addActionListener(actionEvent -> display.setText(""));

        // When the stop button is pressed exit the program
        stopServerButton.addActionListener(actionEvent -> System.exit(1));

        add(displayPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        pack();
        setSize(800, 800);
        setVisible(true);
    }
}
