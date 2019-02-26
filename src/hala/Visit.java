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
    String med[];
    String remarks="";
    public Visit(String doc,Date d2,int w,int n,String m[],String r)
    {
        d1=new Date();
        DocName=doc;
        d1=new Date();
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        age = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        age=age/365;
        //System.out.println("LOL= "+age);
        //age=period.getYears();
        weight=w;
        med=new String[n];
        for(int i=0;i<n;i++)
        {
            med[i]=m[i];
        }
        remarks=r;
    }
}