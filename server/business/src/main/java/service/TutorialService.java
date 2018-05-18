package service;

import dao.TutorialDAO;
import dao.TutorialMaterialDAO;
import dao.UserDAO;
import dto.TutorialDTO;
import dto.TutorialMaterialDTO;
import dto.mapper.TutorialMapper;
import dto.mapper.TutorialMaterialMapper;
import entity.Tutorial;
import entity.TutorialMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public List<TutorialDTO> getAllTutorials() {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.allTutorials());
    }

    public List<TutorialDTO> filterByKeyword(String keyword) {
        return tutorialMapper.entitiesToDTOs(tutorialDAO.filterByKeyword(keyword));
    }

    public TutorialDTO addTutorial(TutorialDTO tutorialDTO, List<Integer> ownersIds, List<String> contactPersons) {
//        List<String> usernames = extractContactPersonsUsernames(contactPersons);
        Tutorial tutorial = tutorialMapper.mapToEntity(tutorialDTO, new Tutorial());
//        List<User> users = new ArrayList<>();
//
//        for (int i=0; i<usernames.size(); i++)
//        {
//            User user = userDAO.findUserByUsername(usernames.get(i)).get();
//            users.add(user);
//        }
//
//        tutorial.setContactPersons(users);

//        List<Tutorial> owners = new ArrayList<>();

//        List<User> constantPerson = new ArrayList<>();
//        contactPersonsId.forEach(cpId -> constantPerson.add(userDAO.findEntity(cpId)));

        //tutorial.setOwners(owners);
//        tutorial.setContactPersons(constantPerson);

        return tutorialMapper.mapToDTO(tutorialDAO.persistEntity(tutorial));
    }

    public TutorialMaterialDTO addTutorialMaterial(TutorialMaterialDTO tutorialMaterialDTO) {
        TutorialMaterial tutorialMaterial = tutorialMaterialMapper.mapToEntity(tutorialMaterialDTO, new TutorialMaterial());

//        List<Tutorial> owners = new ArrayList<>();

//        List<User> constantPerson = new ArrayList<>();
//        contactPersonsId.forEach(cpId -> constantPerson.add(userDAO.findEntity(cpId)));

        //tutorial.setOwners(owners);
//        tutorial.setContactPersons(constantPerson);

        return tutorialMaterialMapper.mapToDTO(tutorialMaterialDAO.persistEntity(tutorialMaterial));
    }

    public TutorialDTO getTutorialById(Integer tutorialId) {
        Tutorial tutorialEntity = tutorialDAO.findTutorialById(tutorialId);
        return tutorialMapper.mapToDTO(tutorialEntity);
    }

    private List<String> extractContactPersonsUsernames(List<String> nameUsernamesEmail) {
        List<String> usernames = new ArrayList<>();
        StringTokenizer st;
        String username;

        for (int i = 0; i < nameUsernamesEmail.size(); i++) {
            st = new StringTokenizer(nameUsernamesEmail.get(i), "()");

            if (st.hasMoreTokens()) {
                st.nextToken();
            }
            if (st.hasMoreTokens()) {
                usernames.add(st.nextToken());
            }
        }

        return usernames;
    }

    public TutorialDTO findTutorialById(Integer idTutorial) {
        return tutorialMapper.mapToDTO(tutorialDAO.findTutorialById(idTutorial));
    }

    public TutorialMaterialDTO getMaterialById(Integer id) {
        return tutorialMaterialMapper.mapToDTO(tutorialMaterialDAO.findEntity(id));
    }
}
