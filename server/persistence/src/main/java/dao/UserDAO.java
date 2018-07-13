package dao;

import entity.CheckList;

import entity.User;
import entity.enums.DepartmentType;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static entity.enums.RoleType.ROLE_ABTEILUNGSLEITER;

@Service
public class UserDAO extends AbstractDAO<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    /**
     * Identify a user by username
     *
     * @param username String
     * @return User
     */
    public Optional<User> findUserByUsername(String username) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);
        Optional<User> firstUser = query.getResultList().stream().findFirst();
        return firstUser;
    }

    public User userByUsername(String username) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);
        User firstUser = query.getResultList().stream().findFirst().get();
        return firstUser;
    }

    /**
     * Identify a user by email
     *
     * @param email the user email
     * @return User
     */
    public Optional<User> findUserByEmail(String email) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        Optional<User> firstUser = query.getResultList().stream().findFirst();
        return firstUser;
    }

    public List<User> getAllUsers() {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);
        criteriaQuery.select(cb.construct(User.class, rootUser.get("idUser"), rootUser.get("name"),
                rootUser.get("username"),rootUser.get("email"), rootUser.get("msgMail")));
        return (List<User>) this.executeCriteriaQuery(criteriaQuery);
    }



    public List<User> getAbteilungsleiters() {
//        String queryString = "select u from User u where u.role.role=:role";
//        Query query = this.em.createQuery(queryString);
//        query.setParameter("role", ROLE_ABTEILUNGSLEITER);
//        return query.getResultList();
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);
        criteriaQuery.select(cb.construct(User.class,
                rootUser.get("idUser"),
                rootUser.get("name"),
                rootUser.get("username"),
                rootUser.get("email"),
                rootUser.get("msgMail"))).
                where(cb.equal(rootUser.get("role").get("role"),ROLE_ABTEILUNGSLEITER));
        List<User> users = this.executeCriteriaQuery(criteriaQuery);
        return users;
    }


    public List<User> searchByName(String name) {

//        String queryString = "select u from User u where u.name LIKE :name";
//        Query query = this.em.createQuery(queryString);
//        query.setParameter("name", "%" + name + "%");
//        return query.getResultList();
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);
        criteriaQuery.select(cb.construct(User.class,
                rootUser.get("idUser"),
                rootUser.get("name"),
                rootUser.get("username"),
                rootUser.get("email"),
                rootUser.get("msgMail"))).
                where(cb.like(cb.upper(rootUser.get("name")), "%" + name.toUpperCase() + "%"));
        List<User> users = this.executeCriteriaQuery(criteriaQuery);
        return users;

    }

    public List<User> getUsersInDepartment(String department) {

        String queryString = "select ui.userAccount from UserInformation ui where ui.department=:department";
        Query query = this.em.createQuery(queryString);
        query.setParameter("department", DepartmentType.valueOf(department));
        return query.getResultList();

//        CriteriaBuilder cb = this.getCriteriaBuilder();
//        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
//        Root<UserInformation> rootUser = criteriaQuery.from(UserInformation.class);
//        criteriaQuery.select(cb.construct(User.class,
//                rootUser.get("userAccount.idUser"),
//                rootUser.get("userAccount.name"),
//                rootUser.get("userAccount.username"),
//                rootUser.get("userAccount.email")
// ));
////                where(cb.equal(rootUser.get("department"),department));
//        List<User> users = this.executeCriteriaQuery(criteriaQuery);
//        return users;
    }

    /**
     * returns map of checklist properties and values for a particular user
     */
    public Map<String, Boolean> getCheckListMapForUser(User user) {
        String queryString = "select cl from CheckList cl where cl.userAccount=:user";
        Query query = em.createQuery(queryString);
        query.setParameter("user", user);
        try {
            CheckList checkList = (CheckList) query.getSingleResult();
            Map checkListMap = new HashMap();
            Field fields[] = checkList.getClass().getDeclaredFields();
            boolean value = false;
            String attribute;
            for (int i = 2; i < fields.length; i++) {

                attribute = fields[i].getName();
                try {
                    fields[i].setAccessible(true);
                    value = (boolean) fields[i].get(checkList);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                checkListMap.put(attribute, value);
            }

            return checkListMap;
        } catch (NoResultException e) {

            return null;

        }
    }


    public String getDepartmentForUser(String username) {

        Optional<User> userOptional = findUserByUsername(username);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            String queryString = "select ui.department from UserInformation ui where ui.userAccount=:user";
            Query query = this.em.createQuery(queryString);
            query.setParameter("user", user);
            DepartmentType departmentType = (DepartmentType) query.getSingleResult();
            return departmentType.toString();
        }
        return null;
    }
}