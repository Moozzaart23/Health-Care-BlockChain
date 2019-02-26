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
 * @author ANISH and AKHIL
 */
public class Hala 
{
    public static void main(String[] args) throws Exception
    {
        ArrayList<Block> chain=new ArrayList<Block> ();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date d1 = sdf.parse("21/12/1998");
        //Date d1=new Date(1998,12,21);
        String arr[]={"Crocin"};
        String arr1[]={"Calpol"};
        Visit v1=new Visit("Akhil",d1,50,1,arr,"Fever");
        Block b1=new Block(11,"Anish",d1,50,v1,"0");
        chain.add(b1);
        //chain.get(0).printAll();
        Visit v2=new Visit("Medam",d1,50,1,arr1,"Fever1");
        chain.get(0).updateBlock(v2);
        chain.get(0).printAll();
    }
}
