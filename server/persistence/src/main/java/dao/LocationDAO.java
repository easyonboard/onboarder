package dao;

import entity.Location;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class LocationDAO extends AbstractDAO<Location> {

    @Override
    public Class<Location> getEntityClass() {

        return Location.class;
    }

    public List<Location> getAllLocations() {

        Query q = em.createQuery("select l from Location l");
        return q.getResultList();

    }

    public Optional<Location> findLocationByName(String name) {

        TypedQuery<Location> query = this.em.createNamedQuery(Location.FIND_LOCATION_BY_NAME, Location.class);
        query.setParameter("name", name);
        Optional<Location> firstLocation = query.getResultList().stream().findFirst();
        return firstLocation;
    }
}
