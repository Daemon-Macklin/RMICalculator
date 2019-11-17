/*
Filename: Client.java
Author: Daemon Macklin
*/

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.registry.Registry;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Classname: Client
Comment: The RMI client.
*/
public class Client extends JFrame {

    // Variables declaration
    private javax.swing.JButton button0;
    private javax.swing.JButton button1;
    private javax.swing.JButton button2;
    private javax.swing.JButton button3;
    private javax.swing.JButton button4;
    private javax.swing.JButton button5;
    private javax.swing.JButton button6;
    private javax.swing.JButton button7;
    private javax.swing.JButton button8;
    private javax.swing.JButton button9;
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonDiv;
    private javax.swing.JButton buttonEquals;
    private javax.swing.JButton buttonMul;
    private javax.swing.JButton buttonPrev;
    private javax.swing.JButton buttonSub;
    private javax.swing.JLabel logText;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JButton buttonClear;

    // Global variables to store data
    private Double previousAnswer;
    private ClientConnection rmiConnection;

    /**
     * Main method
     * @param args
     */
    public static void main(String args[]){

        Client client = new Client();
        client.createGUI();
        client.rmiConnection = new ClientConnection();

        // Connect to the server via RMI and check the connection status
        String connected = client.rmiConnection.connectToRMIServer();
        if(connected.equals("ConnectionException")){
            // If the status is connection exception there was an error connecting to the server
            client.logText.setText("Error Connecting to Server");
        } else if(connected.equals("")){
            // If it is empty there is another error
            client.logText.setText("Error");
        } else{
            // Else it will be the welcome message
            client.logText.setText(connected);
        }
    }

    /**
     * Method to create the GUI
     */
    public void createGUI(){

        button1 = new javax.swing.JButton();
        button4 = new javax.swing.JButton();
        button7 = new javax.swing.JButton();
        button0 = new javax.swing.JButton();
        button2 = new javax.swing.JButton();
        button8 = new javax.swing.JButton();
        button5 = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        button3 = new javax.swing.JButton();
        button6 = new javax.swing.JButton();
        button9 = new javax.swing.JButton();
        buttonSub = new javax.swing.JButton();
        buttonDiv = new javax.swing.JButton();
        buttonMul = new javax.swing.JButton();
        buttonEquals = new javax.swing.JButton();
        buttonPrev = new javax.swing.JButton();
        resultLabel = new javax.swing.JLabel();
        logText = new javax.swing.JLabel();
        buttonClear = new javax.swing.JButton();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(254, 254, 254));

        button1.setText("1");
        button1.setToolTipText("");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("1");
            }
        });

        button4.setText("4");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("4");
            }
        });

        button7.setText("7");
        button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("7");
            }
        });

        button0.setText("0");
        button0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("0");
            }
        });

        button2.setText("2");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("2");
            }
        });

        button8.setText("8");
        button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("8");
            }
        });

        button5.setText("5");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("5");
            }
        });

        buttonAdd.setText("+");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("+");
            }
        });

        button3.setText("3");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("3");
            }
        });

        button6.setText("6");
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("6");
            }
        });

        button9.setText("9");
        button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("9");
            }
        });

        buttonSub.setText("-");
        buttonSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("-");
            }
        });

        buttonDiv.setText("/");
        buttonDiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("/");
            }
        });

        buttonMul.setText("x");
        buttonMul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToLabel("x");
            }
        });

        buttonEquals.setText("=");
        buttonEquals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                // If the result label is not empty
                if(!resultLabel.getText().equals("") && !resultLabel.getText().contains("=")) {
                    // Get the result of the desired calculation
                    Double result = compute(resultLabel.getText());

                    // Check if the result is null
                    if(result != null){

                        // If it is no then add the result to the result label
                        resultLabel.setText(resultLabel.getText() + " = " + " " + result);

                        // Check if the result is NaN or infinite
                        if(!result.isNaN() && !result.isInfinite())

                            // If it is neither then store the result
                            previousAnswer = result;

                    } else{

                        // If the result is null Check the connection
                        if(rmiConnection.getObj() == null) {
                            // If the connection obj is null then there is a server error
                            resultLabel.setText("Server Error");
                            // Try to reconnect
                            rmiConnection.connectToRMIServer();

                            if(rmiConnection.getObj() != null){
                                logText.setText("Connection Established");
                                resultLabel.setText("");
                            }
                        }
                        else {

                            // If the object is not null
                            try {
                                // Test the connection
                                rmiConnection.getObj().welcome();

                                // If it succeeds then it must be a user error
                                resultLabel.setText("Syntax Error");
                            } catch (RemoteException e) {

                                // If it fails there is a server error
                                logText.setText("Server Error");
                            }
                        }
                    }
                }
            }
        });

        buttonPrev.setText("<");
        buttonPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                // If the perviousAnswer has been set add it to the resultlabel
                if(previousAnswer != null)
                addToLabel(previousAnswer.toString());
            }
        });

        buttonClear.setText("Clear");
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logText.setText("Clearing Field");
                resultLabel.setText("");
            }
        });

        // GUI code generated with the NetBeans Swing Builder

        resultLabel.setForeground(new java.awt.Color(0,0,0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(resultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(logText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(button0, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(buttonEquals, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(buttonPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(buttonMul, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(button9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(buttonSub, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(buttonDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addContainerGap(20, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonClear)
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logText, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(resultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button7)
                                        .addComponent(button8)
                                        .addComponent(button9)
                                        .addComponent(buttonDiv))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button4)
                                        .addComponent(button5)
                                        .addComponent(button6)
                                        .addComponent(buttonSub))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button1)
                                        .addComponent(button2)
                                        .addComponent(button3)
                                        .addComponent(buttonAdd))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button0)
                                        .addComponent(buttonEquals)
                                        .addComponent(buttonPrev)
                                        .addComponent(buttonMul))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonClear))
        );

        pack();
        setSize(255,290);
        setVisible(true);
    }

    /**
     * Method to add data to the label/calculation
     * @param str
     */
    private void addToLabel(String str){
        boolean isNumeric = false;

        // Try and cast the str as a double
        try{
            // If this succeeds then the item is a number
            Double.parseDouble(str);
            isNumeric = true;
        } catch (Exception e){
        }

        // If it is a number
        if(!isNumeric)
            // Add spaces
            str = " " + str + " ";

        // If the current label contains an error message
        if(resultLabel.getText().equals("Syntax Error") || resultLabel.getText().equals("Error") || resultLabel.getText().equals("Server Error"))
            // Clear it
            resultLabel.setText("");

        // If the current label contains an answer
        if(resultLabel.getText().contains("="))
            // Clear it
            resultLabel.setText("");

        // Add the item to the result label
        resultLabel.setText(resultLabel.getText() + str);
    }

    /**
     * Method to parse label to calculate answer
     * @param str
     * @return
     */
    private Double compute(String str){

        Double result;

        // Split the label into a list of the individual elements
        String[] data = str.split(" ");

        // Check the length of the list
        if(data.length == 3){

            // If the length is 3 the calculation is a simple calculation i.e 1+2
            result = simpleOperation(data);
        } else if(data.length > 3){

            // If it is longer it is a complex calculation i.e 1+2+3+4
        	// Check for syntax errors
        	for(int i = 0; i < data.length; i+=1) {
        		
        		// Check if the index is a multiple of 2
        		if(i % 2 == 0) {
        			// If it is we should have a number here
        			try {
        				Double.parseDouble(data[i]);
        			} catch(Exception e) {
        				return null;
        			}
        		} else {
        			// If it is not we should have an operator here
        			try {
        				Double.parseDouble(data[i]);
        				return null;
        			} catch(Exception e) {
        			}
        		}
        	}
            result = complexOperation(data);
        } else {

            // Else it is a syntax error
            result = null;
        }
        return result;
    }

    /**
     * Method to perform simple calculations
     * @param data a list containing num1, operator, num2 in that order
     * @return The result
     */
    private Double simpleOperation(String[] data){
        Double x, y, result;
        String operator;

        // Check if the numbers are numbers
        try {
            x = Double.parseDouble(data[0]);
            y = Double.parseDouble(data[2]);
        } catch (Exception e){

            // If they are not return null
            return null;
        }

        // Check if the operator is not a number
        try {

            // If it is return null
            Double.parseDouble(data[1]);
            return null;
        } catch (Exception e){
            operator = data[1];
        }

        // Case switch to select the appropriate function to call.
        switch (operator) {
            case "+":
                result = rmiConnection.add(x, y);
                logText.setText("Server Performing Addition");
                break;
            case "-":
                result = rmiConnection.subtract(x,y);
                logText.setText("Server Performing Subtraction");
                break;
            case "/":
                result = rmiConnection.divide(x,y);
                logText.setText("Server Performing Division");
                break;
            case "x":
                result = rmiConnection.multiply(x,y);
                logText.setText("Server Performing Multiplication");
                break;
            default:
                result = null;
        }

        // Return the result
        return result;
    }

    /**
     * Method to perform complex operations
     * @param data a list of elements in the calculation ordered: num, operator, num operator, num...etc.
     * @return The result
     */
    private Double complexOperation(String[] data) {

        // Create an arraylist with the input list as it will be easier to move things around.
        ArrayList<String> dataAL = new ArrayList<>(Arrays.asList(data));

        System.out.println(dataAL);
        // Apply the multiplication and division operations first
        dataAL = applyOperations(dataAL, "x", "/");

        // Apply the addition and subtraction operations second
        dataAL = applyOperations(dataAL, "+", "-");

        // If the result list is not of length 1 there has been an error
        if(dataAL == null || dataAL.size() != 1){
            return null;
        } else{

            // Check to see if there was a division by 0
            if(dataAL.get(0).equals("Math Error")){

                // If there was return infinity
                return Double.POSITIVE_INFINITY;
            }
            // Else return the result
            return Double.parseDouble(dataAL.get(0));
        }
    }

    /**
     * Method to provide given operations on calculation
     * @param dataAL A list of numbers and operators
     * @param operator1 Operator1 to execute
     * @param operator2 Operator2 to execute
     * @return Returns a list after operations have been executed
     */
    private ArrayList<String> applyOperations(ArrayList<String> dataAL, String operator1, String operator2){

        // While we have not gotten to the end of the list
        int index = 0;
        while (index < dataAL.size()){

            // If the item at this index is an operator the user wants executed
            if(dataAL.get(index).equals(operator1) || dataAL.get(index).equals(operator2)){

                // Perform a simple operation on the numbers before and after this operator
                String[] list = {dataAL.get(index-1), dataAL.get(index), dataAL.get(index+1)};
                Double result = simpleOperation(list);

                // If the result is null there is an error
                if(result == null){
                    // return null
                    return null;
                }
                // Check if there was a division by zero
                if(result.isInfinite()){

                    // If there is clear the list, return a list with only "Math Error"
                    dataAL.clear();
                    dataAL.add("Math Error");
                    return dataAL;
                }

                // If there is no division by zero
                // Set the result to be the value before the operator
                dataAL.set(index-1, result.toString());

                // Remove the operator and the value after it
                dataAL.remove(index);
                dataAL.remove(index);
                // Go back one
                index-=1;
            } else{

                // If it is not an operator the user wants executed iterate forward
                index+=1;
            }
        }

        // Return the list
        return dataAL;
    }
}

/*
Classname: ClientConnection
Comment: The RMI client's connection manager.
*/
class ClientConnection {

    static String message = "blank";
    static ServerInterface obj = null;

    /**
     * Method to connect to server
     * @return returns a string with the connection status
     */
    public String connectToRMIServer() {
        try {
            obj = (ServerInterface) Naming.lookup("//"
                    + "localhost"
                    + "/Calculator");
            message = obj.welcome();
            return message;
        } catch (ConnectException e){
            return "ConnectionException";
        }catch (Exception e) {
            return "";
        }
    }

    /**
     * Method to get the connection object
     * @return
     */
    public ServerInterface getObj(){
        return obj;
    }

    /**
     * Method to invoice add method
     * @param x
     * @param y
     * @return
     */
    public Double add(Double x, Double y) {
        try {
            return obj.add(x, y);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Method to invoice subtract method
     * @param x
     * @param y
     * @return
     */
    public Double subtract(Double x, Double y){
        try {
            return obj.subtract(x, y);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Method to invoice multiply method
     * @param x
     * @param y
     * @return
     */
    public Double multiply(Double x, Double y){
        try {
            return obj.multiply(x, y);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Method to invoice divide method
     * @param x
     * @param y
     * @return
     */
    public Double divide(Double x, Double y){
        try {
            return obj.divide(x, y);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
