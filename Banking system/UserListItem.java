package project;

public class UserListItem {
    public String username;
    public String cardNumber;
    public double balance;
    public UserListItem nextUser;

    public UserListItem(String username, String cardNumber, double balance, UserListItem nextUser) {
        this.username = username;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.nextUser = nextUser;
    }


}
