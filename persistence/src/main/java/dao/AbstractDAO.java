package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public abstract class AbstractDAO<E> {

    @Autowired
    private EntityManager em;


    public abstract Class<E> getEntityClass();

    @Transactional
    public E persistEntity(E entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Transactional
    public void deleteEntity(E entity){
        em.remove(entity);
    }


    @Transactional
    public E findEntity(int id){
        return em.find(getEntityClass(), id);
    }



    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
