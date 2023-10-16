import java.io.Serializable;
import java.util.List;


public class Subject implements Serializable {
    private String subjectCode;
    private String subjectName;
    private List<Score> scores;

    public Subject() {
    }

    public Subject(String subjectCode, String subjectName, List<Score> scores) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.scores = scores;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}