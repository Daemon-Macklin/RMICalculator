/*
Filename: ServerInterface.java
Author: Daemon Macklin
*/

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
Classname: ServerInterface
Comment: The remote interface.
*/
public interface ServerInterface extends Remote {
    String welcome() throws RemoteException;
    double add(double x, double y) throws RemoteException;
    double subtract(double x, double y) throws RemoteException;
    double multiply(double x, double y) throws RemoteException;
    double divide(double x, double y) throws RemoteException;
}
