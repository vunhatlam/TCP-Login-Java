/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 *
 * @author kami
 */
public class ClientControl {
    private Socket mySocket;
    private String serverHost = "localhost";
    private int serverPort = 7878;
    
    public ClientControl(){}
    
    public Socket openConnection(){
        try{
            mySocket = new Socket(serverHost, serverPort);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return mySocket;
    }
    
    public boolean sendData(User user){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(user);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String receiveData(){
        String result = null;
        try{
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            if(o instanceof String){
                result = (String)o;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }
    
    public boolean closeConnection(){
        try{
            mySocket.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
