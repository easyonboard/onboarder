package service;

import dao.MaterialDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import dto.MaterialDTO;
import dto.mapper.MaterialMapper;
import entity.Course;
import entity.Material;
import entity.Subject;
import entity.User;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for {@link MaterialDTO}
 */
@Service
public class MaterialService {

    @Autowired
    private MaterialDAO materialDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;

    public void createMaterial(MaterialDTO materialDTO, Integer idSubject) {
        Material  material = materialMapper.mapToNewEntity(materialDTO);
        List<Subject> subjects = material.getContainedBySubjects();
        if (null==subjects){
            subjects= new ArrayList<>();
        }
        Subject subjectEntity = subjectDAO.findEntity(idSubject);
        subjects.add(subjectEntity);
        material.setContainedBySubjects(subjects);

        material = materialDAO.persistEntity(material);
        List<Material> materials = subjectEntity.getMaterials();
        materials.add(material);
        subjectEntity.setMaterials(materials);
        subjectDAO.persistEntity(subjectEntity);
    }

    public MaterialDTO getMaterialById(Integer id) {
        return materialMapper.mapToDTO(materialDAO.findEntity(id));
    }

    /**
     *
     * @param username String
     * @return List of Materials uploaded by the user
     */
    public List<MaterialDTO> materialUploadedByUser(String username) throws UserNotFoundException {
        Optional<User> user =userDAO.findUserByUsername(username);

        if(user.isPresent()) {
            List<Course> ownerForCourses = user.get().getOwnerForCourses();
            Set<Subject> subjectsWhereUserIdOwner = new HashSet<>();
            Set<Material> materials = new HashSet<>();
            ownerForCourses.stream().forEach(course -> subjectsWhereUserIdOwner.addAll(course.getSubjects()));
            subjectsWhereUserIdOwner.stream().forEach(subject -> materials.addAll(subject.getMaterials()));
            return materialMapper.entitiesToDTOs(new ArrayList<>(materials));
        }else {
            throw  new UserNotFoundException("User not found!");
        }
    }
}
