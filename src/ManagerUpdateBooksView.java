import javax.swing.*;

public class ManagerUpdateBooksView extends JFrame {

    private JTextField txtBookID = new JTextField(10);
    private JTextField txtTitle = new JTextField(30);
    private JTextField txtAuthor = new JTextField(10);
    private JTextField txtPublisher = new JTextField(10);
    private JTextField txtPrice = new JTextField(10);
    private JTextField txtDescription = new JTextField(10);

    private JButton btnLoad = new JButton("Load Book");
    private JButton btnSave = new JButton("Save Book");

    private JButton btnAdd = new JButton("Add Book (Ignores ID)");

    public ManagerUpdateBooksView() {
        this.setTitle("Manage Books");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoad);
        panelButton.add(btnSave);
        panelButton.add(btnAdd);
        this.getContentPane().add(panelButton);

        JPanel panelBookID = new JPanel();
        panelBookID.add(new JLabel("Book ID: "));
        panelBookID.add(txtBookID);
        txtBookID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelBookID);

        JPanel panelBookTitle = new JPanel();
        panelBookTitle.add(new JLabel("Title: "));
        panelBookTitle.add(txtTitle);
        this.getContentPane().add(panelBookTitle);

        JPanel panelBookInfo = new JPanel();
        panelBookInfo.add(new JLabel("Author: "));
        panelBookInfo.add(txtAuthor);
        txtAuthor.setHorizontalAlignment(JTextField.RIGHT);

        panelBookInfo.add(new JLabel("Publisher: "));
        panelBookInfo.add(txtPublisher);
        txtPublisher.setHorizontalAlignment(JTextField.RIGHT);

        panelBookInfo.add(new JLabel("Price: "));
        panelBookInfo.add(txtPrice);
        txtPrice.setHorizontalAlignment(JTextField.RIGHT);

        panelBookInfo.add(new JLabel("Description: "));
        panelBookInfo.add(txtDescription);
        txtDescription.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelBookInfo);

    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JTextField getTxtBookID() {
        return txtBookID;
    }

    public JTextField getTxtTitle() {
        return txtTitle;
    }

    public JTextField getTxtAuthor() {
        return txtAuthor;
    }

    public JTextField getTxtPublisher() {
        return txtPublisher;
    }

    public JTextField getTxtPrice() {
        return txtPrice;
    }

    public JTextField getTxtDescription() {
        return txtDescription;
    }

}
