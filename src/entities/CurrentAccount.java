package entities;

import enums.AccountType;

import java.math.BigDecimal;

public class CurrentAccount extends Account{

    public static final BigDecimal Interest_rate = new BigDecimal(4);

    public static final BigDecimal Minumin_balance = new BigDecimal(0);

    public CurrentAccount(){
        super();
        setType(AccountType.CURRENT);
    }

    public CurrentAccount(String accountNo, String customerID, BigDecimal balance) {
        super(accountNo, customerID, AccountType.CURRENT, balance);
    }

    @Override
    public BigDecimal getInterestRate() {
        return Interest_rate;
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return Minumin_balance;
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
