package org.example;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class RMI_Data_HoanViKeTiep {
    static void hoanViKeTiep(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] > a[i+1]) i--;
        if (i < 0) {
            reverse(a, 0, a.length-1);
            return;
        }
        int j = a.length - 1;
        while (a[j] <= a[i]) j--;
        swap(a, i, j);
        reverse(a, i+1, a.length-1);
    }

    static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    static void reverse(int[] a, int l, int r) {
        while (l < r) {
            swap(a, l, r);
            l++; r--;
        }
    }

    static void main() throws Exception {
        String msv = "B23DCCN264";
        String qcode = "3Qe0Qb5w";

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        DataService service = (DataService) registry.lookup("RMIDataService");

        String[] data = service.requestData(msv, qcode).toString().split(",");
        System.out.println(Arrays.toString(data));
        int[] a = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            a[i] = Integer.parseInt(data[i]);
        }
        hoanViKeTiep(a);
        System.out.println(Arrays.toString(a));

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i != 0) res.append(",");
            res.append(a[i]);
        }
        System.out.println(res);

        service.submitData(msv, qcode, res.toString());
    }
}
