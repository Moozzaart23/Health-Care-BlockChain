/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala;
import java.util.*;
import java.io.*;
import java.util.Date;
import java.time.Month;
import java.text.SimpleDateFormat;
/**
 *
 * @author ANISH and AKHIL Mittal
 */
public class Hala 
{
    public static void main(String[] args) throws Exception
    {
        ArrayList<Block> chain=new ArrayList<Block> ();
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
        }
    }
}
