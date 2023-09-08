import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }



    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Task Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Next Task:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JTextArea taskDescriptionArea = new JTextArea();
        taskDescriptionArea.setEditable(false);
        taskDescriptionArea.setLineWrap(true);
        taskDescriptionArea.setWrapStyleWord(true);
        taskDescriptionArea.setText("Task description goes here...");

        JButton completeButton = new JButton("Mark as Complete");
        completeButton.addActionListener(e -> {
            // Code to mark the task as complete
        });

        panel.add(titleLabel);
        panel.add(taskDescriptionArea);
        panel.add(completeButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
