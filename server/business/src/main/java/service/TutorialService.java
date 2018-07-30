package service;

import dao.TutorialMaterialDAO;
import dao.TutorialRepository;
import dao.UserRepository;
import dto.TutorialDto;
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
    private TutorialRepository tutorialRepository;

    @Autowired
    private TutorialMaterialDAO tutorialMaterialDAO;

    @Autowired
    private UserRepository userRepository;

    private TutorialMapper tutorialMapper = TutorialMapper.INSTANCE;
    private TutorialMaterialMapper tutorialMaterialMapper = TutorialMaterialMapper.INSTANCE;

    public List<TutorialDto> getAllPublicTutorials() {
        return tutorialMapper.entitiesToDTOs(tutorialRepository.findByIsDraft(false));
    }

    public List<TutorialDto> filterByKeyword(String keyword) {
        return tutorialMapper.entitiesToDTOs(tutorialRepository.findByKeywordsContainingIgnoreCase(keyword));
    }

    public TutorialDto addTutorial(TutorialDto tutorialDto, List<String> contactPersonMsgMail) {
        Tutorial tutorial = tutorialMapper.mapToEntity(tutorialDto, new Tutorial());
        tutorial.setContactPersons(getUsersByMsgEmail(contactPersonMsgMail));
        if (tutorial.getDraft()==null) {
            tutorial.setDraft(false);
        }
        return tutorialMapper.mapToDTO(tutorialRepository.save(tutorial));
    }

    private List<User> getUsersByIds(List<Integer> contactPersonsIds) {
        List<User> users = new ArrayList<>();
        for (Integer id : contactPersonsIds) {
            users.add(userRepository.findOne(id));
        }
        return users;
    }

    private List<User> getUsersByMsgEmail(List<String> contactPersonMsgMails) {
        List<User> users = new ArrayList<>();
        for (String contactPersonMsgMail : contactPersonMsgMails) {
            users.add(userRepository.findByMsgMail(contactPersonMsgMail).get());
        }
        return users;
    }

    public TutorialMaterialDTO addTutorialMaterial(TutorialMaterialDTO tutorialMaterialDTO) {
        TutorialMaterial tutorialMaterial = tutorialMaterialMapper.mapToEntity(tutorialMaterialDTO, new TutorialMaterial());
        return tutorialMaterialMapper.mapToDTO(tutorialMaterialDAO.persistEntity(tutorialMaterial));
    }

    public TutorialDto getTutorialById(Integer tutorialId) {
        Tutorial tutorialEntity = tutorialRepository.findOne(tutorialId);
        return tutorialMapper.mapToDTO(tutorialEntity);
    }

    public TutorialMaterialDTO getMaterialById(Integer id) {
        return tutorialMaterialMapper.mapToDTO(tutorialMaterialDAO.findEntity(id));
    }

    public List<TutorialMaterialDTO> getAllMaterialsForTutorial(Integer idTutorial) {
        List<TutorialMaterialDTO> tutorialMaterialDTOS;

        List<TutorialMaterial> tutorialMaterials = tutorialRepository.findOne(idTutorial).getTutorialMaterials();
        tutorialMaterialDTOS = tutorialMaterials.stream().map(tutorial -> tutorialMaterialMapper.mapToDTO(tutorial)).collect(Collectors.toList());

        return tutorialMaterialDTOS;
    }

    public List<TutorialDto> deleteTutorial(TutorialDto tutorial) {

        Tutorial entity = tutorialRepository.findOne(tutorial.getIdTutorial());
        tutorialRepository.delete(entity);
        return getAllPublicTutorials();
    }

    public TutorialDto updateTutorial(TutorialDto tutorialDto, List<String> contactPersons) {
        Tutorial tutorial = new Tutorial();
        tutorialMapper.mapToEntity(tutorialDto, tutorial);
        tutorial.setContactPersons(getUsersByMsgEmail(contactPersons));
        return tutorialMapper.mapToDTO(tutorialRepository.save(tutorial));
    }

    public List<TutorialDto> allDraftTutorialsForUser(Integer idUser) {
        return tutorialMapper.entitiesToDTOs(tutorialRepository.getAllDraftTurorialsForUser(userRepository.findOne(idUser)));
    }
}
