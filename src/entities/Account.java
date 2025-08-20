package entities;

import enums.AccountType;
import exception.InsufficientBalanceException;
import exception.InvalidDepositValueException;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account {
    private String accountNo;
    private String customerID;
    private AccountType type;
    private BigDecimal balance;

    public Account(){

    }

    public Account(String accountNo, String customerID, AccountType type, BigDecimal balance) {
        this.accountNo = accountNo;
        this.customerID = customerID;
        this.type = type;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNo='" + accountNo + '\'' +
                ", customerID='" + customerID + '\'' +
                ", type=" + type +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        Account account = (Account) obj;
        return Objects.equals(accountNo, account.accountNo);
    }

    @Override
    public int hashCode(){
        return Objects.hash(accountNo);
    }

    public void deposit(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidDepositValueException("Deposit value must be greater than zero");
        }
        this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        if(this.balance.compareTo(amount) < 0){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        this.balance.subtract(amount);
    }

    public abstract BigDecimal getInterestRate();
    public abstract BigDecimal getMinimumBalance();

    public BigDecimal calculateInterest(){
        return balance.multiply(getInterestRate()).divide(new BigDecimal(100));
    }
}
