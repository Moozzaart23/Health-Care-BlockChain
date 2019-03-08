/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala;
import java.util.*;
import java.util.Date;
import java.time.Month;
import java.text.SimpleDateFormat;
/**
 *
 * @author ANISH
 */
public class Block 
{
    int uid;
    String PatientName;
    long age;
    int weight;
    int no=0;
    Date dob;
    //String Allergies[];
    String Doctor[]=new String[100];
    int n=0;
    ArrayList<Visit> v=new ArrayList<Visit> ();
    String hash;
    String previous_hash;
    public Block(int id,String name,String d11,int w,ArrayList<Visit> v1,String h,String ph)
    {
        //dob=d1;
        try
        {
            dob=new SimpleDateFormat("dd/MM/yyyy").parse(d11);
        }
         catch(Exception e)
         {
             System.out.println(e);
         }
        uid=id;
        PatientName=name;
        //age=v1.age;
        weight=w;
        //System.out.println(v1.size());
        for(int i=0;i<v1.size();i++)
        {
            v.add(v1.get(i));
        }
        //Doctor[n++]=v1.DocName;
        previous_hash=ph;
        hash=h;
    }
    /*public String calculateHash()
    {
        return "a1#";
    }*/
    public void updateBlock(Visit v1)
    {
        v.add(v1);
        no++;
        int i;
        for(i=0;i<n;i++)
        {
            if(Doctor[i]==v1.DocName)
            {
                break;
            }
        }
        if(i==n)
        {
            Doctor[n++]=v1.DocName;
        }
    }
    public void printAll()
    {
        System.out.println("Uid "+uid);
        System.out.println("Name "+PatientName);
        //System.out.println("Age "+v.get(0).age);
        System.out.println("Weight "+weight);
        System.out.println("MEDICINES and REMARKS ::::");
        for(int i=0;i<v.size();i++)
        {
            System.out.println("Medicine= "+v.get(i).med);
            System.out.println("Remarks= "+v.get(i).remarks);
        }
        
    }
}
