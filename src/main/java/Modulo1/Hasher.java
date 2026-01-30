/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author driss
 */
public class Hasher {
    
    public String hash(byte[] conversion){
       try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(conversion);
            byte[] hasheado = md.digest();
            return bytesToHex(hasheado);
        } catch (NoSuchAlgorithmException e) {
            return "Error: "+e.getMessage();
        }       
    }  
    private static String bytesToHex(byte[] bytes) {
        StringBuilder out = new StringBuilder();
        for (byte b : bytes) {
            out.append(String.format("%02X", b));
        }
        return out.toString();
    }
}
