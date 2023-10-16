import java.io.Serializable;

public class Score implements Serializable {
    private String studentCode;
    private String subjectCode;
    private String subjectName;
    private double score;


    public Score( String studentCode, String subjectCode, String subjectName, double score) {
        this.studentCode = studentCode;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.score = score;
    }

    public Score(String studentCode, String subjectCode, double score) {
        this.studentCode = studentCode;
        this.subjectCode = subjectCode;
        this.score = score;
    }

    
    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
