/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author kami
 */
public class ServerView {

    public ServerView() {
        System.out.println("Server is running...");
        ServerControl ctr = new ServerControl();
    }
    
    public void showMessage(String msg){
        System.out.println(msg);
    }
}
