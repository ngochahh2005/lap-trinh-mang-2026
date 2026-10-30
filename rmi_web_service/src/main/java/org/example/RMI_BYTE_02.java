package org.example;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class RMI_BYTE_02 {
    static void main(String[] args) throws Exception {
        String msv = "B23DCCN264";
        String qcode = "z8E5DcB7";
        String host = "36.50.135.242";
        int port = 1099;

        Registry registry = LocateRegistry.getRegistry(host, port);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(msv, qcode);
        Map<Byte, Integer> mp = new LinkedHashMap<>();
        for (byte b : data) {
            mp.put(b, mp.getOrDefault(b, 0) + 1);
        }

        byte maxEle = data[0];
        int maxCnt = mp.get(maxEle);

        for (Map.Entry<Byte, Integer> e : mp.entrySet()) {
            if (maxCnt < e.getValue()) {
                maxCnt = e.getValue();
                maxEle = e.getKey();
            }
        }

        byte[] res = new byte[]{maxEle, (byte) maxCnt};
        service.submitData(msv, qcode, res);
    }
}
