package org.example;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMI_Byte_NenRLE {
    static void main() throws Exception {
        String msv = "B23DCCN264", qcode = "2uG0lQGi";

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(msv, qcode);
        List<Byte> res = new ArrayList<>();
        int f = 1;
        for (int i = 1; i < data.length; i++) {
            if (data[i] == data[i-1]) f++;
            else {
                res.add(data[i-1]);
                res.add(Byte.parseByte(String.valueOf(f)));
                f = 1;
            }
        }

        res.add(data[data.length-1]);
        res.add(Byte.parseByte(String.valueOf(f)));

        byte[] kq = new byte[res.size()];
        for (int i = 0; i < res.size(); i++) {
            kq[i] = res.get(i);
        }

        service.submitData(msv, qcode, kq);
    }
}
