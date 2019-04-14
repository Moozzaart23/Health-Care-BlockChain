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
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author ANISH DEY and AKHIL MITTAL
 */
public class Hala 
{ 
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
        
        
        Block b1=null;
        Visit v1=null;
        boolean isConn=false;
        int port1=5557;
        
        while(!isConn)
	{
            try
            {
		ServerSocket ss=new ServerSocket(port1);
                Socket s=ss.accept();
		System.out.println("Connected");
                isConn=true;
		ObjectInputStream i=new ObjectInputStream(s.getInputStream());
                b1=(Block)i.readObject();
                ss.close();
            }
            catch(Exception e)
            {
		e.printStackTrace();
            }
	}
        
      v1=b1.v.get(0);
      int uid=b1.uid;
      int l=chain.size();
      int i=0;
      for(i=0;i<chain.size();i++)
      {
        int x=chain.get(i).uid;
        ZeroKnowledge zk=new ZeroKnowledge(x,uid);
        if(!zk.verify())
        {
            System.out.println();
            System.out.println("The details of the patient already exists");
            //System.out.println("Enter Doctor Name");
            String name=chain.get(i).PatientName;
            String Doc_Name=v1.DocName;
            Date d1 = sdf.parse(v1.u);
            String date =sdf.format(d1);
            int weight=chain.get(i).weight;
            String med=v1.med;
            String remarks=v1.remarks;
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
        Date d1 = sdf.parse(b1.x);
        //System.out.println("Enter Name,Doc_Name,weight");
        String name=b1.PatientName;
        String Doc=v1.DocName;
        int weight=b1.weight;
        String date =sdf.format(d1);
        String med=v1.med;
        String remarks=v1.remarks;
        String ph=chain.get(i-1).hash;
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
      String ip[]={"192.168.43.42","192.168.43.142"};
      int x=-1;
      int port=8887;
      if(port1==5557)
          x=0;
      else
          x=1;
      for(int j=0;j<2;j++)
      {
        boolean isConn1=false;
        port=port+1;
        if(x==j)
            continue;
        try
        {
            Socket s=new Socket(ip[j],port);
            System.out.println("Connected");
            isConn1=true;
            ObjectOutputStream o=new ObjectOutputStream(s.getOutputStream());
            o.writeObject(b1);
            s.close();
            o.flush();
            o.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
      }
    }
    
}