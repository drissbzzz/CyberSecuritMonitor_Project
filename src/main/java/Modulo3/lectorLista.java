/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
/**
 *
 * @author driss
 */
public class lectorLista {
    public static void main(String[] args){
        ArrayList<String> nombres = new ArrayList<>();
        String textoEjemplo= "[\"MinerThread\",\"SpyWare\",\"MalwareHider\"]";
        String regex = "\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textoEjemplo);
        while(matcher.find()){
            nombres.add(matcher.group(1));
        }
        System.out.println("Detectado: "+nombres.get(0));
    }
}
