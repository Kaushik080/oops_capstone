import entities.*;
import enums.TransactionType;
import exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Map<String, Customer> customers = new HashMap<>();
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final List<Transaction> transactions = new ArrayList<>();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        System.out.println("Welcome");

        try {
            while (true) {
                showMainMenu();
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }finally {
            scanner.close();
        }
    }

    public static void showMainMenu(){
        System.out.println("\n ===Main Menu=== ");
        System.out.println("1. Register Customer");
        System.out.println("2. Create Account");
        System.out.println("3. Perform Transaction");
        System.out.println("4. View Account details");
        System.out.println("5. View Transaction History");
        System.out.println("6. Exit");

        System.out.println("Enter your choice: ");
        int choice = getInput();

        switch (choice){
            case 1:
                registerCustomer();
                break;
            case 2:
                createAccount();
                break;
            case 3:
                performTransaction();
                break;
            case 4:
                viewAccountDetails();
                break;
            case 5:
                viewTransactionHistory();
                break;
        }
    }

    private static int getInput() {
        while(true){try {
                return Integer.parseInt((scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number: ");
        }
    }
    }

    public static void registerCustomer(){
        System.out.println("\n===Customer Registration===");

        System.out.println("Enter Customer ID");
        String customerID = scanner.nextLine().trim();

        if(customers.containsKey(customerID)){
            System.out.println("Customer Already Exist");
        }

        System.out.println("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.println("Enter Mobile no.: ");
        String phone = scanner.nextLine().trim();

        System.out.println("Enter DOB in yyyy-mm-dd: ");
        String dobStr = scanner.nextLine().trim();

        LocalDate dateOfBirth = null;
        try {
            dateOfBirth = LocalDate.parse(dobStr, dateFormatter);
        } catch (Exception e) {
            System.out.println("Invalid Date Formatter");
        }

        System.out.println("Enter Password: ");
        String password = scanner.nextLine().trim();

        Customer customer = new Customer(customerID, name, phone, email, password, dateOfBirth);
        customers.put(customerID, customer);
        System.out.println("Customer Registered Successfully");

    }

    private static void createAccount(){
        System.out.println("\n===Creating New Account===");
        System.out.println("Enter Customer ID: ");
        String customerID = scanner.nextLine().trim();

        Customer customer = customers.get(customerID);
        if(customer == null){
            System.out.println("Customer Not Found");
        }

        System.out.println("Choose Account Type");
        System.out.println("1. Savings Account(6% Interest Rate and minimum balance of 1000");
        System.out.println("2. Current Account(4% Interest rate and no minimum balance)");
        System.out.println("Select account type: ");

        int typeChoice = getInput();

        Account account = null;

        System.out.println("Enter initial balance: ");
        String balance = scanner.nextLine().trim();

        try{
            BigDecimal initialBalance = new BigDecimal(balance);
            String accountNo = generateAccountNo();

            switch(typeChoice){
                case 1:
                    account = new SavingsAccount(accountNo,customerID,initialBalance);
                    break;
                case 2:
                    account = new CurrentAccount(accountNo,customerID,initialBalance);
                    break;
                default:
                    System.out.println("Invalid account type");
                    return;
            }
            accounts.put(accountNo, account);
            System.out.println(accounts);
            System.out.println("Account Created!!  with Account No." + accountNo);
        }catch(NumberFormatException e){
            System.out.println("Invalid balance amount");
        }
    }

    private static String generateAccountNo() {
        return String.format("%010d",System.currentTimeMillis() % 1000000000L);
    }

    private static void performTransaction(){
        System.out.println("\n===Perform Transaction===");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("Enter transaction choice: ");

        int transactionChoice = getInput();
        switch (transactionChoice){
            case 1:
                performDeposit();
                break;
            case 2:
                performWithdraw();
                break;
            case 3:
                performTransfer();
                break;
            default:
                System.out.println("Invalid transaction type");
        }
    }

    private static void performDeposit(){
        System.out.println("Enter Account Number: ");
        String accountNo = scanner.nextLine().trim();

        Account account = accounts.get(accountNo);
        if(account == null){
            System.out.println("Account not found");
            return;
        }

        System.out.println("Enter Deposit Amount");
        String amountStr = scanner.nextLine().trim();

        try{
            BigDecimal amount = new BigDecimal(amountStr);

            account.deposit(amount);

            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(transactionId, amount, accountNo, LocalDateTime.now(), TransactionType.DEPOSIT);

            transactions.add(transaction);
            System.out.println("Transaction successful, New Balance " + account.getBalance());
        }catch (NumberFormatException e){
            System.out.println("Invalid amount");
        }
    }

    private static void performWithdraw(){
        System.out.println("Enter Account Number: ");
        String accountNo = scanner.nextLine().trim();

        Account account = accounts.get(accountNo);
        if(account == null){
            System.out.println("Account not found");
            return;
        }

        System.out.println("Enter Withdraw Amount");
        String amountStr = scanner.nextLine().trim();

        try{
            BigDecimal amount = new BigDecimal(amountStr);

            account.withdraw(amount);

            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(transactionId, amount, accountNo, LocalDateTime.now(), TransactionType.DEPOSIT);

            transactions.add(transaction);
            System.out.println("Transaction successful, New Balance " + account.getBalance());
        }catch (NumberFormatException e){
            System.out.println("Invalid amount");
        }catch (InsufficientBalanceException e){
            System.out.println("Error " + e.getMessage());
        }

    }

    private static void performTransfer(){
        System.out.println("Enter Source Account Number: ");
        String fromAccountNo = scanner.nextLine().trim();

        Account fromAccount = accounts.get(fromAccountNo);
        if(fromAccount == null){
            System.out.println("Source Account not found");
            return;
        }

        System.out.println("Enter Destination Account Number: ");
        String toAccountNo = scanner.nextLine().trim();

        Account toAccount = accounts.get(toAccountNo);
        if(toAccount == null){
            System.out.println("Account not found");
            return;
        }

        if(fromAccountNo.equals(toAccount)){
            System.out.println("Can't transfer to same account");
        }

        System.out.println("Enter Transaction Amount");
        String amountStr = scanner.nextLine().trim();

        try{
            BigDecimal amount = new BigDecimal(amountStr);
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            String transactionId = generateTransactionId();

            Transaction transaction = new Transaction(transactionId, amount, fromAccountNo, LocalDateTime.now(),TransactionType.TRANSFER, toAccountNo);
        }catch (NumberFormatException e){
            System.out.println("Invalid Amount");
        }catch (InsufficientBalanceException e){
            System.out.println("error " + e.getMessage());
        }
    }

    private static void viewAccountDetails(){
        System.out.println("\n===Account Details===");
        System.out.println("Enter Account Number: ");
        String accountNo = scanner.nextLine().trim();

        Account account = accounts.get(accountNo);
        if(account == null){
            System.out.println("Account not found");
            return;
        }

        System.out.println(account.toString());
        if(account instanceof SavingsAccount){
            System.out.println("Account Type: Savings Account");
        }else{
            System.out.println("Account Typer: Current Account");
        }
    }

    private static void viewTransactionHistory(){
        System.out.println("\n===Transaction History===");
        System.out.println("Enter Account Number: ");
        String accountNo = scanner.nextLine().trim();

        Account account = accounts.get(accountNo);
        if(account == null){
            System.out.println("Account not found");
            return;
        }

        List<Transaction> accountTransactions = transactions.stream()
                .filter(t -> t.getAccountNo().equals(accountNo))
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();

        if(accountTransactions.isEmpty()){
            System.out.println("No transactions found");
            return;
        }
        for(Transaction transaction: accountTransactions){
            System.out.println(transaction.toString());     //try transaction instead of .toString
        }

        Map<TransactionType, Long> transactionSummary = accountTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType,Collectors.counting()));
        System.out.println("===Transaction Summary===");
        System.out.println(transactionSummary);
    }

    private static String generateTransactionId(){
        return "HDFC_TXN" + System.currentTimeMillis();
    }


}