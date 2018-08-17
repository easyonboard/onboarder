package dao;

import entity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class UserRepositoryTest {

    private static UserRepository userRepositoryMock;

    @BeforeClass
    public static void setup() {

        userRepositoryMock = mock(UserRepository.class);
    }

    @Test
    public void findByUsername() {

        User user = new User();
        user.setUsername("usernameForTest");
        user.setEmail("user_test@test.app");
        user.setName("User Test");
        user.setMsgMail("UserTest@msg.group");
        user.setIdUser(0);

        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        String username = userRepositoryMock.findByUsername("usernameForTest").get().getUsername();

        assertEquals(user.getUsername(), username );

    }

    @Test(expected = NoSuchElementException.class)
    public void findByUsername_fail() {

        User user = new User();
        user.setUsername("usernameForTest_fail");

        when(userRepositoryMock.findByUsername("another_username")).thenReturn(Optional.of(user));
        userRepositoryMock.findByUsername(user.getUsername()).get().getUsername();

    }

    @Test
    public void findByMsgMail() {

        User user = new User();
        user.setName("User Test");
        user.setMsgMail("UserTestMsgMail4@msg.group");
        user.setIdUser(0);

        when(userRepositoryMock.findByMsgMail(user.getMsgMail())).thenReturn(Optional.of(user));
        String msgMail = userRepositoryMock.findByMsgMail("UserTestMsgMail4@msg.group").get().getMsgMail();

        assertEquals(user.getMsgMail(),msgMail);

    }

    @Test
    public void findByNameContainingIgnoreCase(){
        User user = new User();
        List<User> usersContaining=new ArrayList<>();
        user.setName("User Test");
        usersContaining.add(user);
        when(userRepositoryMock.findByNameContainingIgnoreCase(any(String.class))).thenReturn(usersContaining);
        int resultNumber = userRepositoryMock.findByNameContainingIgnoreCase("use").size();

        assertEquals(1, resultNumber);

    }

}
