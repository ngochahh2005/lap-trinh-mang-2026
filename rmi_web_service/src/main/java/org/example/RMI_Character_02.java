package org.example;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_Character_02 {
    static String intToRoman(int n) {
        int[] a = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] b = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV" , "I"};
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < a.length; i++) {
            while (n >= a[i]) {
                n -= a[i];
                res.append(b[i]);
            }
        }
        return res.toString();
    }
    static void main(String[] args) throws Exception{
        String msv = "B23DCCN264";
        String qcode = "n9SDGP1V";

        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

        String s = service.requestCharacter(msv, qcode);
        int n = Integer.parseInt(s);
        System.out.println(n);
        String res = intToRoman(n);
        service.submitCharacter(msv, qcode, res);
    }
}
