package service;

import dao.TutorialDAO;
import dao.UserDAO;
import dto.CourseDTO;
import dto.TutorialDTO;
import dto.mapper.TutorialMapper;
import entity.Course;
import entity.Tutorial;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialService {

    @Autowired
    private TutorialDAO tutorialDAO;

    @Autowired
    private UserDAO userDAO;

    private TutorialMapper tutorialMapper= TutorialMapper.INSTANCE;

    public List<TutorialDTO> getAllCourses() {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.allCourses());
    }

    public List<TutorialDTO> filterByKeyword(String keyword) {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.filterByKeyword(keyword));
    }

    public TutorialDTO addTutorial(TutorialDTO tutorialDTO, List<Integer> ownersIds, List<Integer> contactPersonsId) {
        Tutorial tutorial = tutorialMapper.mapToEntity(tutorialDTO, new Tutorial());
//        List<Tutorial> owners = new ArrayList<>();

//        List<User> constantPerson = new ArrayList<>();
//        contactPersonsId.forEach(cpId -> constantPerson.add(userDAO.findEntity(cpId)));

        //tutorial.setOwners(owners);
//        tutorial.setContactPersons(constantPerson);

        return tutorialMapper.mapToDTO(tutorialDAO.persistEntity(tutorial));
    }
}
