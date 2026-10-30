package org.example;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class RMI_Byte_PhepXor {
    static void main() throws Exception {
        String msv = "B23DCCN264";
        String qcode = "4BraNTI5";

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(msv, qcode);
        System.out.println(Arrays.toString(data));
        byte[] key = "PTIT".getBytes();
        byte[] res = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            res[i] = (byte) (data[i] ^ key[i % 4]);
        }

        service.submitData(msv, qcode, res);
    }
}
