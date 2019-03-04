/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala;
//import bldm;
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
                   // v.add(new Visit())
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
            System.out.println("*********");
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /*Scanner sc=new Scanner (System.in);
        System.out.println("Enter uid");
        int uid=sc.nextInt();*/
        /*ArrayList<Block> chain=new ArrayList<Block> ();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Scanner sc=new Scanner (System.in);
        System.out.println("Enter uid");
        int uid=sc.nextInt();
        
        //
        
        
        Date d1 = sdf.parse("21/12/1998");
        //Date d1=new Date(1998,12,21);
        String arr[]={"Crocin"};
        String arr1[]={"Calpol"};
        Visit v1=new Visit("Akhil",d1,50,1,arr,"Fever");
        Block b1=new Block(11,"Anish",d1,50,v1,"0");
        chain.add(b1);
        
        
        
        
        
        
        
        
        Date d2 = sdf.parse("22/12/1998");
        //Date d1=new Date(1998,12,21);
        String arr2[]={"Crocinn"};
        String arr12[]={"Calpoll"};
        Visit v12=new Visit("Akhill",d1,50,1,arr,"Feverr");
        Block b12=new Block(12,"Anishh",d1,50,v12,"0");
        chain.add(b12);
        
        
        
        
        
        
        
        int l=chain.size();
        //System.out.println("Size= "+l);
        int i;
        
        
        
        
        
        
        for(i=0;i<chain.size();i++)
        {
            int x=chain.get(i).uid;
            ZeroKnowledge zk=new ZeroKnowledge(x,uid);
            if(!zk.verify())
            {
                System.out.println("The details of the patient already exists");
                break;
            }
        }
        if(i==l)
        {
            Date d3 = sdf.parse("23/12/1998");
            //Date d1=new Date(1998,12,21);
            String arr3[]={"Crocinnn"};
            String arr13[]={"Calpolll"};
            Visit v123=new Visit("Akhilll",d1,50,1,arr,"Feverrr");
            Block b123=new Block(uid,"Anishhh",d1,50,v123,"0");
            chain.add(b123);
        }
        
        
        
        
        
        //chain.get(0).printAll();
        //Visit v2=new Visit("Medam",d1,50,1,arr1,"Fever1");
        //chain.get(0).updateBlock(v2);
        //chain.get(0).printAll();
        
        
        
        
        
        
        
        for(int j=0;j<chain.size();j++)
        {
            chain.get(j).printAll();
            System.out.println("*********");
        }*/
    }
}
