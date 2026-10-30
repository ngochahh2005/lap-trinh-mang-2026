package org.example;

import RMI.ObjectService;
import RMI.TicketSla;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_Object {
    static void main(String[] args) throws Exception {
        String msv = "B23DCCN264";
        String qcode = "ITLPFamr";

        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        ObjectService service = (ObjectService) registry.lookup("RMIObjectService");

        TicketSla ticketSla = (TicketSla) service.requestObject(msv, qcode);

        switch (ticketSla.getPriority()) {
            case "CRITICAL":
                if (ticketSla.getOpenedHoursAgo() > 2) ticketSla.setBreached(true);
                break;
            case "HIGH":
                if (ticketSla.getOpenedHoursAgo() > 8) ticketSla.setBreached(true);
                break;
            case "MEDIUM":
                if (ticketSla.getOpenedHoursAgo() > 24) ticketSla.setBreached(true);
                break;
            default:
                if (ticketSla.getOpenedHoursAgo() > 72) ticketSla.setBreached(true);
                break;
        }

        if (!ticketSla.isBreached()) ticketSla.setAction("MONITOR");
        else if (ticketSla.getPriority().equals("CRITICAL") || ticketSla.getOpenedHoursAgo() > 96) ticketSla.setAction("ESCALATE_L2");
        else ticketSla.setAction("ESCALATE_L1");

        service.submitObject(msv, qcode, ticketSla);
    }
}
