import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewLoanRecords extends JFrame {

    private DataAccess dataAdapter; // to save and load product information
    private DefaultTableModel items = new DefaultTableModel(); // store information for the table!

    private JTable tblItems = new JTable(items); // null, new String[]{"ProductID", "Product Name", "Price", "Quantity", "Cost"});

    public ViewLoanRecords(DataAccess dataAdapter) {

        List<BookLoanRecordModel> list = dataAdapter.loadAllBookLoanRecords();

        this.setTitle("Loan Record Log");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(400, 600);


        // will need to update for each table
        items.addColumn("Loan Record ID");
        items.addColumn("Book Copy ID");
        items.addColumn("User ID");
        items.addColumn("Borrow Date");
        items.addColumn("Due Date");
        items.addColumn("Returned Date");
        items.addColumn("Still Outstanding");

        Object rowData[] = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).getLoanRecordID();
            rowData[1] = list.get(i).getLoanBookCopyID();
            rowData[2] = list.get(i).getLoanUserID();
            rowData[3] = list.get(i).getBorrowDate();
            rowData[4] = list.get(i).getDueDate();
            rowData[5] = list.get(i).getReturnDate();
            rowData[6] = list.get(i).getIsOpen();
            items.addRow(rowData);
        }

        JPanel panelOrder = new JPanel();
        panelOrder.setPreferredSize(new Dimension(400, 450));
        panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
        tblItems.setBounds(0, 0, 400, 350);
        panelOrder.add(tblItems.getTableHeader());
        panelOrder.add(tblItems);
        tblItems.setFillsViewportHeight(true);
        this.getContentPane().add(panelOrder);

        JPanel panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(400, 100));

        this.getContentPane().add(panelButton);

    }
}
