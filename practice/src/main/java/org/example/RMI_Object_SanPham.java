package org.example;

import RMI.ObjectService;
import RMI.Product;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_Object_SanPham {
    static void main(String[] args) {
        try {
            String msv = "B23DCCN264", qcode = "dx3nt4Ij";

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ObjectService service = (ObjectService) registry.lookup("RMIObjectService");

            Product product = (Product) service.requestObject(msv, qcode);
            System.out.println(product);
            product.updateCode();
            product.updateExportPrice();

            service.submitObject(msv, qcode, product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
