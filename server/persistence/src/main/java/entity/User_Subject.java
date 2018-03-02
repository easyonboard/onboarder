package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( uniqueConstraints=@UniqueConstraint(columnNames={"idSubject", "idUser"}))
public class User_Subject implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idUserSubject;

    @ManyToOne
    @JoinColumn(name="idSubject")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="idUser")
    private User user;



    //interogari in tabela de course pentru a vedea ce subiecte are cursul si apoi ce subiecrte are user ul prin interogare user_subject sau
    //coloana in tabela user_subject cu cursul si interogare directa a tabelei????
    @ManyToOne
    private Course course;



    @Column(columnDefinition="NUMBER(1)")
    private boolean status;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getIdUserSubject() {
        return idUserSubject;
    }

    public void setIdUserSubject(int idUserSubject) {
        this.idUserSubject = idUserSubject;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
