package org.example;

import RMI.CharacterService;

import java.net.URI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_Character_01 {
    static void main(String[] args) throws Exception {
        String msv = "B23DCCN264";
        String qcode = "efoBuMXc";

        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

        String s = service.requestCharacter(msv, qcode);
        String encoded = new URI(null, null, s, null).toASCIIString();
        service.submitCharacter(msv, qcode, encoded);
    }
}
