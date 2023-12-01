import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class UserController implements ActionListener {

    private UserCheckoutView userCheckoutView;
    private DataAccess dataAdapter; // to save and load product information
    public UserController(UserCheckoutView userCheckoutView, DataAccess dataAdapter) {
        this.dataAdapter = dataAdapter;

        this.userCheckoutView = userCheckoutView;


        userCheckoutView.getBtnCheckOutBook().addActionListener(this);
        userCheckoutView.getBtnReturnBook().addActionListener(this);


    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == userCheckoutView.getBtnCheckOutBook())
            checkOutBookCopy();
        else if (e.getSource() == userCheckoutView.getBtnReturnBook())
            returnBookCopy();
    }

    public void checkOutBookCopy() {

        UserModel user =  Application.getInstance().getCurrentUser();
        int bookCopyID;
        try {
            bookCopyID = Integer.parseInt(userCheckoutView.getTxtBookCopyID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book ID! Please provide a valid book ID!");
            return;
        }

        int highestLoanRecordID = dataAdapter.findMaxLoanRecord();
        BookLoanRecordModel highestLoanRecord = dataAdapter.loadBookLoanRecord(highestLoanRecordID);

        BookCopiesModel checkBookCopy = dataAdapter.loadBookCopy(bookCopyID);
        if (checkBookCopy == null) {
            JOptionPane.showMessageDialog(null, "This Copy does not exist in the database!");
            return;
        }
        else if (checkBookCopy.getIsLoaned()) {
            JOptionPane.showMessageDialog(null, "This Copy is already loaned out. Please try another copy ID!");
            return;
        }
        // Create Record and set book to loaned out
        else {
            Date date2 = new Date();
            final java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(date2);
            c.add(Calendar.MONTH, 1);
           final Date futureDate2 = c.getTime();

            String dueDate = new SimpleDateFormat("MM-dd-yyyy").format(futureDate2);
            String checkoutDate = new SimpleDateFormat("MM-dd-yyyy").format(date2);

            highestLoanRecord.setLoanRecordID(highestLoanRecordID + 1);
            highestLoanRecord.setLoanBookCopyID(bookCopyID);
            highestLoanRecord.setLoanUserID(user.getUserID());
            highestLoanRecord.setBorrowDate(checkoutDate);
            highestLoanRecord.setDueDate(dueDate);
            highestLoanRecord.setReturnDate("");
            highestLoanRecord.setIsOpen(true);
            dataAdapter.updateBookLoanRecord(highestLoanRecord);

            checkBookCopy.setIsLoaned(true);
            dataAdapter.saveBookCopy(checkBookCopy);

            BookModel book = dataAdapter.loadBook(checkBookCopy.getCopyBookID());
            book.setUses(book.getUses()+1);
            dataAdapter.saveBook(book);
            JOptionPane.showMessageDialog(null, "You are all checked out. Enjoy your Book!");
            return;
        }
    }

    public void returnBookCopy() {
        UserModel user =  Application.getInstance().getCurrentUser();
        int bookCopyID;
        try {
            bookCopyID = Integer.parseInt(userCheckoutView.getTxtBookCopyID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid book ID! Please provide a valid book ID!");
            return;
        }
        BookLoanRecordModel loanRecord = dataAdapter.loadBookLoanRecordFromUserAndCopy(user.getUserID(), bookCopyID, true);
        if (loanRecord == null) {
            JOptionPane.showMessageDialog(null, "This Book hasn't been checked out by you!");
            return;
        }

        BookCopiesModel bookCopy = dataAdapter.loadBookCopy(bookCopyID);

        Date date2 = new Date();
        String returnDate = new SimpleDateFormat("MM-dd-yyyy").format(date2);

        // close loan record
        loanRecord.setReturnDate(returnDate);
        loanRecord.setIsOpen(false);
        dataAdapter.updateBookLoanRecord(loanRecord);

        // set book copy to not loaned
        bookCopy.setIsLoaned(false);
        dataAdapter.saveBookCopy(bookCopy);

        JOptionPane.showMessageDialog(null, "Book copy has been returned!");

        return;


    }


}
