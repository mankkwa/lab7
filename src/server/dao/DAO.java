package server.dao;

import other.models.Organization;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collection;

public interface DAO {
    Long add(Organization organization);
    boolean update(Organization organization);
    boolean remove(Long id);
}
