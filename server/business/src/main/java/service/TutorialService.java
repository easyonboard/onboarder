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
import exception.types.DatabaseException;
import exception.types.EntityNotFoundException;
import exception.types.NoDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static exception.Constants.*;

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

    public TutorialDto addTutorial(TutorialDto tutorialDto, List<String> contactPersonMsgMail) throws EntityNotFoundException, DatabaseException {

        Tutorial tutorial = tutorialMapper.mapToEntity(tutorialDto, new Tutorial());
        tutorial.setContactPersons(getUsersByMsgEmail(contactPersonMsgMail));
        if (tutorial.getDraft() == null) {
            tutorial.setDraft(false);
        }
       Tutorial tutorialSaved= tutorialRepository.save(tutorial);
        if(tutorialSaved==null){
            throw new DatabaseException(TUTORIAL_SAVE_EXCEPTION);
        }
        return tutorialMapper.mapToDTO(tutorialSaved);
    }

    private List<User> getUsersByIds(List<Integer> contactPersonsIds) {

        List<User> users = new ArrayList<>();
        for (Integer id : contactPersonsIds) {
            users.add(userRepository.findOne(id));
        }
        return users;
    }

    private List<User> getUsersByMsgEmail(List<String> contactPersonMsgMails) throws EntityNotFoundException {

        List<User> users = new ArrayList<>();
        for (String contactPersonMsgMail : contactPersonMsgMails) {
            Optional<User> optionalUser = userRepository.findByMsgMail(contactPersonMsgMail);
            if (!optionalUser.isPresent()) {
                throw new EntityNotFoundException(userNotFound(contactPersonMsgMail));
            }
            users.add(optionalUser.get());
        }
        return users;
    }

    public TutorialMaterialDTO addTutorialMaterial(TutorialMaterialDTO tutorialMaterialDTO) {

        TutorialMaterial tutorialMaterial = tutorialMaterialMapper.mapToEntity(tutorialMaterialDTO,
                                                                               new TutorialMaterial());
        return tutorialMaterialMapper.mapToDTO(tutorialMaterialDAO.persistEntity(tutorialMaterial));
    }

    public TutorialDto getTutorialById(Integer tutorialId) throws EntityNotFoundException {

        Tutorial tutorialEntity = tutorialRepository.findOne(tutorialId);
        if(tutorialEntity==null){
            throw new EntityNotFoundException(TUTORIAL_NOT_FOUND);
        }
        return tutorialMapper.mapToDTO(tutorialEntity);
    }

    public TutorialMaterialDTO getMaterialById(Integer id)  {

        TutorialMaterial tutorialMaterial = tutorialMaterialDAO.findEntity(id);
        return tutorialMaterialMapper.mapToDTO(tutorialMaterial);
    }

    public List<TutorialMaterialDTO> getAllMaterialsForTutorial(
            Integer idTutorial) throws EntityNotFoundException, NoDataException {

        List<TutorialMaterialDTO> tutorialMaterialDTOS;
        Tutorial tutorialEntity = tutorialRepository.findOne(idTutorial);
        if (tutorialEntity == null) {
            throw new EntityNotFoundException(TUTORIAL_NOT_FOUND);
        }
        List<TutorialMaterial> tutorialMaterials = tutorialEntity.getTutorialMaterials();
        if (tutorialMaterials.isEmpty()) {
            throw new NoDataException(noMaterials(tutorialEntity.getTitleTutorial()));
        }
        tutorialMaterialDTOS = tutorialMaterials.stream().map(
                tutorial -> tutorialMaterialMapper.mapToDTO(tutorial)).collect(Collectors.toList());

        return tutorialMaterialDTOS;
    }

    public List<TutorialDto> deleteTutorial(TutorialDto tutorial) throws EntityNotFoundException {

        Tutorial entity = tutorialRepository.findOne(tutorial.getIdTutorial());
        if (entity == null) {
            throw new EntityNotFoundException(TUTORIAL_NOT_FOUND);
        }
        tutorialRepository.delete(entity);
        return getAllPublicTutorials();
    }

    public TutorialDto updateTutorial(TutorialDto tutorialDto,
                                      List<String> contactPersons) throws EntityNotFoundException {

        Tutorial tutorial = new Tutorial();
        tutorialMapper.mapToEntity(tutorialDto, tutorial);
        tutorial.setContactPersons(getUsersByMsgEmail(contactPersons));
        return tutorialMapper.mapToDTO(tutorialRepository.save(tutorial));
    }

    public List<TutorialDto> allDraftTutorialsForUser(Integer idUser) throws NoDataException {

        List<TutorialDto> draftTutorials = tutorialMapper.entitiesToDTOs(
                tutorialRepository.getAllDraftTurorialsForUser(userRepository.findOne(idUser)));
        if (draftTutorials.isEmpty()) {
            throw new NoDataException(NO_DRAFT);
        } else
            return draftTutorials;
    }

    public List<TutorialDto> allDraftTutorialsForUserFilterByKeyword(Integer idUser, String keyword) throws NoDataException {
        return this.allDraftTutorialsForUser(idUser).stream().filter(tutorial -> tutorial.getKeywords().indexOf(keyword) >= 0).collect(Collectors.toList());
    }
}
