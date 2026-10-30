package org.example;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMI_Data_Bo3SoPytago {
    static void main() throws Exception {
       String msv = "B23DCCN264";
       String qcode = "NMATI6Zw";

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        DataService service = (DataService) registry.lookup("RMIDataService");

        int n = (int) service.requestData(msv, qcode);
        List<List<Integer>> a = new ArrayList<>();
        for (int i = 1; i <= n-2; i++) {
            for (int j = i+1; j <= n-1; j++) {
                for (int k = j+1; k <= n; k++) {
                    if (i * i + j * j == k * k) {
                        a.add(new ArrayList<>(List.of(i, j, k)));
                    }
                }
            }
        }

        service.submitData(msv, qcode, a);
    }
}
