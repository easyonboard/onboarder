package service;

import dao.TutorialDAO;
import dao.TutorialMaterialDAO;
import dao.UserDAO;
import dto.ContactPersonDto;
import dto.TutorialDTO;
import dto.TutorialMaterialDTO;
import dto.mapper.TutorialMapper;
import dto.mapper.TutorialMaterialMapper;
import entity.Tutorial;
import entity.TutorialMaterial;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutorialService {

    @Autowired
    private TutorialDAO tutorialDAO;

    @Autowired
    private TutorialMaterialDAO tutorialMaterialDAO;

    @Autowired
    private UserDAO userDAO;

    private TutorialMapper tutorialMapper = TutorialMapper.INSTANCE;
    private TutorialMaterialMapper tutorialMaterialMapper = TutorialMaterialMapper.INSTANCE;

    public List<TutorialDTO> getAllPublicTutorials() {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.allPublicTutorials());
    }

    public List<TutorialDTO> filterByKeyword(String keyword) {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.filterByKeyword(keyword));
    }

    public TutorialDTO addTutorial(TutorialDTO tutorialDTO, List<ContactPersonDto> contactPersons) {
        Tutorial tutorial = tutorialMapper.mapToEntity(tutorialDTO, new Tutorial());
        tutorial.setContactPersons(getUsersByUsername(contactPersons));
        if (tutorial.getDraft()==null) {
            tutorial.setDraft(false);
        }
        return tutorialMapper.mapToDTO(tutorialDAO.persistEntity(tutorial));
    }

    private List<User> getUsersByIds(List<Integer> contactPersonsIds) {
        List<User> users = new ArrayList<>();
        for (Integer id : contactPersonsIds) {
            users.add(userDAO.findEntity(id));
        }
        return users;
    }

    private List<User> getUsersByUsername(List<ContactPersonDto> contactPersons) {
        List<User> users = new ArrayList<>();
        for (ContactPersonDto contactPerson : contactPersons) {
            users.add(userDAO.findUserByUsername(contactPerson.getUsername()).get());
        }
        return users;
    }

    public TutorialMaterialDTO addTutorialMaterial(TutorialMaterialDTO tutorialMaterialDTO) {
        TutorialMaterial tutorialMaterial = tutorialMaterialMapper.mapToEntity(tutorialMaterialDTO, new TutorialMaterial());
        return tutorialMaterialMapper.mapToDTO(tutorialMaterialDAO.persistEntity(tutorialMaterial));
    }

    public TutorialDTO getTutorialById(Integer tutorialId) {
        Tutorial tutorialEntity = tutorialDAO.findTutorialById(tutorialId);
        return tutorialMapper.mapToDTO(tutorialEntity);
    }


    public TutorialDTO findTutorialById(Integer idTutorial) {
        return tutorialMapper.mapToDTO(tutorialDAO.findTutorialById(idTutorial));
    }

    public TutorialMaterialDTO getMaterialById(Integer id) {
        return tutorialMaterialMapper.mapToDTO(tutorialMaterialDAO.findEntity(id));
    }

    public List<TutorialMaterialDTO> getAllMaterialsForTutorial(Integer idTutorial) {
        List<TutorialMaterialDTO> tutorialMaterialDTOS;

        List<TutorialMaterial> tutorialMaterials = tutorialDAO.findTutorialById(idTutorial).getTutorialMaterials();
        tutorialMaterialDTOS = tutorialMaterials.stream().map(tutorial -> tutorialMaterialMapper.mapToDTO(tutorial)).collect(Collectors.toList());

        return tutorialMaterialDTOS;
    }

    public List<TutorialDTO> deleteTutorial(TutorialDTO tutorial) {

        Tutorial entity = tutorialDAO.findEntity(tutorial.getIdTutorial());
        tutorialDAO.deleteEntity(entity);
        return getAllPublicTutorials();
    }

    public TutorialDTO updateTutorial(TutorialDTO tutorialDTO, List<Integer> contactPersons) {
        Tutorial tutorial = new Tutorial();
        tutorialMapper.mapToEntity(tutorialDTO, tutorial);
        tutorial.setContactPersons(getUsersByIds(contactPersons));
        return tutorialMapper.mapToDTO(tutorialDAO.update(tutorial));
    }

    public List<TutorialDTO> allDraftTutorialsForUser(Integer idUser) {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.getAllDraftTutorialsForUser(userDAO.findEntity(idUser)));
    }
}
