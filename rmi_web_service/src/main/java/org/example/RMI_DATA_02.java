package org.example;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Locale;

public class RMI_DATA_02 {
    static void main(String[] args) throws Exception {
        String msv = "B23DCCN264";
        String qcode = "gQ8Nlzeu";

        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        DataService service = (DataService) registry.lookup("RMIDataService");

        String data = service.requestData(msv, qcode).toString();
        System.out.println(data);
        String[] str = data.split(",");
        int n = str.length;

        double[] a = new double[n];
        double average = 0, sum = 0;
        for (int i = 0; i < n; i++) {
            double x = Double.parseDouble(str[i].trim());
            a[i] = x;
            sum += x;
        }
        average = sum / n;

        double variance = 0;
        for (int i = 0; i < n; i++) {
            variance += Math.pow(a[i] - average, 2);
        }
        double s = Math.sqrt(variance / n);
        Arrays.sort(a);
        int idx = (int) Math.ceil(n * 0.95) - 1;
        double p95 = a[idx];

        String res = String.format(Locale.US, "average=%.2f;stddev=%.2f;p95=%.2f", average, s, p95);
        System.out.println(res);

        service.submitData(msv, qcode, res);
    }
}
