package service;

import dao.TutorialDAO;
import dto.CourseDTO;
import dto.TutorialDTO;
import dto.mapper.TutorialMapper;
import entity.Course;
import entity.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class TutorialService {

    @Autowired
    private TutorialDAO tutorialDAO;

    private TutorialMapper tutorialMapper= TutorialMapper.INSTANCE;

    public List<TutorialDTO> getAllCourses() {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.allCourses());
    }

    public List<TutorialDTO> filterByKeyword(String keyword) {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.filterByKeyword(keyword));
    }

}
