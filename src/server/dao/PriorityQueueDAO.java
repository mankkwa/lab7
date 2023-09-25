package server.dao;

import server.FileManager;
import server.OrganizationComparator;
import other.models.Organization;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.*;

public final class PriorityQueueDAO implements DAO {
    private PriorityQueue<Organization> collection = new PriorityQueue<>();
    private static Long availableId = 0L;
    private static PriorityQueueDAO pqd;
    private ZonedDateTime initDate;


    public PriorityQueueDAO(PriorityQueue<Organization> collection){
        this.collection = collection;
        initDate = ZonedDateTime.now();
        setAvailableId();
    }

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
    public String show() {
        if (collection.isEmpty()) return null;
        return collection.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    @Override
    public void sort(){
        Organization[] organizations = collection.toArray(new Organization[0]);
        Arrays.sort(organizations, new OrganizationComparator());
        collection.clear();
        collection.addAll(Arrays.asList(organizations));
    }

    @Override
    public void save() {
        FileManager.writeCollection(collection);
    }


    public void removeGreater(Organization organization) {
        collection.removeIf(organization1 -> organization1.compareTo(organization) > 0);
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
