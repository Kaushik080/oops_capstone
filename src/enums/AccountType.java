package enums;

public enum AccountType {
    SAVINGS("Savings account"),
    CURRENT("Current account");

    public final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "displayName='" + displayName + '\'' +
                '}';
    }

    public String getDisplayName() {
        return displayName;
    }
}
