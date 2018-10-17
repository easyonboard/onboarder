package service;

import com.google.common.hash.Hashing;
import dao.*;
import dto.CheckListDto;
import dto.LeaveCheckListDto;
import dto.UserDto;
import dto.mapper.CheckListMapper;
import dto.mapper.LeaveCheckListMapper;
import dto.mapper.UserMapper;
import entity.*;
import entity.enums.RoleType;
import exception.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

import javax.persistence.NoResultException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static exception.Constants.*;

/**
 * Service for {@link UserDto}
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;


    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private LeaveCheckListRepository leaveCheckListRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Autowired
    private CheckListService checkListService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TutorialRepository tutorialRepository;


    private UserMapper userMapper = UserMapper.INSTANCE;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    private LeaveCheckListMapper leaveCheckListMapper = LeaveCheckListMapper.INSTANCE;

    public UserDto findUserByUsername(String username) throws EntityNotFoundException {

        Optional<User> entity = userRepository.findByUsername(username);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException(userNotFound(username));
        }
        return userMapper.mapToDTO(entity.get());
    }

    public void addUser(UserDto userDto) throws InvalidDataException {
        userDto.setPassword(encrypt(userDto.getUsername()));
        userValidator.validateUsername(userDto.getUsername());
        userValidator.validateUserData(userDto);
<<<<<<< HEAD
        User mappedUser = userMapper.mapToNewEntity(userDto);
        mappedUser = userRepository.save(mappedUser);
        checkListService.addCheckList(mappedUser);
=======
        Department department = departmentRepository.findByDepartmentName(userDto.getDepartment().getDepartmentName());
        User user = new User();
        userDto.setRole(role);
        userDto.setDepartment(department);

        User mappedUser = userMapper.mapToEntity(userDto, user);
        userRepository.save(mappedUser);

>>>>>>> master
    }


    public void updateUserInfo(UserDto userInfo) {

        User actualUserInfo = userRepository.findOne(userInfo.getIdUser());

        actualUserInfo.setTeam(userInfo.getTeam());
        actualUserInfo.setLocation(userInfo.getLocation());
        actualUserInfo.setFloor(userInfo.getFloor());
        actualUserInfo.setProject(userInfo.getProject());

        actualUserInfo.setDepartment(userInfo.getDepartment());
        actualUserInfo.setStartDate(userInfo.getStartDate());
        userRepository.save(actualUserInfo);
    }

<<<<<<< HEAD
=======

>>>>>>> master
    public String encrypt(String initString) {

        return Hashing.sha256().hashString(initString, StandardCharsets.UTF_8).toString();
    }

    public void updateUser(UserDto userUpdated) throws InvalidDataException, EntityNotFoundException, DatabaseException {

        Optional<User> user = userRepository.findByUsername(userUpdated.getUsername());

        if (user.isPresent()) {
            User entity = userMapper.mapToEntity(userUpdated, user.get());
            userValidator.validateUserData(userMapper.mapToDTO(entity));
            if (userRepository.save(entity) == null) {
                throw new DatabaseException(USER_SAVE_DATABASE_EXCEPTION);
            }
        } else {
            throw new EntityNotFoundException(userNotFound(userUpdated.getUsername()));
        }
    }

    public List<UserDto> getAllUsers() throws EntityNotFoundException {

        List<User> allUsersFromDb = userRepository.findAll();
        if (allUsersFromDb.isEmpty()) {
            throw new EntityNotFoundException(USERS_NOT_FOUND_EXCEPTION);
        }

        return userMapper.entitiesToDTOs(allUsersFromDb);
    }

    public List<UserDto> getAllNewUsers() throws EntityNotFoundException {

        List<User> newUsers = userRepository.findByStartDateAfter(new Date());
        if (newUsers.isEmpty()) {
            throw new EntityNotFoundException(NEW_USERS_NOT_FOUND_EXCEPTION);
        }

        return userMapper.entitiesToDTOs(newUsers);
    }

    public List<UserDto> searchByName(String name) throws EntityNotFoundException {

        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        if (users.isEmpty()) {
            throw new EntityNotFoundException(USERS_MATCHING_STRING_NOT_FOUND_EXCEPTION);
        }

        return userMapper.entitiesToDTOs(users);
    }

    public List<UserDto> getUsersInDepartmentForUser(
            String username) throws EntityNotFoundException, FieldNotFoundException {
        List<User> usersInDepartment = new ArrayList<>();
        Department department = getDepartmentForUser(username);
        if (department != null) {
            usersInDepartment.addAll(userRepository.findByDepartment(department));
            List<Department> allChildDepartments = getAllChildDepartments(department);
            if (allChildDepartments != null) {
                for (Department d : allChildDepartments) {
                    if (d != null) {
                        usersInDepartment.addAll(userRepository.findByDepartment(d));

                    }
                }
            }
        } else {
            throw new EntityNotFoundException(departmentForUserNotFound(username));
        }


        return userMapper.entitiesToDTOs(usersInDepartment);
    }

    private List<Department> getAllChildDepartments(Department department) {

        List<Department> allDepartments = new ArrayList<>(departmentRepository.findByParent(department));

        for (int i = 0; i < allDepartments.size(); i++) {
            allDepartments.addAll(departmentRepository.findByParent(allDepartments.get(i)));
        }
        return allDepartments;
    }

    private Department getDepartmentForUser(String username) throws FieldNotFoundException {

        Optional<User> userEntity = userRepository.findByUsername(username);
        if (!userEntity.isPresent()) {

            throw new FieldNotFoundException(userNotFound(username));
        }

        return userEntity.get().getDepartment();


    }

    public Map getCheckList(UserDto userDto) throws EntityNotFoundException {

        User user = userRepository.findOne(userDto.getIdUser());
        CheckList checkList = checkListRepository.findByUserAccount(user);

        if (checkList == null) {
            throw new EntityNotFoundException(checklistForUserNotFound(userDto.getUsername()));
        }

        Map<String, Boolean> checkListMap = new HashMap();
        Field fields[] = checkList.getClass().getDeclaredFields();
        boolean value = false;
        String attribute;
        for (int i = 2; i < fields.length; i++) {

            attribute = fields[i].getName();
            try {
                fields[i].setAccessible(true);
                value = (boolean) fields[i].get(checkList);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            checkListMap.put(attribute, value);
        }

        if (user.getMateUsername()==null) {
            checkListMap.put("hasBuddyAssigned", false);
            checkList.setHasBuddyAssigned(false);
            checkListRepository.save(checkList);
        }
        return checkListMap;
    }

    public void saveCheckListForUser(String username,
                                     CheckListDto checkList) throws EntityNotFoundException, DatabaseException {

        User userEntity = userRepository.findByUsername(username).get();
        if (userEntity == null) {
            throw new EntityNotFoundException(userNotFound(username));
        }

        checkList.setUserAccount(userMapper.mapToDTO(userEntity));
        CheckList checkListEntity = checkListRepository.findByUserAccount(userEntity);
        if (checkListEntity == null) {
            throw new EntityNotFoundException(checklistForUserNotFound(username));
        }

        checkListMapper.mapToEntity(checkList, checkListEntity);
        if (checkListRepository.save(checkListEntity) == null) {
            throw new DatabaseException(checklistForUserNotSaved(username));
        }
    }

    /**
     * @param username
     * @return
     * @throws EntityNotFoundException delete user and all children from db
     *                                 searches all children and deletes them one by one
     *                                 searches all other users who have the user to be deleted as buddy and sets the
     *                                 buddy field TO NULL
     *                                 <p>
     *                                 <p>
     *                                 TO DO refactoring
     */
    public boolean deleteUser(String username) throws EntityNotFoundException, DatabaseException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();

            if (canUserBeDeleted(userEntity)) {

                CheckList checkListEntity = checkListRepository.findByUserAccount(userEntity);
                if (checkListEntity != null) {
                    checkListRepository.delete(checkListEntity);
                }

                LeaveCheckList leavecheckListEntity = leaveCheckListRepository.findLeaveCheckListByUserAccount(
                        userEntity);
                if (leavecheckListEntity != null) {
                    leaveCheckListRepository.delete(leavecheckListEntity);
                }

                List<Tutorial> tutorialsForUser = tutorialRepository.getTutorialsForUser(userEntity);
                for (Tutorial aTutorialForUser : tutorialsForUser) {
                    aTutorialForUser.getContactPersons().remove(userEntity);
                    tutorialRepository.save(aTutorialForUser);

                }
                List<Event> eventsEnrolled = eventRepository.removeUserFromEnrolledList(userEntity);
                for (Event enrolled : eventsEnrolled) {
                    enrolled.getEnrolledUsers().remove(userEntity);
                    eventRepository.save(enrolled);
                }
                List<Event> contactPerson = eventRepository.findByContactPerson(userEntity);
                for (Event enrolled : contactPerson) {
                    enrolled.getEnrolledUsers().remove(userEntity);
                    eventRepository.save(enrolled);
                }
                userRepository.delete(userEntity);
                return true;
            } else {
                return false;
            }
        } else {
            throw new EntityNotFoundException(userNotFound(username));
        }
    }


    public UserDto getUserInformationForUser(String username) throws EntityNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new EntityNotFoundException(userNotFound(username));
        }

        return userMapper.mapToDTO(user.get());
    }

    public List<String> getAllMsgMails() throws EntityNotFoundException {

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException(USERS_NOT_FOUND_EXCEPTION);
        }
        List<String> usersList = new ArrayList<>();

        users.forEach(user -> usersList.add(user.getMsgMail()));

        return usersList;
    }

    public void updateUserPassword(String username, String password) throws DatabaseException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password != null) {
                user.setPassword(encrypt(password));
            }
            if (userRepository.save(user) == null) {
                throw new DatabaseException(USER_SAVE_DATABASE_EXCEPTION);
            }
        }

    }

    public Boolean getStatusMailForUser(String username) throws EntityNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return checkListRepository.getValueForMailSent(user);
        } else {
            throw new EntityNotFoundException(userNotFound(username));
        }
    }

    public LeaveCheckListDto getLeaveCheckListForUser(String username) throws EntityNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User userEntity = user.get();
            LeaveCheckList leaveCheckList = leaveCheckListRepository.findLeaveCheckListByUserAccount(userEntity);
            if (leaveCheckList == null) {
                leaveCheckList = new LeaveCheckList();
                leaveCheckList.setUserAccount(userEntity);
                Field[] fields = LeaveCheckList.class.getDeclaredFields();

                for (Field field : fields) {    // all fields are set to false, except id and userAccount
                    field.setAccessible(true);
                    try {
                        if (field.getType() == Boolean.class) {
                            field.set(leaveCheckList, false);
                        }
                        return leaveCheckListMapper.mapToDTO(leaveCheckListRepository.save(leaveCheckList));

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                leaveCheckListRepository.save(leaveCheckList);
            }
            return leaveCheckListMapper.mapToDTO(leaveCheckList);
        } else
            throw new EntityNotFoundException(userNotFound(username));

    }

    public LeaveCheckListDto saveLeaveCheckList(LeaveCheckListDto leaveCheckListDto) throws DatabaseException {

        LeaveCheckList leaveCheckList = new LeaveCheckList();
        leaveCheckListMapper.mapToEntity(leaveCheckListDto, leaveCheckList);
        if (leaveCheckListRepository.save(leaveCheckList) == null) {
            throw new DatabaseException("Leave check list for user could not be updated");
        }
        return leaveCheckListMapper.mapToDTO(leaveCheckListRepository.findOne(leaveCheckListDto.getIdCheckList()));
    }

    private boolean canUserBeDeleted(User user) {

        LeaveCheckList leaveCheckList = leaveCheckListRepository.findLeaveCheckListByUserAccount(user);
        if (leaveCheckList != null) {
            Field[] fields = LeaveCheckList.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {    // all fields are set to false, except id and userAccount
                fields[i].setAccessible(true);
                if (fields[i].getType() == boolean.class) {
                    try {
                        if (!fields[i].getBoolean(leaveCheckList)) {
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
            return true;

        }
        return false;
    }

    public boolean checkUnicity(String username, String msgMail) throws DuplicatedDataException {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicatedDataException(USERNAME_DUPLICATED_EXCEPTION);
        }
        if (userRepository.findByMsgMail(msgMail).isPresent()) {
            throw new DuplicatedDataException(USER_MSG_MAIL_DUPLICATED_EXCEPTION);
        }

        return !(userRepository.findByUsername(username).isPresent() || userRepository.findByMsgMail(
                msgMail).isPresent());
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
