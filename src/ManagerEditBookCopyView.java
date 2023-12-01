import javax.swing.*;

public class ManagerEditBookCopyView extends JFrame {

    private JTextField txtBookCopyID = new JTextField(10);
    private JTextField txtBookReferenceID = new JTextField(10);
    private JButton btnLoadBookCopy = new JButton("Load Book Copy");
    private JButton btnDeleteBookCopy = new JButton("Delete Book Copy");
    private JButton btnAddBookCopy = new JButton("Add Copy - ignores Copy ID");

    public ManagerEditBookCopyView() {
        this.setTitle("Add and Remove Book Copies");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoadBookCopy);
        panelButton.add(btnDeleteBookCopy);
        panelButton.add(btnAddBookCopy);
        this.getContentPane().add(panelButton);

        JPanel panelBookCopyID = new JPanel();
        panelBookCopyID.add(new JLabel("Book Copy ID: "));
        panelBookCopyID.add(txtBookCopyID);
        txtBookCopyID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelBookCopyID);

        JPanel panelBookReferenceID = new JPanel();
        panelBookReferenceID.add(new JLabel("Book Reference ID: "));
        panelBookReferenceID.add(txtBookReferenceID);
        txtBookReferenceID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelBookReferenceID);
    }

    public JButton getBtnLoadBookCopy() {
        return btnLoadBookCopy;
    }

    public JButton getBtnDeleteBookCopy() {
        return btnDeleteBookCopy;
    }
    public JButton getBtnAddBookCopy() {
        return btnAddBookCopy;
    }

    public JTextField getTxtBookCopyID() {
        return txtBookCopyID;
    }
    public JTextField getTxtBookReferenceID() {
        return txtBookReferenceID;
    }


}
