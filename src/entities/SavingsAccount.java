package entities;

import enums.AccountType;

import java.math.BigDecimal;

public class SavingsAccount extends Account{

    public static final BigDecimal Interest_rate = new BigDecimal(6);

    public static final BigDecimal Minimum_balance = new BigDecimal(1000);

    public SavingsAccount(){
        super();
        setType(AccountType.SAVINGS);
    }

    public SavingsAccount(String accountNo, String customerID, BigDecimal balance) {
        super(accountNo, customerID, AccountType.SAVINGS, balance);
    }

    @Override
    public BigDecimal getInterestRate() {
        return Interest_rate;
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return Minimum_balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNo='" + getAccountNo() + '\'' +
                ", customerID='" + getCustomerID()+ '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
