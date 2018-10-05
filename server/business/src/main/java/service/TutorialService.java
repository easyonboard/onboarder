package service;

import dao.MaterialRepository;
import dao.TutorialRepository;
import dao.UserRepository;
import dto.TutorialDto;
import dto.MaterialDto;
import dto.mapper.TutorialMapper;
import dto.mapper.MaterialMapper;
import entity.Material;
import entity.Tutorial;
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
    private UserRepository userRepository;

    private TutorialMapper tutorialMapper = TutorialMapper.INSTANCE;
    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;

    public List<TutorialDto> getAllPublicTutorials() {
        return tutorialMapper.entitiesToDTOs(tutorialRepository.findAll());
    }

    public List<TutorialDto> filterByKeyword(String keyword) {
        return tutorialMapper.entitiesToDTOs(tutorialRepository.findByKeywordsContainingIgnoreCase(keyword));
    }

    public TutorialDto addTutorial(TutorialDto tutorialDto, List<String> contactPersonMsgMail) throws EntityNotFoundException, DatabaseException {

        Tutorial tutorial = tutorialMapper.mapToEntity(tutorialDto, new Tutorial());
        tutorial.setContactPersons(getUsersByMsgEmail(contactPersonMsgMail));
        Tutorial tutorialSaved = tutorialRepository.save(tutorial);
        if (tutorialSaved == null) {
            throw new DatabaseException(TUTORIAL_SAVE_EXCEPTION);
        }
        return tutorialMapper.mapToDTO(tutorialSaved);
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


    public TutorialDto getTutorialById(Integer tutorialId) throws EntityNotFoundException {

        Tutorial tutorialEntity = tutorialRepository.findOne(tutorialId);
        if (tutorialEntity == null) {
            throw new EntityNotFoundException(TUTORIAL_NOT_FOUND);
        }
        return tutorialMapper.mapToDTO(tutorialEntity);
    }


    public List<MaterialDto> getAllMaterialsForTutorial(
            Integer idTutorial) throws EntityNotFoundException, NoDataException {

        List<MaterialDto> materialDtos;
        Tutorial tutorialEntity = tutorialRepository.findOne(idTutorial);
        if (tutorialEntity == null) {
            throw new EntityNotFoundException(TUTORIAL_NOT_FOUND);
        }
        List<Material> materials = tutorialEntity.getMaterials();
        if (materials.isEmpty()) {
            throw new NoDataException(noMaterials(tutorialEntity.getTitleTutorial()));
        }
        materialDtos = materials.stream().map(
                tutorial -> materialMapper.mapToDTO(tutorial)).collect(Collectors.toList());

        return materialDtos;
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
}
