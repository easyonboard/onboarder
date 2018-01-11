package dao;

import entity.Subject;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

@Service
public class SubjectDAO  extends AbstractDAO<Subject> {
    @Override
    public Class<Subject> getEntityClass() {
        return Subject.class;
    }


    public Subject findSubjectFromCourse(int idCourse, int idSubject){
        Query query=em.createQuery("select s from Course c inner join c.subjects s where c.idCourse=:idCourse and s.idSubject=:idSubject");
        query.setParameter("idCourse",idCourse);
        query.setParameter("idSubject", idSubject);
        return (Subject) query.getSingleResult();
    }
}
