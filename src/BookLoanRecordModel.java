public class BookLoanRecordModel {

    private int loanRecordID;
    private int loanBookCopyID;
    private int loanUserID;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private Boolean isOpen;

    public int getLoanRecordID() {
        return loanRecordID;
    }

    public void setLoanRecordID(int loanRecordID) {
        this.loanRecordID = loanRecordID;
    }

    public int getLoanBookCopyID() {
        return loanBookCopyID;
    }

    public void setLoanBookCopyID(int loanBookCopyID) {
        this.loanBookCopyID = loanBookCopyID;
    }

    public int getLoanUserID() {
        return loanUserID;
    }

    public void setLoanUserID(int loanUserID) {
        this.loanUserID = loanUserID;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

}
