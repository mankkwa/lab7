package server.commands;

import server.dao.PriorityQueueDAO;
import models.Address;
import models.Organization;

public class CountGreaterThanPostalAddress implements Command{
    private PriorityQueueDAO pqd = new PriorityQueueDAO();

    @Override
    public void execute(Object obj) {
        Organization organization = (Organization) obj;
        Address postalAddress = organization.getPostalAddress();
        int i = 0;
        try {
            for(Organization organizationField : pqd.getAll()){
                Address higherAddress = organizationField.getPostalAddress();
                if (postalAddress.compareTo(higherAddress) < 0){
                    i++;
                }
            }
            System.out.println("Количество элементов, превышающих postalAddress равно: " + i);
        } catch (NullPointerException e){
            System.err.println("count_greater_than_postal_address: Ничего найти не удалось :(");
        }
    }
}
