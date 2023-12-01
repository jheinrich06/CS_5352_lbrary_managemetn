import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class ViewBooks extends JFrame {

    private DefaultTableModel items = new DefaultTableModel(); // store information for the table!

    private JTable tblItems = new JTable(items);

    private DataAccess dataAdapter; // to save and load book information
    public ViewBooks(DataAccess dataAdapter) {

        List<BookModel> list = dataAdapter.loadAllBooks();

        this.setTitle("Book Catalog");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(400, 600);


        // will need to update for each table
        items.addColumn("Book ID");
        items.addColumn("Title");
        items.addColumn("Author");
        items.addColumn("Publisher");
        items.addColumn("Price");
        items.addColumn("Description");
        items.addColumn("Uses");

        Object rowData[] = new Object[7];
        for(int i = 0; i < list.size(); i++)
        {
            rowData[0] = list.get(i).getBookID();
            rowData[1] = list.get(i).getTitle();
            rowData[2] = list.get(i).getAuthor();
            rowData[3] = list.get(i).getPublisher();
            rowData[4] = list.get(i).getPrice();
            rowData[5] = list.get(i).getDescription();
            rowData[6] = list.get(i).getUses();
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
