package org.example;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RMI_Data_PhanTuLonThuK {
    static void main() throws Exception {
        String msv = "B23DCCN264";
        String qcode = "wzG6G9ji";

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        DataService service = (DataService) registry.lookup("RMIDataService");

        String[] data = service.requestData(msv, qcode).toString().split(";");
        int k = Integer.parseInt(data[1].trim());
        List<Integer> a = new ArrayList<>();
        for (String s : data[0].split(",")) {
            a.add(Integer.parseInt(s));
        }
        Set<Integer> set = new TreeSet<>(a);
        List<Integer> l = new ArrayList<>(set);
        l.sort((a1, a2) -> {
            return a2 - a1;
        });
        service.submitData(msv, qcode, l.get(k-1));
    }
}
