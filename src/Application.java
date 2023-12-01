//import java.sql.*;

public class Application {

    private static Application instance;   // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
    // Main components of this application

    //private Connection connection;

    //public Connection getConnection() {
        //return connection;
   // }

    private DataAccess dataAdapter;

    private UserModel currentUser = null;

    public UserModel getCurrentUser() { return currentUser; }

    public void setCurrentUser(UserModel user) {
        this.currentUser = user;
    }
    private UserView userview = new UserView();
    public UserView getUserView() {
        return userview;
    }

    private ManagerView managerView = new ManagerView();
    public ManagerView getManagerView() {
        return managerView;
    }


    private UserCheckoutView userCheckoutView = new UserCheckoutView();
    public UserCheckoutView getUserCheckoutView() {
        return userCheckoutView;
    }

    private ManagerEditBookCopyView managerEditBookCopyView = new ManagerEditBookCopyView();
    public ManagerEditBookCopyView getManagerEditBookCopyView() {
        return managerEditBookCopyView;
    }

    private ManagerUpdateBooksView managerUpdateBooksView = new ManagerUpdateBooksView();
    public ManagerUpdateBooksView getManagerUpdateBooksView() {
        return managerUpdateBooksView;
    }

    private  ManagerUpdateLoanRecordView managerUpdateLoanRecordView = new ManagerUpdateLoanRecordView();
    public ManagerUpdateLoanRecordView getManagerUpdateLoanRecordView() {return managerUpdateLoanRecordView;}

    private ViewBookCopies viewBookCopies;
    public ViewBookCopies getViewBookCopies() { return viewBookCopies;}

    private ViewBooks viewBooks;
    public ViewBooks getViewBooks() { return viewBooks;}

    private ViewLoanRecords viewLoanRecords;
    public ViewLoanRecords getViewLoanRecords() { return viewLoanRecords;}

    public LoginScreen loginScreen = new LoginScreen();

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }


    public LoginController loginController; // = new LoginController(loginScreen, dataAdapter);


    private ManagerController managerController;

    public ManagerController getManagerController() {
        return managerController;
    }

    private UserController userController;

    public UserController getUserController() {
        return userController;
    }


    public DataAccess getDataAdapter() {
        return dataAdapter;
    }



    private Application() {
        // create Mongo database connection here!
        try {

            dataAdapter = new MongoDataAdapter();

        }
        catch (Exception ex) {
            System.out.println("Problem connecting to MongoDB is not installed. System exits with error!");
            ex.printStackTrace();
            System.exit(1);
        }

        // Create data adapter here!

        loginController = new LoginController(loginScreen, dataAdapter);

        userController = new UserController(userCheckoutView, dataAdapter);

        managerController = new ManagerController(dataAdapter, managerEditBookCopyView, managerUpdateBooksView, managerUpdateLoanRecordView, managerView);

        viewBooks = new ViewBooks(dataAdapter);
        viewBookCopies = new ViewBookCopies(dataAdapter);
        viewLoanRecords = new ViewLoanRecords(dataAdapter);

    }


    public static void main(String[] args) {
        Application.getInstance().getLoginScreen().setVisible(true);
    }
}
