package org.example;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class RMI_Byte_PhanTuXuatHienNhieuLanNhat {
    static void main() throws Exception {
        String msv = "B23DCCN264", qcode = "z8E5DcB7";

        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(msv, qcode);
        Map<Byte, Integer> mp = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            mp.put(data[i], mp.getOrDefault(data[i], 0) + 1);
        }

        byte key = 0;
        int f = 0;
        for (byte x : data) {
            if (f < mp.get(x)) {
                key = x;
                f = mp.get(x);
            }
        }

        System.out.println(key + " - " + f);
        service.submitData(msv, qcode, new byte[]{key, Byte.parseByte(String.valueOf(f))});
    }
}
