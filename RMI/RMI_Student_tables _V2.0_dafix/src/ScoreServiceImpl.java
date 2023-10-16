import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;


public class ScoreServiceImpl extends UnicastRemoteObject implements ScoreService {
    private Connection connection;

    public ScoreServiceImpl() throws RemoteException {
        super();
        // Khởi tạo kết nối đến cơ sở dữ liệu MySQL ở đây
        String dbURL = "jdbc:mysql://localhost:3306/student_db";
        String username = "root";
        String password = "123456";
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getScoresByStudentCode(String studentCode) throws RemoteException {
        Student student = null;
        try {
            // Truy vấn thông tin điểm của sinh viên dựa trên mã sinh viên
            String query = "SELECT student_name, subject.subject_code, subject_name, subject_score " +
                        "FROM student " +
                        "INNER JOIN score ON student.student_code = score.student_code " +
                        "INNER JOIN subject ON score.subject_code = subject.subject_code " +
                        "WHERE student.student_code = ?";
        
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                student = new Student(studentCode, studentName, new ArrayList<>());

                List<Score> scores = new ArrayList<>();
                do {
                    String subjectCode = resultSet.getString("subject_code");
                    String subjectName = resultSet.getString("subject_name");
                    double score = resultSet.getDouble("subject_score");
                    scores.add(new Score(studentCode, subjectCode, subjectName, score));
                } while (resultSet.next());
                student.setScores(scores);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Subject getScoresBySubjectCode(String subjectCode) throws RemoteException {
        Subject subject = null;
        try {
            // Truy vấn thông tin điểm của các sinh viên trong môn học dựa trên mã môn học
            String query = "SELECT student.student_code, student_name, subject_name, subject_score " +
                        "FROM student " +
                        "INNER JOIN score ON student.student_code = score.student_code " +
                        "INNER JOIN subject ON score.subject_code = subject.subject_code " +
                        "WHERE subject.subject_code = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, subjectCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                String subjectName = resultSet.getString("subject_name");
                subject = new Subject(subjectCode, subjectName, new ArrayList<>());
                List<Score> scores = new ArrayList<>();
                do {
                    String studentCode = resultSet.getString("student_code");
                    String studentName = resultSet.getString("student_name");
                    double score = resultSet.getDouble("subject_score");
                    scores.add(new Score(studentCode, subjectCode, subjectName, score));
                } while (resultSet.next());
                subject.setScores(scores);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    @Override
    public void addOrUpdateStudent(Student student) throws RemoteException {
        String insertQuery = "INSERT INTO student (student_code, student_name) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE student_name = VALUES(student_name)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, student.getStudentCode());
            preparedStatement.setString(2, student.getStudentName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi thêm hoặc cập nhật sinh viên.");
        }
    }

    @Override
    public void addOrUpdateSubject(Subject subject) throws RemoteException {
        String insertQuery = "INSERT INTO subject (subject_code, subject_name) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE subject_name = VALUES(subject_name)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, subject.getSubjectCode());
            preparedStatement.setString(2, subject.getSubjectName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi thêm hoặc cập nhật môn học.");
        }
    }

    @Override
    public void addOrUpdateScore(Score score) throws RemoteException {
        try {
            // Truy vấn để kiểm tra xem bản ghi đã tồn tại hay chưa
            String selectQuery = "SELECT * FROM score WHERE student_code = ? AND subject_code = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, score.getStudentCode());
            selectStatement.setString(2, score.getSubjectCode());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Bản ghi đã tồn tại, cập nhật điểm số
                String updateQuery = "UPDATE score SET subject_score = ? WHERE student_code = ? AND subject_code = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setDouble(1, score.getScore());
                updateStatement.setString(2, score.getStudentCode());
                updateStatement.setString(3, score.getSubjectCode());
                updateStatement.executeUpdate();
            } else {
                // Bản ghi không tồn tại, thêm bản ghi mới
                String insertQuery = "INSERT INTO score (student_code, subject_code, subject_score) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, score.getStudentCode());
                insertStatement.setString(2, score.getSubjectCode());
                insertStatement.setDouble(3, score.getScore());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi thêm hoặc cập nhật điểm số.");
        }
    }

    @Override
    public void deleteStudent(String studentCode) throws RemoteException {
        try {
            // Xóa các bản ghi điểm có mã sinh viên tương ứng
            String deleteScoresQuery = "DELETE FROM score WHERE student_code = ?";
            PreparedStatement deleteScoresStatement = connection.prepareStatement(deleteScoresQuery);
            deleteScoresStatement.setString(1, studentCode);
            deleteScoresStatement.executeUpdate();

            // Xóa bản ghi sinh viên
            String deleteStudentQuery = "DELETE FROM student WHERE student_code = ?";
            PreparedStatement deleteStudentStatement = connection.prepareStatement(deleteStudentQuery);
            deleteStudentStatement.setString(1, studentCode);
            deleteStudentStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi xóa sinh viên và điểm số.");
        }
    }

    @Override
    public void deleteSubject(String subjectCode) throws RemoteException {
        try {
            // Xóa các bản ghi điểm có mã môn học tương ứng
            String deleteScoresQuery = "DELETE FROM score WHERE subject_code = ?";
            PreparedStatement deleteScoresStatement = connection.prepareStatement(deleteScoresQuery);
            deleteScoresStatement.setString(1, subjectCode);
            deleteScoresStatement.executeUpdate();

            // Xóa bản ghi môn học
            String deleteSubjectQuery = "DELETE FROM subject WHERE subject_code = ?";
            PreparedStatement deleteSubjectStatement = connection.prepareStatement(deleteSubjectQuery);
            deleteSubjectStatement.setString(1, subjectCode);
            deleteSubjectStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi xóa môn học và điểm số.");
        }
    }

    @Override
    public void deleteScore(String studentCode, String subjectCode) throws RemoteException {
        String deleteQuery = "DELETE FROM score WHERE student_code = ? AND subject_code = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, studentCode);
            preparedStatement.setString(2, subjectCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Lỗi khi xóa điểm số.");
        }
    }

}
