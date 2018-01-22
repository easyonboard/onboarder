package service;

import dao.MaterialDAO;
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

    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;

    public void createMaterial(MaterialDTO materialDTO) {
        Material  material = materialMapper.mapToNewEntity(materialDTO);
        materialDAO.persistEntity(material);
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
