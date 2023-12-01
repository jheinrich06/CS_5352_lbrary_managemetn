import javax.swing.*;

public class ManagerUpdateLoanRecordView extends JFrame {

    private JTextField txtLoanRecordID = new JTextField(10);
    private JTextField txtLoanRecordDate = new JTextField(10);
    private JButton btnLoadRecord = new JButton("Load Record to see due date");
    private JButton btnUpdateRecord = new JButton("Update Due Date");

    public ManagerUpdateLoanRecordView() {
        this.setTitle("View and Edit Loan Due Dates");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoadRecord);
        panelButton.add(btnUpdateRecord);
        this.getContentPane().add(panelButton);

        JPanel panelLoanRecordID = new JPanel();
        panelLoanRecordID.add(new JLabel("Loan Record ID: "));
        panelLoanRecordID.add(txtLoanRecordID);
        txtLoanRecordID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelLoanRecordID);

        JPanel panelLoanRecordDate = new JPanel();
        panelLoanRecordDate.add(new JLabel("Loan Record Due Date: "));
        panelLoanRecordDate.add(txtLoanRecordDate);
        txtLoanRecordID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelLoanRecordDate);
    }

    public JButton getBtnLoadRecord() {
        return btnLoadRecord;
    }

    public JButton getBtnUpdateRecord() {
        return btnUpdateRecord;
    }

    public JTextField getTxtLoanRecordID() {
        return txtLoanRecordID;
    }

    public JTextField getTxtLoanRecordDate() {
        return txtLoanRecordDate;
    }


}
