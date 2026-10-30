package org.example;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RMI_Character_XyLyXauKyTu {
    static void main() throws Exception {
        String msv = "B23DCCN264";
        String qcode = "NFldNPp6";

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

        String[] data = service.requestCharacter(msv, qcode).split("[{},]");
        System.out.println(Arrays.toString(data));

        List<String> chan = new ArrayList<>(), le = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i].isEmpty()) continue;
            String s = data[i].replaceAll("[\"\\\\]", "").trim();
            if (i % 2 == 0) chan.add(s);
            else le.add(s);
        }

        String res = String.join(", ", le) + "; " + String.join(", ", chan);

        service.submitCharacter(msv, qcode, res);
    }
}
