/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala;

/**
 *
 * @author ANISH
 */
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

public class SHA256
{ 
    String getSHA256Hash(String data)
    {
        String result = null;
	try 
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash);
	}
        catch(Exception ex) 
        {
            System.out.println("lol");
            ex.printStackTrace();
	}
	return result;
    }
    private String bytesToHex(byte[] hash) 
    {
	return DatatypeConverter.printHexBinary(hash);
    }
}
