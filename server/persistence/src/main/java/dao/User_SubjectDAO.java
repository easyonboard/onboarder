package dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import entity.Course;
import entity.Subject;
import entity.User;
import entity.User_Subject;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class User_SubjectDAO extends AbstractDAO<User_Subject> {
    @Override
    public Class<User_Subject> getEntityClass() {
        return User_Subject.class;
    }


    public List<User_Subject> getSubjectsCompletedByUser(User user, Course course) {

        Query query = em.createQuery("select us from User_Subject us where us.user=:user and us.course=:course and us.status=1 ");
        query.setParameter("user", user);
        query.setParameter("course", course);
        return query.getResultList();

    }


    @Transactional
    public void deleteEntriesWhenUnenroll(User user, Course course) {

        Query query = em.createQuery("delete  from User_Subject us where us.course= :course and us.user= :user");
        query.setParameter("user", user);
        query.setParameter("course", course);
        query.executeUpdate();
    }

    public User_Subject findEntityByUserAndSubject(User user, Subject subject) {


        Query query = em.createQuery("select us from User_Subject us where us.user=:user and us.subject=:subject");
        query.setParameter("user", user);
        query.setParameter("subject", subject);

        try {
            return (User_Subject) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean isSubjectFinished(User userEntity, Subject subjectEntity) {
    Query query=em.createQuery("select us.status from User_Subject us where us.user= :user and us.subject= :subject");
    query.setParameter("user", userEntity);
    query.setParameter("subject", subjectEntity);
    try {
        return (Boolean)query.getSingleResult();
    }catch (NoResultException e){
        return  false;
    }


    }
}
