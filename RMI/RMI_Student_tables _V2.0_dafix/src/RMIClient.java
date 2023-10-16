import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            // Look up the remote object by its name
            String registryURL = "rmi://localhost:3457/ScoreService";
            ScoreService scoreService = (ScoreService) Naming.lookup(registryURL);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Menu:");
                System.out.println("1. Tìm kiếm sinh viên hoặc môn học");
                System.out.println("2. Thêm hoặc cập nhật sinh viên");
                System.out.println("3. Thêm hoặc cập nhật môn học");
                System.out.println("4. Thêm hoặc cập nhật điểm số");
                System.out.println("5. Xóa sinh viên");
                System.out.println("6. Xóa môn học");
                System.out.println("7. Xóa điểm số");
                System.out.println("8. Thoát");
                System.out.print("Chọn chức năng (1-8): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Đọc ký tự newline

                switch (choice) {
                    case 1:
                        System.out.print("Nhập mã sinh viên hoặc mã môn học: ");
                        String code = scanner.nextLine();


                        Student student = scoreService.getScoresByStudentCode(code);
                        Subject subject = scoreService.getScoresBySubjectCode(code);

                        if (student != null) {
                            System.out.println("Thông tin sinh viên:");
                            System.out.println("Mã sinh viên: " + student.getStudentCode());
                            System.out.println("Tên sinh viên: " + student.getStudentName());
                            List<Score> scores = student.getScores();
                            if (scores != null && !scores.isEmpty()) {
                                System.out.println("Điểm số:");
                                for (Score score : scores) {
                                    System.out.println("Mã môn học: " + score.getSubjectCode());
                                    System.out.println("Tên môn học: " + score.getSubjectName());
                                    System.out.println("Điểm: " + score.getScore());
                                    System.out.println("---");
                                }
                            } else {
                                System.out.println("Không có điểm số.");
                            }
                        } else if (subject != null) {
                            System.out.println("Thông tin môn học:");
                            System.out.println("Mã môn học: " + subject.getSubjectCode());
                            System.out.println("Tên môn học: " + subject.getSubjectName());
                            List<Score> scores = subject.getScores();
                            if (scores != null && !scores.isEmpty()) {
                                System.out.println("Scores:");
                                for (Score score : scores) {
                                    System.out.println("Student code: " + score.getStudentCode());
                                    // Lấy thông tin sinh viên từ cơ sở dữ liệu dựa trên studentCode
                                    Student students = scoreService.getScoresByStudentCode(score.getStudentCode());
                                    if (students != null) {
                                        System.out.println("Student name: " + students.getStudentName());
                                    } else {
                                        System.out.println("Student is not found");
                                    }
                                    System.out.println("Score: " + score.getScore());
                                    System.out.println("---");
                                }
                            } else {
                                System.out.println("Score is not found");
                            }
                        } else {
                            System.out.println("Không tìm thấy thông tin.");
                        }
                        break;
                    case 2:
                        System.out.print("Nhập mã sinh viên: ");
                        String studentCode = scanner.nextLine();
                        System.out.print("Nhập tên sinh viên: ");
                        String studentName = scanner.nextLine();
                        Student newStudent = new Student(studentCode, studentName, null);
                        scoreService.addOrUpdateStudent(newStudent);
                        System.out.println("Thêm hoặc cập nhật sinh viên thành công.");
                        break;
                    case 3:
                        System.out.print("Nhập mã môn học: ");
                        String subjectCode = scanner.nextLine();
                        System.out.print("Nhập tên môn học: ");
                        String subjectName = scanner.nextLine();
                        Subject newSubject = new Subject(subjectCode, subjectName, null);
                        scoreService.addOrUpdateSubject(newSubject);
                        System.out.println("Thêm hoặc cập nhật môn học thành công.");
                        break;
                    case 4:
                        System.out.print("Nhập mã sinh viên: ");
                        String studentCodeScore = scanner.nextLine();
                        System.out.print("Nhập mã môn học: ");
                        String subjectCodeScore = scanner.nextLine();
                        System.out.print("Nhập điểm số: ");
                        double score = scanner.nextDouble();
                        Score newScore = new Score(studentCodeScore, subjectCodeScore, null, score);
                        scoreService.addOrUpdateScore(newScore);
                        System.out.println("Thêm hoặc cập nhật điểm số thành công.");
                        break;
                    case 5:
                        System.out.print("Nhập mã sinh viên cần xóa: ");
                        String deleteStudentCode = scanner.nextLine();
                        scoreService.deleteStudent(deleteStudentCode);
                        System.out.println("Xóa sinh viên thành công.");
                        break;
                    case 6:
                        System.out.print("Nhập mã môn học cần xóa: ");
                        String deleteSubjectCode = scanner.nextLine();
                        scoreService.deleteSubject(deleteSubjectCode);
                        System.out.println("Xóa môn học thành công.");
                        break;
                    case 7:
                        System.out.print("Nhập mã sinh viên cần xóa điểm số: ");
                        String deleteScoreStudentCode = scanner.nextLine();
                        System.out.print("Nhập mã môn học cần xóa điểm số: ");
                        String deleteScoreSubjectCode = scanner.nextLine();
                        scoreService.deleteScore(deleteScoreStudentCode, deleteScoreSubjectCode);
                        System.out.println("Xóa điểm số thành công.");
                        break;
                    case 8:
                        System.out.println("Kết thúc ứng dụng.");
                        System.exit(0);
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Hãy chọn từ 1 đến 8.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
