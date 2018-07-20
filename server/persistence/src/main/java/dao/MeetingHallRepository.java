package dao;

import entity.MeetingHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Service
//public class MeetingHallRepository extends AbstractDAO<MeetingHall> {
//    @Override
//    public Class<MeetingHall> getEntityClass() {
//        return MeetingHall.class;
//    }
//
//    public List<MeetingHall> getAllRooms(){
//
//        Query q=em.createQuery("select l from MeetingHall l");
//        return q.getResultList();
//
//    }
//}

// TODO : pe viitor vom folosi Spring data JPA.
/** E un feature Spring care usureaza munca -
 *  aste e si ideea pentru care folosim Spring
 */
public interface MeetingHallRepository extends JpaRepository<MeetingHall, Integer> {

    List<MeetingHall> findByLocation(String location);

    MeetingHall findByHallName(String hallName);

}
