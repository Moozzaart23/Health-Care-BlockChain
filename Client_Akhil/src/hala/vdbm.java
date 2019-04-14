/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala;

/**
 *
 * @author ANISH
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANISH
 */

import java.sql.*;
//import javax.swing.*;
public class vdbm {
    Connection conn=null;
    public static Connection dbconnect(){ 
    
  
    try
    {
    Class.forName("org.sqlite.JDBC");
    Connection conn=DriverManager.getConnection("jdbc:sqlite:E:\\BPHC\\Crypto\\Hala\\databases\\Visit.sqlite");
    //JOptionPane.showMessageDialog(null ,"connected");
    return conn;
    }
    catch(Exception e)
    {
    //JOptionPane.showMessageDialog(null ,e);
    System.out.println(e+" lol");
    return null;
    }
}
}
