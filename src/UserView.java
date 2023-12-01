import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JFrame {

    private JButton btnCheckOut = new JButton("Check out/Return Books");
    private JButton btnViewBooks = new JButton("View books");

    public UserView() {
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 300);

        btnViewBooks.setPreferredSize(new Dimension(220, 50));
        btnCheckOut.setPreferredSize(new Dimension(220, 50));


        JLabel title = new JLabel("Library User System");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        this.getContentPane().add(panelTitle);

        JPanel panelButton = new JPanel();
        panelButton.add(btnCheckOut);
        panelButton.add(btnViewBooks);

        this.getContentPane().add(panelButton);

        btnCheckOut.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {

                Application.getInstance().getUserCheckoutView().setVisible(true);
            }
        });


        btnViewBooks.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getViewBooks().setVisible(true);
                Application.getInstance().getViewBookCopies().setVisible(true);
            }
        });
    }


}
