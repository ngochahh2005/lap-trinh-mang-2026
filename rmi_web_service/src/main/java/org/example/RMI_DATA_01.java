package org.example;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RMI_DATA_01 {
    static void main(String[] args) throws Exception {
        String msv = "B23DCCN264";
        String qcode = "FudPegmn";

        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        DataService service = (DataService) registry.lookup("RMIDataService");

        int[] a = (int[]) service.requestData(msv, qcode);
        List<Integer> res = new ArrayList<>();
        System.out.println(Arrays.toString(a));
        for (int i = 0; i < a.length; i++) {
            if (i == 0 && a[i] > a[i+1]) res.add(i + 1);
            else if (i == a.length - 1 && a[i] > a[i-1]) res.add(i + 1);
            else if (i > 0 && i + 1 < a.length && a[i] > a[i-1] && a[i] > a[i+1]) res.add(i + 1);
        }
        service.submitData(msv, qcode, res);
    }
}
