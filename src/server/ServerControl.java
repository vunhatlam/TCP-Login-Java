/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import client.User;

/**
 *
 * @author kami
 */
public class ServerControl {
    private Connection con;
    private ServerSocket myServer;
    private int serverPort = 7878;
    
    public ServerControl(){
        getDBConnection("databasename", "username", "password");
        openServer(serverPort);
        
        while(true){
            listenning();
        }
    }
    
    private void getDBConnection(String dbName, String username, String password){
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        try{
            con = DriverManager.getConnection(dbUrl, username, password);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void openServer(int portNumber){
        try{
            myServer = new ServerSocket(portNumber);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void listenning(){
        try{
            Socket clientSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            Object o = ois.readObject();
            if(o instanceof User){
                User user = (User)o;
                if(checkUser(user)){
                    oos.writeObject("OK");
                }
                else{
                    oos.writeObject("false");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private boolean checkUser(User user) throws Exception{
        String query = "SELECT * FROM ???? WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
        try{
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            
            if(rs.next()){
                return true;
            }
        }catch(Exception e){
            throw e;
        }
        return false;
    }
}
