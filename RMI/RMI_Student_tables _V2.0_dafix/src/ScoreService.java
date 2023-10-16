import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ScoreService extends Remote {
    Student getScoresByStudentCode(String studentCode) throws RemoteException;

    Subject getScoresBySubjectCode(String subjectCode) throws RemoteException;

    void addOrUpdateScore(Score score) throws RemoteException;

    void deleteScore(String studentCode, String subjectCode) throws RemoteException;

    void addOrUpdateStudent(Student student) throws RemoteException;

    void deleteStudent(String studentCode) throws RemoteException;

    void addOrUpdateSubject(Subject subject) throws RemoteException;

    void deleteSubject(String subjectCode) throws RemoteException;
}
