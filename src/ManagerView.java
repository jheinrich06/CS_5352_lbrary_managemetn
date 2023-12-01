import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerView extends JFrame {

    private JButton btnEditBookCollection = new JButton("Add/Edit Book Catalog");
    private JButton btnAddDeleteBookCopies = new JButton("Add/Remove Book Copies");
    private JButton btnEditDueDate = new JButton("View/Edit Loan Due Dates");
    private JButton btnViewBooksCopiesLoans = new JButton("View books, copies, and loans");
    private JButton btnPopulateDB = new JButton("Populate the database!");
    private JButton btnPopulateLoans = new JButton("Populate loans!");

    public ManagerView() {
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1700, 300);


        btnEditBookCollection.setPreferredSize(new Dimension(320, 50));
        btnAddDeleteBookCopies.setPreferredSize(new Dimension(320, 50));
        btnEditDueDate.setPreferredSize(new Dimension(320, 50));
        btnViewBooksCopiesLoans.setPreferredSize(new Dimension(320, 50));


        btnPopulateDB.setPreferredSize(new Dimension(320, 50));
        btnPopulateLoans.setPreferredSize(new Dimension(320, 50));


        JLabel title = new JLabel("Library Manager System");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        this.getContentPane().add(panelTitle);

        JPanel panelButton = new JPanel();
        panelButton.add(btnEditBookCollection);
        panelButton.add(btnAddDeleteBookCopies);
        panelButton.add(btnEditDueDate);
        panelButton.add(btnViewBooksCopiesLoans);
        panelButton.add(btnPopulateDB);
        panelButton.add(btnPopulateLoans);

        this.getContentPane().add(panelButton);

        btnEditBookCollection.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getManagerUpdateBooksView().setVisible(true);
            }
        });

        btnAddDeleteBookCopies.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getManagerEditBookCopyView().setVisible(true);
            }
        });

        btnEditDueDate.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getManagerUpdateLoanRecordView().setVisible(true);
            }
        });


        btnViewBooksCopiesLoans.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getViewBooks().setVisible(true);
                Application.getInstance().getViewBookCopies().setVisible(true);
                Application.getInstance().getViewLoanRecords().setVisible(true);

            }
        });

        btnPopulateDB.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnPopulateLoans.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public JButton getBtnPopulateDB() {
        return btnPopulateDB;
    }
    public JButton getBtnPopulateLoans() {
        return btnPopulateLoans;
    }

}
