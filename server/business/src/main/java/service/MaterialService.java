package service;

import dao.MaterialDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import dto.MaterialDTO;
import dto.TutorialMaterialDTO;
import dto.mapper.MaterialMapper;
import entity.Course;
import entity.Material;
import entity.Subject;
import entity.User;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.MaterialValidator;

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

    @Autowired
    private MaterialValidator materialValidator;

    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;

    public void createMaterial(MaterialDTO materialDTO, Integer idSubject) throws InvalidDataException {
        materialValidator.validateMaterial(materialDTO);
        Material material = materialMapper.mapToNewEntity(materialDTO);

        Subject subjectEntity = subjectDAO.findEntity(idSubject);
        material.setSubject(subjectEntity);

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
     * @param username String
     * @return List of Materials uploaded by the user
     */
    public List<MaterialDTO> materialUploadedByUser(String username) throws UserNotFoundException {
        Optional<User> user = userDAO.findUserByUsername(username);

        if (user.isPresent()) {
            List<Course> ownerForCourses = user.get().getOwnerForCourses();
            Set<Subject> subjectsWhereUserIdOwner = new HashSet<>();
            Set<Material> materials = new HashSet<>();
            ownerForCourses.stream().forEach(course -> subjectsWhereUserIdOwner.addAll(course.getSubjects()));
            subjectsWhereUserIdOwner.stream().forEach(subject -> materials.addAll(subject.getMaterials()));
            return materialMapper.entitiesToDTOs(new ArrayList<>(materials));
        } else {
            throw new UserNotFoundException("User not found!");
        }
    }

    public List<MaterialDTO> getMaterialBySubjectId(Integer subjectId) {
        Subject subject = subjectDAO.findEntity(subjectId);
        return materialMapper.entitiesToDTOs(materialDAO.getMaterialsBySubject(subject));
    }
}
