package dao;

import entity.Location;
import org.springframework.stereotype.Service;


import javax.persistence.Query;
import java.util.List;

@Service
public class LocationDAO extends AbstractDAO<Location> {
    @Override
    public Class<Location> getEntityClass() {
        return Location.class;
    }


    public List<Location> getAllLocations(){

        Query q=em.createQuery("select l from Location l");
        return q.getResultList();

    }
}
