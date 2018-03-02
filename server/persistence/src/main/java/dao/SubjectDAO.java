package dao;

import entity.Course;
import entity.Subject;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Service
public class SubjectDAO  extends AbstractDAO<Subject> {
    @Override
    public Class<Subject> getEntityClass() {
        return Subject.class;
    }

    /**
     * Return a Subject from specific Course
     * @param  idCourse the id of the Course
     * @param  idSubject the id of the Subject
     * @return Subject
     */
    public Subject findSubjectFromCourse(int idCourse, int idSubject){
        Query query=em.createQuery("select s from Course c inner join c.subjects s where c.idCourse=:idCourse and s.idSubject=:idSubject");
        query.setParameter("idCourse",idCourse);
        query.setParameter("idSubject", idSubject);
        return (Subject) query.getSingleResult();
    }

    public Subject getFirstSubjectFromCourse(Course course){


        Query query=em.createQuery("select s from Subject s where containedByCourse=:course and position=1");
        query.setParameter("course", course);
        return (Subject) query.getSingleResult();
    }

    public Subject findNextSubject(Subject subjectEntity) {

        Query query = em.createQuery("select s from Subject s where s.position=:position and s.containedByCourse=:course");
        query.setParameter("position", subjectEntity.getPosition() + 1);
        query.setParameter("course", subjectEntity.getContainedByCourse());
        try {
            return (Subject) query.getSingleResult();
        }catch (NoResultException e){
            return null;

        }
    }
}
