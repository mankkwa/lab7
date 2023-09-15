package server;

import models.Organization;

import java.util.Comparator;

public class OrganizationComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization o1, Organization o2) {
       return o1.compareTo(o2);
    }
}
