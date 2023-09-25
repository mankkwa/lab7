package server.dao;

import other.models.Organization;

import java.io.IOException;
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
    String show();
    void sort();
    void setAvailableId();
    Float averageOfAnnual();
    Organization firstOrganization();
    void removeGreater(Organization organization);
    void save() throws IOException;
}
