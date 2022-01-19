package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BankSystem {

    public static UserListItem head;
    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();

    public static void main(String[] args) {
        try {
            while(true){
                System.out.println("\n1) Create an account");
                System.out.println("2) Add Money");
                System.out.println("3) Withdraw Money ");
                System.out.println("4) Transfer Money");
                System.out.println("5) Print All");
                System.out.println("6) Highest Balance account");
                System.out.println("7) Lowest Balance account");
                System.out.println("8) Delete account");
                System.out.println("9) Numbers of accounts");
                System.out.println("10) Exit");
                System.out.println("---------------");
                System.out.println("Choose an option: ");

                int op=scanner.nextInt();

                switch (op) {
                    case 1 -> createUser();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> transfer();
                    case 5 -> printUsersList();
                    case 6 -> printHighestUser();
                    case 7 -> printLowestUser();
                    case 8 -> deleteUser();
                    case 9 -> System.out.println("The Total Number of users is : " + getUsersCount());
                    case 10 -> {
                        System.out.println("Exiting..");
                        System.exit(1);
                    }
                    default -> System.out.println("Invalid Option");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Please Enter a number between 0 and 10");
        }
    }

    public static void printUsersList() {
        System.out.println("Printing");
        UserListItem tempList = head;
        while (tempList!=null) {
            System.out.printf("Username: %s CardNumber: %s Balance: $%.2f\n------------\n",
                    tempList.username,tempList.cardNumber,tempList.balance);
            tempList = tempList.nextUser;
        }
    }
    public static void createUser() {
        System.out.println("Enter the username");
        String username = scanner.next();
        String cardNumber = String.valueOf(random.nextInt(10000000,99999999))
                .concat(String.valueOf(random.nextInt(10000000,99999999)));
        System.out.println("card number is " + cardNumber);

        // we check if the head is null
        if (head==null) {
            head = new UserListItem(username,cardNumber,0,null);
        }else {
            // if it's not null we iterate to the end of the temp list, and we add the new user
            UserListItem tempList = head;
            while (tempList.nextUser!=null) {
                tempList = tempList.nextUser;
            }
            tempList.nextUser = new UserListItem(username,cardNumber,0,null);
        }
    }
    public static UserListItem checkCardNumber(String searchCardNumber) {
        boolean found = false;
        UserListItem tempList = head;
        while (tempList!=null) {
            if (tempList.cardNumber.equals(searchCardNumber)) {
                found = true;
                break;
            }
            tempList = tempList.nextUser;
        }
        if (found) {
            return tempList;
        }else {
            return null;
        }
    }
    public static void deposit() {
        System.out.println("Please write your card number ");
        String searchCardNumber = scanner.next();
        UserListItem tempList = checkCardNumber(searchCardNumber);

        if (tempList != null) {
            System.out.printf("Welcome %s your balance is $%.2f\n",tempList.username,tempList.balance);
            System.out.println("How much do you want to deposit ?");
            try {
                double deposit = scanner.nextDouble();
                if (deposit<0) {
                    System.out.println("Please enter a valid deposit number");
                }else {
                    tempList.balance+=deposit;
                }
            }catch (InputMismatchException e) {
                System.out.println("Please enter a valid deposit number");
            }
        }else {
            System.out.println("The Card Number doesn't exist");
        }
    }
    public static void withdraw() {
        System.out.println("Please write your card number ");
        String searchCardNumber = scanner.next();
        UserListItem tempList = checkCardNumber(searchCardNumber);

        if (tempList!=null) {
            System.out.printf("Welcome %s your balance is $%.2f\n",tempList.username,tempList.balance);
            System.out.println("How much do you want to withdraw ?");
            try {
                double withdraw = scanner.nextDouble();
                if (withdraw<0 || withdraw > tempList.balance) {
                    System.out.println("Please enter a valid withdraw number");
                }else {
                    tempList.balance-=withdraw;
                }
            }catch (InputMismatchException e) {
                System.out.println("Please enter a valid withdraw number");
            }
        }else {
            System.out.println("The Card Number doesn't exist");
        }
    }
    public static void transfer() {
        System.out.println("Please write your card number you want to transfer from ");
        String cardNumberTF = scanner.next();
        UserListItem tempListTF = checkCardNumber(cardNumberTF);

        if (tempListTF!=null) {
            System.out.printf("Welcome %s your balance is $%.2f\n",tempListTF.username,tempListTF.balance);
            System.out.println("Please Enter the card number you want to transfer to ");
            String cardNumberTT = scanner.next();
            UserListItem tempListTT = checkCardNumber(cardNumberTT);
                if (tempListTT!=null) {
                    System.out.println("How much money do you want to transfer to " + tempListTT.username);
                        try {
                            double transferAmount = scanner.nextDouble();
                            if (transferAmount<0 || transferAmount > tempListTF.balance) {
                                System.out.println("Please enter a valid transfer amount");
                            }else {
                                tempListTF.balance -= transferAmount;
                                tempListTT.balance += transferAmount;

                                //adding the log
                                FileWriter writeTransaction = new FileWriter("src/project/Logs/logs.txt",true);
                                writeTransaction.write(cardNumberTF + " Transferred $" + transferAmount + " To " + cardNumberTT + "\n");
                                writeTransaction.close();
                            }
                        }catch (InputMismatchException e) {
                            System.out.println("Please enter a valid transfer amount");
                        }catch (IOException e) {
                            System.out.println("The File Path is not correct");
                        }
                }else {System.out.println("The Card Number you want to send to doesn't exist");}

        }else {System.out.println("The Card Number doesn't exist");}
    }
    public static void printHighestUser(){

        UserListItem tempList = head;
        UserListItem tempListPrint = head;

        double highestBalance = tempList.balance;
        int highestBalanceIndex = 0;
        int counter = 0;

        while (tempList!=null) {
            if (tempList.balance > highestBalance) {
                highestBalance = tempList.balance;
                highestBalanceIndex = counter;
            }
            counter++;
            tempList = tempList.nextUser;
        }

        //print loop
        for (int count =0; count <= highestBalanceIndex; count++) {
            if (count==highestBalanceIndex) {
                System.out.printf("User with the highest balance: Username: %s CardNumber: %s Balance: $%.2f\n------------\n",
                        tempListPrint.username,tempListPrint.cardNumber,tempListPrint.balance);
                break;
            }
            tempListPrint = tempListPrint.nextUser;
        }

    }
    public static void printLowestUser() {
        UserListItem tempList = head;
        UserListItem tempListPrint = head;

        double lowestBalance = tempList.balance;
        int lowestBalanceIndex = 0;
        int counter = 0;

        while (tempList!=null) {
            if (tempList.balance < lowestBalance) {
                lowestBalance = tempList.balance;
                lowestBalanceIndex = counter;
            }
            counter++;
            tempList = tempList.nextUser;
        }

        //print loop
        for (int count =0; count <= lowestBalanceIndex; count++) {
            if (count==lowestBalanceIndex) {
                System.out.printf("User with the lowest balance: Username: %s CardNumber: %s Balance: $%.2f\n------------\n",
                        tempListPrint.username,tempListPrint.cardNumber,tempListPrint.balance);
                break;
            }
            tempListPrint = tempListPrint.nextUser;
        }
    }
    public static void deleteUser() {
        System.out.println("Please enter the Users card number that you want to delete");
        String deleteCardNumber = scanner.next();
        UserListItem tempList = head;
//        UserListItem tempListPrint = head;

        if (tempList == null) {
            System.out.println("the list is empty we cant delete any users");
        }else {
            int index = search(deleteCardNumber);
            if (index==-1) {
                System.out.println("The card number doesn't exist");
            }else {
                deleteAtIndex(index);
            }
        }
    }
    public static int getUsersCount() {
        UserListItem tempList = head;
        int counter = 0;

        while (tempList!= null) {
            tempList = tempList.nextUser;
            counter++;
        }
        return counter;
    }
    public static int search(String cardNumber) {
        UserListItem tempList = head;
        int count =0;
        int foundIndex = -1;
        while (tempList!=null) {
            if (tempList.cardNumber.equals(cardNumber)) {
                foundIndex = count;
                break;
            }
            tempList = tempList.nextUser;
            count++;
        }
        return foundIndex;
    }
    public static void deleteFront() {

        UserListItem tempList = head;
        head = tempList.nextUser;

    }
    public static void deleteRear() {
        UserListItem tempList = head;
        while (tempList.nextUser.nextUser != null) {
            tempList = tempList.nextUser;
        }
        tempList.nextUser = null;
    }
    public static void deleteAtIndex(int index) {
        UserListItem tempList = head;

        int count = 0;
        if (index == 0) {
            deleteFront();
        } else if(index == getUsersCount()){
            deleteRear();
        }else {
            while (count < index - 1) {
                tempList = tempList.nextUser;
                count++;
            }
            tempList.nextUser = tempList.nextUser.nextUser;
        }
    }
}
