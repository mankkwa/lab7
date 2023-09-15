package server.dao;

import server.FileManager;
import server.OrganizationComparator;
import models.Organization;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public final class PriorityQueueDAO implements DAO {
    private static PriorityQueueDAO pqd = new PriorityQueueDAO();
    private static PriorityQueue<Organization> collection = new PriorityQueue<>();
    private static Long availableId = 0L;
    private final ZonedDateTime initDate;
    private static OrganizationComparator orgComp;

    public PriorityQueueDAO(){
        initDate = ZonedDateTime.now();
        //pqd.readCollection();
    }

    @Override
    public ZonedDateTime getInitDate(){
        return initDate;
    }

    @Override
    public Long add(Organization organization){
        collection.add(organization);
        organization.setId(availableId);
        organization.setCreationDate(ZonedDateTime.now());
        return availableId++;
    }

    public void setAvailableId(){
        if (collection.isEmpty()){
            availableId = 0L;
        } else {
            availableId = getMaxId() + 1;
        }
    }

    public Long getMaxId(){
        Long maxId = null;
        for (Organization org : collection){
            Long orgId = org.getId();
            if(orgId != null && (maxId == null || orgId > maxId)){
                maxId = orgId;
            }
        }
        return maxId;
    }

    @Override
    public void update(Long id, Organization updOrganization) {
        Organization thatOrganization = get(id);
        if (thatOrganization != null) {
            thatOrganization.setName(updOrganization.getName());
            thatOrganization.setCoordinates(updOrganization.getCoordinates());
            thatOrganization.setCreationDate(updOrganization.getCreationDate());
            thatOrganization.setAnnualTurnover(updOrganization.getAnnualTurnover());
            thatOrganization.setFullName(updOrganization.getFullName());
            thatOrganization.setEmployeesCount(updOrganization.getEmployeesCount());
            thatOrganization.setType(updOrganization.getType());
            thatOrganization.setPostalAddress(updOrganization.getPostalAddress());
        }
    }

    @Override
    public void remove(Long id) {
        Organization thatOrganization = get(id);
        if (thatOrganization != null){
            collection.remove(thatOrganization);
        }
    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public Organization get(Long id) {
        return collection.stream().filter(humanBeing -> humanBeing.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Collection<Organization> getAll() {
        return collection;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public Long getAvailableId(){
        return availableId;
    }

    @Override
    public Collection<Organization> show() {
        if (collection.isEmpty()) return null;
        return (pqd.getAll());
    }

    @Override
    public void sort(){
        Organization[] organizations = collection.toArray(new Organization[0]);
        Arrays.sort(organizations, orgComp);
        collection.clear();
        collection.addAll(Arrays.asList(organizations));
    }

    @Override
    public void saveCollectionToFile(String output) {
        try (FileWriter writer = new FileWriter(output)) {
            // Очистить файл, записывая пустую строку
            writer.write("");
        FileManager.writeCollection(pqd.getAll(), output);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readCollection(String input){
        collection = FileManager.readCollection(input);
    }

    public void removeGreater(Organization organization) {
        for (Organization organization1 : pqd.getAll()) {
            if (organization1.compareTo(organization) > 0) {
                pqd.remove(organization1.getId());
            }
        }
    }

    public Organization firstOrganization(){
        return collection.peek();
    }

    public Float averageOfAnnual() {
        Float averageOfAnnual = null;
        int n = 0;
        for (Organization organization : pqd.getAll()) {
            averageOfAnnual += organization.getAnnualTurnover();
            n += 1;
        }
        return averageOfAnnual / n;
    }
}
