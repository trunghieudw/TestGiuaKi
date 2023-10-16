import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    private String studentCode;
    private String studentName;
    private List<Score> scores;

    public Student() {
    }

    public Student(String studentCode, String studentName, List<Score> scores) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.scores = scores;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
