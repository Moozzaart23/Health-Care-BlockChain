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
//import java.time.Month;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author ANISH and AKHIL Mittal
 */
public class Hala 
{
    public Hala()
    {
        
    }
    
    
    /********Function to send the block from MyLaptop to Server************/
    
    
    public void ins(Block b1)
    {
        boolean isConn=false;
        try
        {
            Socket s=new Socket("192.168.43.185",5559);
            System.out.println("Connected");
            isConn=true;
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
        
        
        /*******Printing the BlockChain*********/
        
        
        for(int j=0;j<chain.size();j++)
        {
            chain.get(j).printAll(); 
            System.out.println("                        *********");
            System.out.println("                        *********");
        }
        
        
        /*************Taking input From the console*****************/
        
        
        Scanner sc=new Scanner(System.in);
        int m;
        System.out.println("Do you want to enter a Block? Press 1 for YES, 0 for NO");
        m=sc.nextInt();
        Hala h2=new Hala();
        ArrayList <Visit> g=new ArrayList<Visit>();
        
        
        
        //System.out.println(w+10);
        
        
       /* Visit v1=new Visit("Dr.Shubham Jadhav","13/03/2019",45,"Aciloc","Acidity");
        g.add(v1);
        String ph="x";
        String name="Piyush Pathak";
        String h=a.getSHA256Hash(ph+name);
        Block b1=new Block(17,name,"13/03/1998",87,g,h,ph);*/
        
        if(m==1)
        {
            System.out.println("enter Patient id,Patient Name,DOB,Visit date,Doc name,Weight,Medicine,Remarks");
            sc.nextLine();
            String sss=sc.nextLine();
            Scanner sc1=new Scanner(sss);
            sc1.useDelimiter(",");
            int id=Integer.parseInt(sc1.next());
            String name=sc1.next();
            String dob=sc1.next();
            String d=sc1.next();
            String docn=sc1.next();
            int w=Integer.parseInt(sc1.next());
            String med1=sc1.next();
            String remarks1=sc1.next();
            Visit v1=new Visit(docn,d,w,med1,remarks1);
            g.add(v1);
            String ph="x";
            String h=a.getSHA256Hash(ph+name);
            Block b1=new Block(id,name,dob,w,g,h,ph);
            h2.ins(b1);
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
                    String name1=chain.get(i).PatientName;
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
                    //Visit v2=new Visit(Doc_Name,date,weight,med,remarks);
                    chain.get(i).updateBlock(v1);
                    //g.add(v2);
                    break;
                }
            }
            if(i==l)
            {
                Date d1 = sdf.parse(b1.x);
                //System.out.println("Enter Name,Doc_Name,weight");
                String name1=b1.PatientName;
                String Doc=v1.DocName;
                int weight=b1.weight;
                String date =sdf.format(d1);
                String med=v1.med;
                String remarks=v1.remarks;
                String ph1=chain.get(i-1).hash;
                String hash=a.getSHA256Hash(ph1+name1);
                try
                {
                    String query="insert into Block values(?,?,?,?,?,?)";
                    PreparedStatement ps=null;
                    ps=c2.prepareStatement(query);     
                    ps.setInt(1,uid);
                    ps.setString(2,name1);
                    ps.setString(3,date);
                    ps.setInt(4,weight);
                    ps.setString(5,hash);
                    ps.setString(6,ph1);
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
                    //Visit v3=new Visit(Doc,date,weight,med,remarks);
                    //g.add(v3);
                    //String ph="x";
                    //String h=a.getSHA256Hash(ph+name);
                    //Block b1=new Block(uid,name1,dob,w,g,h,ph);
                    chain.add(b1);
                }
                catch (java.sql.SQLException e)
                {
                  System.out.println("Cannot Enter");
                }
            }
        }
        
        
        
        
        /*************Accept From the Server******************/
        
        
        if(m==0)
        {
        try
        {
            ServerSocket ss=new ServerSocket(8889);
            Socket s=ss.accept();
            System.out.println("Connected");
            ObjectInputStream ii=new ObjectInputStream(s.getInputStream());
            Block b11=(Block)ii.readObject();
            Visit v11=b11.v.get(0);
            int uidd=b11.uid;
            int ll=chain.size();
            int ij=0;
            for(ij=0;ij<chain.size();ij++)
            {
                int x=chain.get(ij).uid;
                ZeroKnowledge zk=new ZeroKnowledge(x,uidd);
                if(!zk.verify())
                {
                    System.out.println();
                    System.out.println("The details of the patient already exists");
                    //System.out.println("Enter Doctor Name");
                    String name1=chain.get(ij).PatientName;
                    String Doc_Name=v11.DocName;
                    Date d1 = sdf.parse(v11.u);
                    String date =sdf.format(d1);
                    int weight=chain.get(ij).weight;
                    String med=v11.med;
                    String remarks=v11.remarks;
                    try
                    {
                        String query1="insert into Visit values(?,?,?,?,?,?)";
                        PreparedStatement ps1=null;
                        ps1=c4.prepareStatement(query1);     
                        ps1.setInt(1,uidd);
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
                    chain.get(ij).updateBlock(v11);
                    break;
                }
            }
            if(ij==ll)
            {
                Date d1 = sdf.parse(b11.x);
                //System.out.println("Enter Name,Doc_Name,weight");
                String name1=b11.PatientName;
                String Doc=v11.DocName;
                int weight=b11.weight;
                String date =sdf.format(d1);
                String med=v11.med;
                String remarks=v11.remarks;
                String ph1=chain.get(ij-1).hash;
                String hash=a.getSHA256Hash(ph1+name1);
                try
                {
                    String query="insert into Block values(?,?,?,?,?,?)";
                    PreparedStatement ps=null;
                    ps=c2.prepareStatement(query);     
                    ps.setInt(1,uidd);
                    ps.setString(2,name1);
                    ps.setString(3,date);
                    ps.setInt(4,weight);
                    ps.setString(5,hash);
                    ps.setString(6,ph1);
                    ps.execute();


                    String query1="insert into Visit values(?,?,?,?,?,?)";
                    PreparedStatement ps1=null;
                    ps1=c3.prepareStatement(query1);     
                    ps1.setInt(1,uidd);
                    ps1.setString(2,Doc);
                    ps1.setString(3,date);
                    ps1.setInt(4,weight);
                    ps1.setString(5, med);
                    ps1.setString(6,remarks);
                    ps1.execute();
                    chain.add(b11);
                }
                catch (java.sql.SQLException e)
                {
                  System.out.println("Cannot Enter");
                }
            }
            ss.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        }
        
        /*******Printing the BlockChain*********/
        
        
        for(int j=0;j<chain.size();j++)
        {
            chain.get(j).printAll(); 
            System.out.println("                        *********");
            System.out.println("                        *********");
        }
        
        
    }
}
