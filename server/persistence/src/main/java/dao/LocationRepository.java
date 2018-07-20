package dao;

import entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

//@Service
//public class LocationRepository extends AbstractDAO<Location> {
//
//    @Override
//    public Class<Location> getEntityClass() {
//
//        return Location.class;
//    }
//
//    public List<Location> getAllLocations() {
//
//        Query q = em.createQuery("select l from Location l");
//        return q.getResultList();
//
//    }
//
//    public Optional<Location> findLocationByName(String name) {
//
//        TypedQuery<Location> query = this.em.createNamedQuery(Location.FIND_LOCATION_BY_NAME, Location.class);
//        query.setParameter("name", name);
//        Optional<Location> firstLocation = query.getResultList().stream().findFirst();
//        return firstLocation;
//    }
//}

// TODO : pe viitor vom folosi Spring data JPA.

/**
 * E un feature Spring care usureaza munca -
 * aste e si ideea pentru care folosim Spring
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {

}