import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagerController implements ActionListener {

    private ManagerEditBookCopyView managerEditBookCopyView;
    private ManagerUpdateBooksView managerUpdateBooksView;
    private ManagerUpdateLoanRecordView managerUpdateLoanRecordView;
    private ManagerView managerView;

    private DataAccess dataAdapter; // to save and load product information

    public ManagerController(DataAccess dataAdapter,
                             ManagerEditBookCopyView managerEditBookCopyView,
                             ManagerUpdateBooksView managerUpdateBooksView,
                             ManagerUpdateLoanRecordView managerUpdateLoanRecordView,
                             ManagerView managerView) {
        this.dataAdapter = dataAdapter;

        this.managerEditBookCopyView = managerEditBookCopyView;
        this.managerUpdateBooksView = managerUpdateBooksView;
        this.managerUpdateLoanRecordView = managerUpdateLoanRecordView;
        this.managerView = managerView;

        managerEditBookCopyView.getBtnAddBookCopy().addActionListener(this);
        managerEditBookCopyView.getBtnDeleteBookCopy().addActionListener(this);
        managerEditBookCopyView.getBtnLoadBookCopy().addActionListener(this);

        managerUpdateBooksView.getBtnLoad().addActionListener(this);
        managerUpdateBooksView.getBtnSave().addActionListener(this);
        managerUpdateBooksView.getBtnAdd().addActionListener(this);

        managerUpdateLoanRecordView.getBtnLoadRecord().addActionListener(this);
        managerUpdateLoanRecordView.getBtnUpdateRecord().addActionListener(this);

        managerView.getBtnPopulateDB().addActionListener(this);
        managerView.getBtnPopulateLoans().addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == managerEditBookCopyView.getBtnAddBookCopy())
            addBookCopy();
        else if (e.getSource() == managerEditBookCopyView.getBtnDeleteBookCopy())
            deleteBookCopy();
        else if (e.getSource() == managerEditBookCopyView.getBtnLoadBookCopy())
            loadBookCopy();
        else if (e.getSource() == managerUpdateBooksView.getBtnLoad())
            loadBook();
        else if (e.getSource() == managerUpdateBooksView.getBtnSave())
            updateBook();
        else if (e.getSource() == managerUpdateBooksView.getBtnAdd())
            addBook();
        else if (e.getSource() == managerUpdateLoanRecordView.getBtnLoadRecord())
            loadLoanRecord();
        else if (e.getSource() == managerUpdateLoanRecordView.getBtnUpdateRecord())
            updateLoanRecord();
        else if (e.getSource() == managerView.getBtnPopulateDB())
            populateDB();
        else if (e.getSource() == managerView.getBtnPopulateLoans())
            populateLoans();
    }

    public void populateDB() {

        BookModel bookPop = new BookModel();
        BookCopiesModel bookCopyPop = new BookCopiesModel();
        int highestBookID = dataAdapter.findMaxBookID();


        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("book_names.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                records.add(Arrays.asList(values));
            }
            for (int i = 1; i < records.toArray().length; i++) {
                bookPop.setBookID(highestBookID + i);
                bookPop.setTitle(records.get(i).get(0));
                bookPop.setAuthor(records.get(i).get(1));
                bookPop.setPublisher(records.get(i).get(2));
                bookPop.setPrice((15 +(int)(Math.random() * ((40 - 15) + 1)))+0.99);
                bookPop.setDescription(records.get(i).get(4));
                dataAdapter.saveBook(bookPop);

                for (int j = 1; j < (2 +(int)(Math.random() * ((5 - 2) + 1))); j++) {
                    int highestBookCopyID = dataAdapter.findMaxBookCopyID();

                    bookCopyPop.setCopyID(highestBookCopyID +1);
                    bookCopyPop.setCopyBookID(highestBookID + i);
                    bookCopyPop.setIsLoaned(false);
                    dataAdapter.saveBookCopy(bookCopyPop);
                }
            }
            //System.out.println(records.get(2).get(2));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JOptionPane.showMessageDialog(null, "Database has been populated!");
    }

    private void populateLoans() {
        Date date2 = new Date();
        final java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date2);
        c.add(Calendar.MONTH, 1);
        final Date futureDate2 = c.getTime();

        String dueDate = new SimpleDateFormat("MM-dd-yyyy").format(futureDate2);
        String checkoutDate = new SimpleDateFormat("MM-dd-yyyy").format(date2);

        int highestBookCopyID = dataAdapter.findMaxBookCopyID();
        int highestUserID = dataAdapter.findMaxUserID();
        BookLoanRecordModel bookLoanRecord = dataAdapter.loadBookLoanRecord(dataAdapter.findMaxLoanRecord());

        for (int i = 1; i < 250; i++) {
            int highestloanRecordID = dataAdapter.findMaxLoanRecord();

            int randomBookCopy = (1 +(int)(Math.random() * ((highestBookCopyID - 1) + 1)));

            bookLoanRecord.setLoanRecordID(highestloanRecordID +1);
            bookLoanRecord.setLoanBookCopyID(randomBookCopy);
            bookLoanRecord.setLoanUserID((1 +(int)(Math.random() * ((highestUserID - 1) + 1))));
            bookLoanRecord.setBorrowDate(checkoutDate);
            bookLoanRecord.setDueDate(dueDate);
            bookLoanRecord.setReturnDate(dueDate);
            bookLoanRecord.setIsOpen(false);
            dataAdapter.updateBookLoanRecord(bookLoanRecord);

            BookCopiesModel bookCopy = dataAdapter.loadBookCopy(randomBookCopy);
            BookModel book = dataAdapter.loadBook(bookCopy.getCopyBookID());

            book.setUses(book.getUses()+1);
            dataAdapter.saveBook(book);

        }
        return;
    }
    private void addBook() {
        // Validate Input
        int bookID;
        try {
            bookID = Integer.parseInt(managerUpdateBooksView.getTxtBookID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book ID! Please provide a valid book ID!");
            return;
        }

        String title = managerUpdateBooksView.getTxtTitle().getText().trim();

        if (title.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid title ! Please provide a non-empty title!");
            return;
        }

        String author = managerUpdateBooksView.getTxtAuthor().getText().trim();

        if (author.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid author ! Please provide a non-empty author!");
            return;
        }

        String publisher = managerUpdateBooksView.getTxtPublisher().getText().trim();

        if (publisher.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid publisher ! Please provide a non-empty publisher!");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(managerUpdateBooksView.getTxtPrice().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price! Please provide a valid price!");
            return;
        }

        String description = managerUpdateBooksView.getTxtDescription().getText().trim();

        if (description.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid description ! Please provide a non-empty description!");
            return;
        }
        // Get highest bookID
        int highestBookID = dataAdapter.findMaxBookID();

        // Post new Book
        BookModel checkRecord = dataAdapter.loadBook(highestBookID);
        if (checkRecord == null) {
            JOptionPane.showMessageDialog(null, "There was an error uploading this book!");
            return;
        }
        // Update Record
        else {
            checkRecord.setBookID(highestBookID + 1);
            checkRecord.setTitle(title);
            checkRecord.setAuthor(author);
            checkRecord.setPublisher(publisher);
            checkRecord.setPrice(price);
            checkRecord.setDescription(description);
            checkRecord.setUses(0);

            dataAdapter.saveBook(checkRecord);
            return;
        }

    }

    private void updateLoanRecord() {
        int recordID;
        try {
            recordID = Integer.parseInt(managerUpdateLoanRecordView.getTxtLoanRecordID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid record ID! Please provide a valid record ID!");
            return;
        }

        String dueDate = managerUpdateLoanRecordView.getTxtLoanRecordDate().getText().trim();

        if (dueDate.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid date ! Please provide a non-empty date!");
            return;
        }

        // pull loan record by ID and check it exists
        BookLoanRecordModel checkRecord = dataAdapter.loadBookLoanRecord(recordID);
        if (checkRecord == null) {
            JOptionPane.showMessageDialog(null, "This Loan Record does not exist in the database!");
            return;
        }
        // Update Record
        else {
            checkRecord.setDueDate(dueDate);
            dataAdapter.updateBookLoanRecord(checkRecord);
            return;
        }
    }

    private void loadLoanRecord() {
        int recordID = 0;
        try {
            recordID = Integer.parseInt(managerUpdateLoanRecordView.getTxtLoanRecordID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid loan record ID! Please provide a valid record ID!");
            return;
        }

        BookLoanRecordModel record = dataAdapter.loadBookLoanRecord(recordID);

        if (record == null) {
            JOptionPane.showMessageDialog(null, "This product ID does not exist in the database!");
            return;
        }

        managerUpdateLoanRecordView.getTxtLoanRecordID().setText(String.valueOf(record.getLoanRecordID()));
        managerUpdateLoanRecordView.getTxtLoanRecordDate().setText(String.valueOf(record.getDueDate()));
        return;
    }

    private void updateBook() {
        int bookID;
        try {
            bookID = Integer.parseInt(managerUpdateBooksView.getTxtBookID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book ID! Please provide a valid book ID!");
            return;
        }

        String title = managerUpdateBooksView.getTxtTitle().getText().trim();

        if (title.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid title ! Please provide a non-empty title!");
            return;
        }

        String author = managerUpdateBooksView.getTxtAuthor().getText().trim();

        if (author.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid author ! Please provide a non-empty author!");
            return;
        }

        String publisher = managerUpdateBooksView.getTxtPublisher().getText().trim();

        if (publisher.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid publisher ! Please provide a non-empty publisher!");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(managerUpdateBooksView.getTxtPrice().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price! Please provide a valid price!");
            return;
        }

        String description = managerUpdateBooksView.getTxtDescription().getText().trim();

        if (description.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid description ! Please provide a non-empty description!");
            return;
        }

        // pull loan record by ID and check it exists
        BookModel checkRecord = dataAdapter.loadBook(bookID);
        if (checkRecord == null) {
            JOptionPane.showMessageDialog(null, "This Book does not exist in the database!");
            return;
        }
        // Update Record
        else {
            checkRecord.setBookID(bookID);
            checkRecord.setTitle(title);
            checkRecord.setAuthor(author);
            checkRecord.setPublisher(publisher);
            checkRecord.setPrice(price);
            checkRecord.setDescription(description);

            dataAdapter.saveBook(checkRecord);
            return;
        }
    }

    private void loadBook() {
        int bookID = 0;
        try {
            bookID = Integer.parseInt(managerUpdateBooksView.getTxtBookID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book ID! Please provide a valid book ID!");
            return;
        }

        BookModel book = dataAdapter.loadBook(bookID);

        if (book == null) {
            JOptionPane.showMessageDialog(null, "This book ID does not exist in the database!");
            return;
        }

        managerUpdateBooksView.getTxtBookID().setText(String.valueOf(book.getBookID()));
        managerUpdateBooksView.getTxtTitle().setText(String.valueOf(book.getTitle()));
        managerUpdateBooksView.getTxtAuthor().setText(String.valueOf(book.getAuthor()));
        managerUpdateBooksView.getTxtPublisher().setText(String.valueOf(book.getPublisher()));
        managerUpdateBooksView.getTxtPrice().setText(String.valueOf(book.getPrice()));
        managerUpdateBooksView.getTxtDescription().setText(String.valueOf(book.getDescription()));

        return;
    }

    private void loadBookCopy() {
        int bookCopyID = 0;
        try {
            bookCopyID = Integer.parseInt(managerEditBookCopyView.getTxtBookCopyID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book copy ID! Please provide a valid book copy ID!");
            return;
        }

        BookCopiesModel bookCopy = dataAdapter.loadBookCopy(bookCopyID);

        if (bookCopy == null) {
            JOptionPane.showMessageDialog(null, "This book ID does not exist in the database!");
            return;
        }
        managerEditBookCopyView.getTxtBookCopyID().setText(String.valueOf(bookCopy.getCopyID()));
        managerEditBookCopyView.getTxtBookReferenceID().setText(String.valueOf(bookCopy.getCopyBookID()));
        return;
    }

    private void addBookCopy() {
        int bookCopyID = 0;
        try {
            bookCopyID = Integer.parseInt(managerEditBookCopyView.getTxtBookCopyID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book copy ID! Please provide a valid book copy ID!");
            return;
        }

        int bookReferenceID = 0;
        try {
            bookReferenceID = Integer.parseInt(managerEditBookCopyView.getTxtBookReferenceID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book ID! Please provide a valid book ID!");
            return;
        }

        int highestBookCopy = dataAdapter.findMaxBookCopyID();
        BookCopiesModel newCopy = dataAdapter.loadBookCopy(highestBookCopy);

        newCopy.setCopyID(highestBookCopy + 1);
        newCopy.setCopyBookID(bookReferenceID);
        newCopy.setIsLoaned(false);

        dataAdapter.saveBookCopy(newCopy);
        return;
    }

    private void deleteBookCopy() {
        int bookCopyID = 0;
        try {
            bookCopyID = Integer.parseInt(managerEditBookCopyView.getTxtBookCopyID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book copy ID! Please provide a valid book copy ID!");
            return;
        }

        int bookReferenceID = 0;
        try {
            bookReferenceID = Integer.parseInt(managerEditBookCopyView.getTxtBookReferenceID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book ID! Please provide a valid book ID!");
            return;
        }


        BookCopiesModel checkExists = dataAdapter.loadBookCopy(bookCopyID);
        if (checkExists == null) {
            JOptionPane.showMessageDialog(null, "This book does not exist in the database!");
            return;
        }
        dataAdapter.deleteBookCopy(checkExists);
        return;
    }
}


