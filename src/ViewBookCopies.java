import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewBookCopies extends JFrame{

    private DefaultTableModel items = new DefaultTableModel(); // store information for the table!

    private DataAccess dataAdapter; // to save and load product information
    private JTable tblItems = new JTable(items); // null, new String[]{"ProductID", "Product Name", "Price", "Quantity", "Cost"});

    public ViewBookCopies(DataAccess dataAdapter) {

        List<BookCopiesModel> list = dataAdapter.loadAllBookCopies();

        this.setTitle("Book Copies Availability");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(400, 600);


        // will need to update for each table
        items.addColumn("Book Copy ID");
        items.addColumn("Book ID");
        items.addColumn("Is Loaned Out");

        Object rowData[] = new Object[3];
        for(int i = 0; i < list.size(); i++)
        {
            rowData[0] = list.get(i).getCopyID();
            rowData[1] = list.get(i).getCopyBookID();
            rowData[2] = list.get(i).getIsLoaned();
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
