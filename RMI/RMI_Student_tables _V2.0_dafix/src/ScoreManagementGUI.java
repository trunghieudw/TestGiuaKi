import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ScoreManagementGUI {
    private JButton searchButton;
    private JButton addOrUpdateStudentButton;
    private JButton deleteStudentButton;
    private JButton addOrUpdateSubjectButton;
    private JButton deleteSubjectButton;
    private JButton addOrUpdateScoreButton;
    private JButton deleteScoreButton;
    private JFrame frame;
    private JTextField inputField;
    private JTable table;
    private DefaultTableModel tableModel;
    private ScoreService scoreService;
    private JTextArea outputArea;


    public ScoreManagementGUI() {
        initializeGUI();
        connectToRMIService();
    }

    private void initializeGUI() {
        frame = new JFrame("Quản lý Điểm số");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonOutputPanel = new JPanel(new BorderLayout());

        // Tạo một panel mới để chứa nút tìm kiếm và ô nhập liệu
        JPanel searchPanel = new JPanel();
        inputField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchPanel.add(inputField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);


        outputArea = new JTextArea(3, 20);
        outputArea.setEditable(false); // Để không cho người dùng chỉnh sửa nội dung
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        // Thêm outputArea vào mainPanel
        buttonOutputPanel.add(outputScrollPane, BorderLayout.SOUTH);

        // Tạo một bảng để hiển thị kết quả
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Mã Sinh viên");
        tableModel.addColumn("Tên Sinh viên");
        tableModel.addColumn("Mã Môn học");
        tableModel.addColumn("Tên Môn học");
        tableModel.addColumn("Điểm số");
        JScrollPane tableScrollPane = new JScrollPane(table);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
    
        // Tạo các nút chức năng
        JPanel buttonPanel = new JPanel();
        addOrUpdateStudentButton = new JButton("Thêm/Cập nhật Sinh viên");
        deleteStudentButton = new JButton("Xóa Sinh viên");
        addOrUpdateSubjectButton = new JButton("Thêm/Cập nhật Môn học");
        deleteSubjectButton = new JButton("Xóa Môn học");
        addOrUpdateScoreButton = new JButton("Thêm/Cập nhật Điểm số");
        deleteScoreButton = new JButton("Xóa Điểm số");
    
        buttonPanel.add(addOrUpdateStudentButton);
        buttonPanel.add(deleteStudentButton);
        buttonPanel.add(addOrUpdateSubjectButton);
        buttonPanel.add(deleteSubjectButton);
        buttonPanel.add(addOrUpdateScoreButton);
        buttonPanel.add(deleteScoreButton);
    
        // Thêm panel nút chức năng vào dưới của bảng
        buttonOutputPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(buttonOutputPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);
    
        setupActionListeners();
    }
    

    private void connectToRMIService() {
        try {
            // Kết nối tới dịch vụ RMI
            String registryURL = "rmi://localhost:3458/ScoreService";
            scoreService = (ScoreService) Naming.lookup(registryURL);
        } catch (RemoteException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ RemoteException ở đây (ví dụ: hiển thị thông báo lỗi)
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ khác ở đây (ví dụ: hiển thị thông báo lỗi)
        }
    }

    private void setupActionListeners() {
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = inputField.getText();
    
                try {
                    if (!searchText.isEmpty()) {
                        List<Score> scores = new ArrayList<Score>();
    
                        if (searchText.startsWith("S")) {
                            // Nếu searchText bắt đầu bằng "S", lấy thông tin sinh viên
                            Student student = scoreService.getScoresByStudentCode(searchText);
                            if (student != null) {
                                for (Score score : student.getScores()) {
                                    scores.add(score);
                                }
                            }
                        } else if (searchText.startsWith("M")) {
                            // Nếu searchText bắt đầu bằng "M", lấy thông tin môn học
                            Subject subject = scoreService.getScoresBySubjectCode(searchText);
                            if (subject != null) {
                                for (Score score : subject.getScores()) {
                                    scores.add(score);
                                }
                            }
                        }

                        if (!scores.isEmpty()) {
                            clearTable();
                            for (Score score : scores) {
                                String studentName;
                                String subjectName;

                                Student student = scoreService.getScoresByStudentCode(score.getStudentCode());
                                if (student != null) {
                                    studentName = student.getStudentName();
                                } else {
                                    studentName = "Student is not found";
                                }

                                Subject subject = scoreService.getScoresBySubjectCode(score.getSubjectCode());
                                if (subject != null) {
                                    subjectName = subject.getSubjectName();
                                } else {
                                    subjectName = "Subject is not found";
                                }

                                tableModel.addRow(new Object[]{
                                    score.getStudentCode(),
                                    studentName,
                                    score.getSubjectCode(),
                                    subjectName,
                                    score.getScore()
                                });
                            }
                        } else {
                            clearTable();
                            JOptionPane.showMessageDialog(frame, "Không tìm thấy điểm số.");
                        }

                    } else {
                        clearTable();
                        JOptionPane.showMessageDialog(frame, "Vui lòng nhập mã sinh viên hoặc mã môn học.");
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    // Xử lý lỗi RMI tại đây (ví dụ: hiển thị thông báo lỗi)
                }
            }
        });

                // Bắt sự kiện khi nút "Thêm/Cập nhật Điểm số" được nhấn
        addOrUpdateScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentCode = JOptionPane.showInputDialog("Nhập mã sinh viên:");
                String subjectCode = JOptionPane.showInputDialog("Nhập mã môn học:");
                String scoreValue = JOptionPane.showInputDialog("Nhập điểm số:");

                if (studentCode != null && subjectCode != null && scoreValue != null) {
                    Score newScore = new Score(studentCode, subjectCode, Integer.parseInt(scoreValue));
                    try {
                        scoreService.addOrUpdateScore(newScore);
                        outputArea.setText("Thêm hoặc cập nhật điểm số thành công.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        // Xử lý lỗi RMI tại đây (ví dụ: hiển thị thông báo lỗi)
                    }
                } else {
                    outputArea.setText("Hủy thêm/cập nhật điểm số.");
                }
            }
        });

        // Bắt sự kiện khi nút "Xóa Điểm số" được nhấn
        deleteScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentCode = JOptionPane.showInputDialog("Nhập mã sinh viên:");
                String subjectCode = JOptionPane.showInputDialog("Nhập mã môn học:");

                if (studentCode != null && subjectCode != null) {
                    try {
                        scoreService.deleteScore(studentCode, subjectCode);
                        outputArea.setText("Xóa điểm số thành công.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        // Xử lý lỗi RMI tại đây (ví dụ: hiển thị thông báo lỗi)
                    }
                } else {
                    outputArea.setText("Hủy xóa điểm số.");
                }
            }
        });

        // Bắt sự kiện khi nút "Thêm/Cập nhật Sinh viên" được nhấn
        addOrUpdateStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentCode = JOptionPane.showInputDialog("Nhập mã sinh viên:");
                String studentName = JOptionPane.showInputDialog("Nhập tên sinh viên:");

                if (studentCode != null && studentName != null) {
                    Student newStudent = new Student(studentCode, studentName, null);
                    try {
                        scoreService.addOrUpdateStudent(newStudent);
                        outputArea.setText("Thêm hoặc cập nhật sinh viên thành công.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        // Xử lý lỗi RMI tại đây (ví dụ: hiển thị thông báo lỗi)
                    }
                } else {
                    outputArea.setText("Hủy thêm/cập nhật sinh viên.");
                }
            }
        });

        // Bắt sự kiện khi nút "Xóa Sinh viên" được nhấn
        deleteStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentCode = JOptionPane.showInputDialog("Nhập mã sinh viên:");

                if (studentCode != null) {
                    try {
                        scoreService.deleteStudent(studentCode);
                        outputArea.setText("Xóa sinh viên thành công.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        // Xử lý lỗi RMI tại đây (ví dụ: hiển thị thông báo lỗi)
                    }
                } else {
                    outputArea.setText("Hủy xóa sinh viên.");
                }
            }
        });

        // Bắt sự kiện khi nút "Thêm/Cập nhật Môn học" được nhấn
        addOrUpdateSubjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String subjectCode = JOptionPane.showInputDialog("Nhập mã môn học:");
                String subjectName = JOptionPane.showInputDialog("Nhập tên môn học:");

                if (subjectCode != null && subjectName != null) {
                    Subject newSubject = new Subject(subjectCode, subjectName, null);
                    try {
                        scoreService.addOrUpdateSubject(newSubject);
                        outputArea.setText("Thêm hoặc cập nhật môn học thành công.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        // Xử lý lỗi RMI tại đây (ví dụ: hiển thị thông báo lỗi)
                    }
                } else {
                    outputArea.setText("Hủy thêm/cập nhật môn học.");
                }
            }
        });

        // Bắt sự kiện khi nút "Xóa Môn học" được nhấn
        deleteSubjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String subjectCode = JOptionPane.showInputDialog("Nhập mã môn học:");

                if (subjectCode != null) {
                    try {
                        scoreService.deleteSubject(subjectCode);
                        outputArea.setText("Xóa môn học thành công.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        // Xử lý lỗi RMI tại đây (ví dụ: hiển thị thông báo lỗi)
                    }
                } else {
                    outputArea.setText("Hủy xóa môn học.");
                }
            }
        });

    }
    
    

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void displayScores(List<Score> scores) {
        clearTable();
        for (Score score : scores) {
            tableModel.addRow(new Object[]{
                    score.getStudentCode(),
                    score.getStudentName(),
                    score.getSubjectCode(),
                    score.getSubjectName(),
                    score.getScore(),
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScoreManagementGUI();
            }
        });
    }
}