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
    public Block(int id,String name,Date d1,int w,Visit v1,String h)
    {
        dob=d1;
        uid=id;
        PatientName=name;
        //age=v1.age;
        weight=w;
        v.add(v1);
        no++;
        Doctor[n++]=v1.DocName;
        previous_hash=h;
        hash=calculateHash();
    }
    public String calculateHash()
    {
        return "a1#";
    }
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
    public void printmed()
    {
       for(int i=0;i<n;i++)
       {
           for(int j=no-1;j>=0;j--)
           {
               Visit v1=v.get(j);
               if(v1.DocName==Doctor[i])
               {
                   System.out.println("Doctor Name: "+v1.DocName);
                   int l=v1.med.length;
                   for(int k=0;k<l;k++)
                   {
                       System.out.print(v1.med[k]+" ");
                   }
                   System.out.println();
                   break;
               }
           }
       }
    }
    public void printAll()
    {
        System.out.println("Uid "+uid);
        System.out.println("Name "+PatientName);
        System.out.println("Age "+v.get(0).age);
        System.out.println("Weight "+weight);
        printmed();
    }
}
