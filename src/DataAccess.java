import java.util.List;

public interface DataAccess {
    void connect(String str);

    BookModel loadBook(int bookID);

    BookCopiesModel loadBookCopy(int copyID);

    BookLoanRecordModel loadBookLoanRecord(int loanRecordID);

    BookLoanRecordModel loadBookLoanRecordFromUserAndCopy(int userID, int bookCopyID, Boolean isOpen);

    List<BookModel> loadAllBooks();

    List<BookModel> loadMatchingBooks(String search);

    List<BookCopiesModel> loadAllBookCopies();

    List<BookLoanRecordModel> loadAllBookLoanRecords();

    //    List<ProductModel> loadAllProducts();
    UserModel loadUser(int userID);

    void saveBook(BookModel book);

    void updateBookLoanRecord(BookLoanRecordModel record);

    void saveBookCopy(BookCopiesModel bookCopy);

    int findMaxBookCopyID();

    int findMaxUserID();

    int findMaxBookID();

    int findMaxLoanRecord();

    void deleteBookCopy(BookCopiesModel bookCopy);

    UserModel loadUser(String username, String password);
}
