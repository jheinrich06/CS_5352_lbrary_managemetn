public class BookCopiesModel {

    private int copyID;
    private int copyBookID;
    private Boolean isLoaned;

    public int getCopyID() {
        return copyID;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }

    public int getCopyBookID() {
        return copyBookID;
    }

    public void setCopyBookID(int copyBookID) {
        this.copyBookID = copyBookID;
    }

    public Boolean getIsLoaned() {
        return isLoaned;
    }

    public void setIsLoaned(Boolean isLoaned) {
        this.isLoaned = isLoaned;
    }

}
