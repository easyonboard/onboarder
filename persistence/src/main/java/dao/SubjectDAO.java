package dao;

import entity.Subject;
import org.springframework.stereotype.Service;

@Service
public class SubjectDAO  extends AbstractDAO<Subject> {
    @Override
    public Class<Subject> getEntityClass() {
        return Subject.class;
    }
}
