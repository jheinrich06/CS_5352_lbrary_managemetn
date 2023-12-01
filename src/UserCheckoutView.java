import javax.swing.*;

public class UserCheckoutView extends JFrame {

    private JTextField txtBookCopyID = new JTextField(10);
    private JButton btnCheckOutBook = new JButton("Check Out Book Copy");
    private JButton btnReturnBook = new JButton("Return Book Copy");

    public UserCheckoutView() {
        this.setTitle("Checkout and Return Books");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);

        JPanel panelButton = new JPanel();
        panelButton.add(btnCheckOutBook);
        panelButton.add(btnReturnBook);
        this.getContentPane().add(panelButton);

        JPanel panelBookCopyID = new JPanel();
        panelBookCopyID.add(new JLabel("Book Copy ID: "));
        panelBookCopyID.add(txtBookCopyID);
        txtBookCopyID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelBookCopyID);
    }

    public JButton getBtnCheckOutBook() {
        return btnCheckOutBook;
    }

    public JButton getBtnReturnBook() {
        return btnReturnBook;
    }

    public JTextField getTxtBookCopyID() {
        return txtBookCopyID;
    }

}
