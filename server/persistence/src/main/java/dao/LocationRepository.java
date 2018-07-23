package dao;

import entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO : pe viitor vom folosi Spring data JPA.

/**
 * E un feature Spring care usureaza munca -
 * aste e si ideea pentru care folosim Spring
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {

}