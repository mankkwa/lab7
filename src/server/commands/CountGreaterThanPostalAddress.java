package server.commands;

import server.dao.DAO;
import server.dao.PriorityQueueDAO;
import other.models.Address;
import other.models.Organization;

public class CountGreaterThanPostalAddress implements Command{
    private final DAO pqd;

    public CountGreaterThanPostalAddress(DAO pqd) {
        this.pqd = pqd;
    }

    @Override
    public Object execute(Object obj) {
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
            return obj;
        } catch (NullPointerException e){
            return ("count_greater_than_postal_address: Ничего найти не удалось :(");
        }
    }
}
