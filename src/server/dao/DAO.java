package server.dao;

import models.Organization;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface DAO {
    Long add(Organization organization);
    void update(Long id, Organization organization);
    void remove(Long id);
    void clear();
    Organization get(Long id);
    Collection<Organization> getAll();
    int size();
    Long getAvailableId();
    Collection<Organization> show();
    ZonedDateTime getInitDate();
    void sort();
    void readCollection(String o);
    void saveCollectionToFile(String o);
}
