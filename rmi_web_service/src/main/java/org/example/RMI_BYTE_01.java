package org.example;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RMI_BYTE_01 {
    static void main() throws Exception {
        String msv = "B23DCCN264";
        String qcode = "hylEK0Cp";
        String host = "36.50.135.242";
        int port = 1099;

        // ket noi den MRI Registry
        Registry registry = LocateRegistry.getRegistry(host, port);

        // lay doi tuong ByteService tu Registry
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        // nhan du lieu tu server
        byte[] data = service.requestData(msv, qcode);

        Map<Byte, Integer> mp = new LinkedHashMap<>();
        for (byte b : data) {
            mp.put(b, mp.getOrDefault(b, 0) + 1);
        }

        byte minEle = data[0];
        int minCnt = mp.get(minEle);

        for (Map.Entry<Byte, Integer> e : mp.entrySet()) {
            if (e.getValue() < minCnt) {
                minEle = e.getKey();
                minCnt = e.getValue();
            }
        }

        byte[] res = new byte[]{minEle, (byte) minCnt};

        service.submitData(msv, qcode, res);
    }
}
