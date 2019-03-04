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
import java.util.concurrent.*;
/**
 *
 * @author ANISH
 */
public class Visit
{
    Date d1;
    String DocName="";
    long age;int weight;
    String med;
    String remarks="";
    public Visit(String doc,String d22,int w,String m,String r)
    {
        d1=new Date();
        DocName=doc;
        //d1=new Date();
        Date d2=new Date();
        try
        {
            d2=new SimpleDateFormat("dd/MM/yyyy").parse(d22);
        }
         catch(Exception e)
         {
             System.out.println(e);
         }
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        age = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        age=age/365;
        //System.out.println("LOL= "+age);
        //age=period.getYears();
        weight=w;
        med=m;
        /*for(int i=0;i<n;i++)
        {
            med[i]=m[i];
        }*/
        remarks=r;
    }
}