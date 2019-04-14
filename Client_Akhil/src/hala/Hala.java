/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import java.util.Date;
import java.time.Month;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.lang.*;
/**
 *
 * @author ANISH and AKHIL Mittal
 */
public class Hala 
{ 
    //Connection c;
    public static void main(String[] args) throws Exception
    {
        ArrayList<Block> chain=new ArrayList<Block>();
        SHA256 a=new SHA256();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        Connection c2=bldbm.dbconnect();
        Connection c3=vdbm.dbconnect();
        Connection c4=vdbm.dbconnect();
        try
        {
            Connection c=bldbm.dbconnect();
            Statement statement=null;
            statement = c.createStatement();
            String s = "SELECT * FROM Block";
            int uid=0;
            ResultSet rs = statement.executeQuery(s);
            while(rs.next())
            {
                uid=rs.getInt("UID");
                ArrayList<Visit> v=new ArrayList<Visit>();
                Connection c1=vdbm.dbconnect();
                Statement statement1 = c1.createStatement();
                String s1 = "SELECT * FROM Visit";
                ResultSet rs1 = statement1.executeQuery(s1);
                int uid2=0;
                while(rs1.next())
                {
                    uid2=rs1.getInt("UID");
                    if(uid==uid2)
                    {
                        Visit v1= new Visit(rs1.getString("DocName"),rs1.getString("DOV"),rs1.getInt("weight"),rs1.getString("Medicine"),rs1.getString("Remarks"));
                        v.add(v1);
                    }
                }
                Block b1=new Block(rs.getInt("UID"),rs.getString("Name"),rs.getString("DOB"),rs.getInt("Weight"),v,rs.getString("HASH"),rs.getString("PreviousHash"));
                chain.add(b1);
             }
        }
        catch(Exception e)
        {
            System.out.println(e+" loll");
        }
        //System.out.pr
        for(int j=0;j<chain.size();j++)
        {
            chain.get(j).printAll();
            System.out.println("                        *********");
            System.out.println("                        *********");
        }
        
        Scanner sc=new Scanner(System.in);
        int uid;String lol;
        System.out.println("Enter Uid:");
        lol=sc.nextLine();
        uid=Integer.parseInt(lol);
        int l=chain.size();
        //System.out.println("Size= "+l);
        int i=0;
        for(i=0;i<chain.size();i++)
        {
            int x=chain.get(i).uid;
            ZeroKnowledge zk=new ZeroKnowledge(x,uid);
            if(!zk.verify())
            {
                System.out.println();
                System.out.println("The details of the patient already exists");
                System.out.println("Enter Doctor Name");
                String name=chain.get(i).PatientName;
                String Doc_Name=sc.nextLine();
                Date d1 = sdf.parse("27/12/2019");
                String date =sdf.format(d1);
                int weight=chain.get(i).weight;
                String med="Saridon";
                String remarks="Headache";
                try
                {
                    String query1="insert into Visit values(?,?,?,?,?,?)";
                    PreparedStatement ps1=null;
                    ps1=c4.prepareStatement(query1);     
                    ps1.setInt(1,uid);
                    ps1.setString(2,Doc_Name);
                    ps1.setString(3,date);
                    ps1.setInt(4,weight);
                    ps1.setString(5, med);
                    ps1.setString(6,remarks);
                    ps1.execute();

                }
                catch (java.sql.SQLException e)
                {
                  System.out.println("Cannot Enter");
                }
                break;
            }
        }
        if(i==l)
        {
            Date d1 = sdf.parse("22/12/1998");
            System.out.println("Enter Name,Doc_Name,weight");
            String name=sc.nextLine();
            String Doc=sc.nextLine();
            int weight=sc.nextInt();
            String date =sdf.format(d1);
            String med="Zapiz-0.25";
            String remarks="Depression";
            String ph=chain.get(i-1).previous_hash;
            String hash=a.getSHA256Hash(ph+name);
            try
            {
                String query="insert into Block values(?,?,?,?,?,?)";
                PreparedStatement ps=null;
                ps=c2.prepareStatement(query);     
                ps.setInt(1,uid);
                ps.setString(2,name);
                ps.setString(3,date);
                ps.setInt(4,weight);
                ps.setString(5,hash);
                ps.setString(6,ph);
                ps.execute();
                
                
                String query1="insert into Visit values(?,?,?,?,?,?)";
                PreparedStatement ps1=null;
                ps1=c3.prepareStatement(query1);     
                ps1.setInt(1,uid);
                ps1.setString(2,Doc);
                ps1.setString(3,date);
                ps1.setInt(4,weight);
                ps1.setString(5, med);
                ps1.setString(6,remarks);
                ps1.execute();
                
            }
            catch (java.sql.SQLException e)
            {
              System.out.println("Cannot Enter");
            }
        }
    }
}
