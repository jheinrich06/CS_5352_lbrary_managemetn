import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

public class MongoDataAdapter implements DataAccess {

    private MongoClient mongoClient = null;
    private static String conStr = "mongodb+srv://jasonheinrich06:TTU2023@cluster0.xuk8oae.mongodb.net";

    @Override
    public void connect(String str) {
        if (mongoClient != null)
            mongoClient.close();
        mongoClient = new MongoClient(new MongoClientURI(str));
    }


    private String title;
    private String author;
    private String publisher;
    private Double price;
    private String description;
    
    @Override
    public BookModel loadBook(int bookID) {
        this.connect(conStr);
        BookModel book = null;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("books");

            MongoCursor<Document> cursor = collection.find(eq("_id", bookID)).iterator();

            if (cursor.hasNext()) {
                book = new BookModel();
                Document doc = cursor.next();
                book.setBookID(bookID);
                book.setTitle(doc.getString("title"));
                book.setAuthor(doc.getString("author"));
                book.setPublisher(doc.getString("publisher"));
                book.setPrice(doc.getDouble("price"));
                book.setDescription(doc.getString("description"));
                book.setUses(doc.getInteger("uses"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

        return book;
    }

    @Override
    public BookCopiesModel loadBookCopy(int copyID) {
        this.connect(conStr);
        BookCopiesModel bookCopy = null;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("book_copies");

            MongoCursor<Document> cursor = collection.find(eq("_id", copyID) ).iterator();

            if (cursor.hasNext()) {
                bookCopy = new BookCopiesModel();
                Document doc = cursor.next();
                bookCopy.setCopyID(copyID);
                bookCopy.setCopyBookID(doc.getInteger("BookID"));
                bookCopy.setIsLoaned(doc.getBoolean("IsLoaned"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

        return bookCopy;
    }

    @Override
    public BookLoanRecordModel loadBookLoanRecord(int loanRecordID) {
        this.connect(conStr);
        BookLoanRecordModel bookLoanRecord = null;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("loan_records");

            MongoCursor<Document> cursor = collection.find(eq("_id", loanRecordID) ).iterator();

            if (cursor.hasNext()) {
                bookLoanRecord = new BookLoanRecordModel();
                Document doc = cursor.next();
                bookLoanRecord.setLoanRecordID(loanRecordID);
                bookLoanRecord.setLoanBookCopyID(doc.getInteger("BookCopyID"));
                bookLoanRecord.setLoanUserID(doc.getInteger("UserID"));
                bookLoanRecord.setBorrowDate(doc.getString("BorrowDate"));
                bookLoanRecord.setDueDate(doc.getString("DueDate"));
                bookLoanRecord.setReturnDate(doc.getString("ReturnedDate"));
                bookLoanRecord.setIsOpen(doc.getBoolean("IsOpen"));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

        return bookLoanRecord;
    }

    @Override
    public BookLoanRecordModel loadBookLoanRecordFromUserAndCopy(int userID, int bookCopyID, Boolean isOpen) {
        this.connect(conStr);
        BookLoanRecordModel bookLoanRecord = null;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("loan_records");

            MongoCursor<Document> cursor = collection.find(and(eq("BookCopyID", bookCopyID ), eq("UserID", userID), eq("IsOpen", true)  )).iterator();

            if (cursor.hasNext()) {
                bookLoanRecord = new BookLoanRecordModel();
                Document doc = cursor.next();
                bookLoanRecord.setLoanRecordID(doc.getInteger("_id"));
                bookLoanRecord.setLoanBookCopyID(doc.getInteger("BookCopyID"));
                bookLoanRecord.setLoanUserID(doc.getInteger("UserID"));
                bookLoanRecord.setBorrowDate(doc.getString("BorrowDate"));
                bookLoanRecord.setDueDate(doc.getString("DueDate"));
                bookLoanRecord.setReturnDate(doc.getString("ReturnedDate"));
                bookLoanRecord.setIsOpen(doc.getBoolean("IsOpen"));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

        return bookLoanRecord;
    }

    @Override
    public List<BookModel> loadAllBooks() {
        List<BookModel> list = new ArrayList<>();
        BookModel book = null;
        try {
            this.connect(conStr);
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("books");

            MongoCursor<Document> cursor = collection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                book = new BookModel();
                book.setBookID(doc.getInteger("_id"));
                book.setTitle(doc.getString("title"));
                book.setAuthor(doc.getString("author"));
                book.setPublisher(doc.getString("publisher"));
                book.setPrice(doc.getDouble("price"));
                book.setDescription(doc.getString("description"));
                book.setUses(doc.getInteger("uses"));
                list.add(book);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BookModel> loadMatchingBooks(String search) {
        List<BookModel> list = new ArrayList<>();
        BookModel book = null;
        try {
            this.connect(conStr);
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("books");

            MongoCursor<Document> cursor = collection.find(or(regex("title", search ), regex("author", search), regex("publisher", search)  )).sort(descending("uses")).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                book = new BookModel();
                book.setBookID(doc.getInteger("_id"));
                book.setTitle(doc.getString("title"));
                book.setAuthor(doc.getString("author"));
                book.setPublisher(doc.getString("publisher"));
                book.setPrice(doc.getDouble("price"));
                book.setDescription(doc.getString("description"));
                book.setUses(doc.getInteger("uses"));
                list.add(book);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BookCopiesModel> loadAllBookCopies() {
        List<BookCopiesModel> list = new ArrayList<>();
        BookCopiesModel bookCopy = null;
        try {
            this.connect(conStr);
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("book_copies");

            MongoCursor<Document> cursor = collection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                bookCopy = new BookCopiesModel();
                bookCopy.setCopyID(doc.getInteger("_id"));
                bookCopy.setCopyBookID(doc.getInteger("BookID"));
                bookCopy.setIsLoaned(doc.getBoolean("IsLoaned"));
                list.add(bookCopy);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BookLoanRecordModel> loadAllBookLoanRecords() {
        List<BookLoanRecordModel> list = new ArrayList<>();
        BookLoanRecordModel bookLoanRecord = null;
        try {
            this.connect(conStr);
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("loan_records");

            MongoCursor<Document> cursor = collection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                bookLoanRecord = new BookLoanRecordModel();
                bookLoanRecord.setLoanRecordID(doc.getInteger("_id"));
                bookLoanRecord.setLoanBookCopyID(doc.getInteger("BookCopyID"));
                bookLoanRecord.setLoanUserID(doc.getInteger("UserID"));
                bookLoanRecord.setBorrowDate(doc.getString("BorrowDate"));
                bookLoanRecord.setDueDate(doc.getString("DueDate"));
                bookLoanRecord.setReturnDate(doc.getString("ReturnedDate"));
                bookLoanRecord.setIsOpen(doc.getBoolean("IsOpen"));
                list.add(bookLoanRecord);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public UserModel loadUser(int userID) {
        this.connect(conStr);
        UserModel user = null;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("users");

            MongoCursor<Document> cursor = collection.find(eq("_id", userID) ).iterator();

            if (cursor.hasNext()) {
                user = new UserModel();
                Document doc = cursor.next();
                user.setUserID(userID);
                user.setUsername(doc.getString("UserName"));
                user.setPassword(doc.getString("Password"));
                user.setFullName(doc.getString("FullName"));
                user.setIsManager(doc.getBoolean("IsManager"));
                user.setAddress(doc.getString("Address"));
                user.setPhone(doc.getString("Phone"));
                user.setEmail(doc.getString("Email"));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

        return user;
    }

    // for existing book
    @Override
    public void saveBook(BookModel book) {
        int id = book.getBookID();
        Document doc = new Document("_id", id)
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("publisher", book.getPublisher())
                .append("price", book.getPrice())
                .append("description", book.getDescription())
                .append("uses", book.getUses());
        this.connect(conStr);
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("books");

            collection.replaceOne(eq("_id", id), doc, new ReplaceOptions().upsert(true));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

    }

    @Override
    public void updateBookLoanRecord(BookLoanRecordModel record) {
        int id = record.getLoanRecordID();
        Document doc = new Document("_id", id)
                .append("BookCopyID", record.getLoanBookCopyID())
                .append("UserID", record.getLoanUserID())
                .append("BorrowDate", record.getBorrowDate())
                .append("DueDate", record.getDueDate())
                .append("ReturnedDate", record.getReturnDate())
                .append("IsOpen", record.getIsOpen());

        this.connect(conStr);
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("loan_records");

            collection.replaceOne(eq("_id", id), doc, new ReplaceOptions().upsert(true));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();
    }

    @Override
    public void saveBookCopy(BookCopiesModel bookCopy) {
        int id = bookCopy.getCopyID();
        Document doc = new Document("_id", id)
                .append("BookID", bookCopy.getCopyBookID())
                .append("IsLoaned", bookCopy.getIsLoaned());
        this.connect(conStr);
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("book_copies");

            collection.replaceOne(eq("_id", id), doc, new ReplaceOptions().upsert(true));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

    }

    @Override
    public int findMaxBookCopyID() {

        this.connect(conStr);
        int i = 0;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");
            // Access a collection
            MongoCollection<Document> collection = database.getCollection("book_copies");

            long count = collection.countDocuments();
            i = (int)count;


        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();
        return(i);
    }

    @Override
    public int findMaxUserID() {

        this.connect(conStr);
        int i = 0;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");
            // Access a collection
            MongoCollection<Document> collection = database.getCollection("users");

            long count = collection.countDocuments();
            i = (int)count;


        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();
        return(i);
    }

    @Override
    public int findMaxBookID() {

        this.connect(conStr);
        int i = 0;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");
            // Access a collection
            MongoCollection<Document> collection = database.getCollection("books");

            long count = collection.countDocuments();
            i = (int)count;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();
        return(i);
    }

    @Override
    public int findMaxLoanRecord() {

        this.connect(conStr);
        int i = 0;
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");
            // Access a collection
            MongoCollection<Document> collection = database.getCollection("loan_records");

            long count = collection.countDocuments();
            i = (int)count;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();
        return(i);
    }


    @Override
    public void deleteBookCopy(BookCopiesModel bookCopy) {
        int id = bookCopy.getCopyID();
        Document doc = new Document("_id", id)
                .append("BookID", bookCopy.getCopyBookID())
                .append("IsLoaned", bookCopy.getIsLoaned());
        this.connect(conStr);
        try {
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("book_copies");

            collection.deleteOne(eq("_id", id));

            BookLoanRecordModel bookLoanRecord = null;
            try {


                // Access a collection
                MongoCollection<Document> collection2 = database.getCollection("loan_records");

                MongoCursor<Document> cursor = collection2.find(eq("BookCopyID", id) ).iterator();

                if (cursor.hasNext()) {
                    bookLoanRecord = new BookLoanRecordModel();
                    Document doc2 = cursor.next();
                    bookLoanRecord.setLoanRecordID(doc.getInteger("_id"));
                    bookLoanRecord.setLoanBookCopyID(id);
                    bookLoanRecord.setLoanUserID(doc.getInteger("UserID"));
                    bookLoanRecord.setBorrowDate(doc.getString("BorrowDate"));
                    bookLoanRecord.setDueDate(doc.getString("DueDate"));
                    bookLoanRecord.setReturnDate(doc.getString("ReturnedDate"));
                    bookLoanRecord.setIsOpen(false);

                    // need to put this back in a doc to send

                    Document doc3 = new Document("_id", bookLoanRecord.getLoanRecordID())
                            .append("BookCopyID", id)
                            .append("UserID",  bookLoanRecord.getLoanUserID())
                            .append("BorrowDate", bookLoanRecord.getBorrowDate())
                            .append("DueDate", bookLoanRecord.getDueDate())
                            .append("ReturnedDate", bookLoanRecord.getReturnDate())
                            .append("IsOpen", false);


                    UpdateOptions options = new UpdateOptions().upsert(true);
                    collection2.updateOne(eq("_id", bookLoanRecord.getLoanRecordID()), doc3, options);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mongoClient.close();

    }

    @Override
    public UserModel loadUser(String username, String password) {
        UserModel user = null;
        try {

            this.connect(conStr);
            // Connect to the database
            MongoDatabase database = mongoClient.getDatabase("libr_mgmt");

            // Access a collection
            MongoCollection<Document> collection = database.getCollection("users");

            MongoCursor<Document> cursor = collection.find(eq("UserName", username) ).iterator();

            if (cursor.hasNext()) {
                Document doc = cursor.next();

                if (doc.getString("Password").equals(password)) {

                    user = new UserModel();
                    user.setUserID(doc.getInteger("_id"));
                    user.setUsername(doc.getString("UserName"));
                    user.setPassword(doc.getString("Password"));
                    user.setFullName(doc.getString("FullName"));
                    user.setIsManager(doc.getBoolean("IsManager"));
                    user.setAddress(doc.getString("Address"));
                    user.setPhone(doc.getString("Phone"));
                    user.setEmail(doc.getString("Email"));
                }
            }

        } catch (Exception e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return user;
    }
}
