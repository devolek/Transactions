import java.util.concurrent.atomic.AtomicLong;

public class Account
{
    private volatile AtomicLong money;
    private String accNumber;
    private volatile boolean accIsLockout = false;

    public Account(long money, String accNumber) {
        this.money = new AtomicLong(money);
        this.accNumber = accNumber;
    }

    public long getMoney() {
        return money.get();
    }

    public void addMoney(long money) {
    this.money.addAndGet(money);
    }
    public void withdrawMoney(long money) {
        this.money.set(this.money.get() - money);
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isAccIsLockout() {
        return accIsLockout;
    }

    public void setAccIsLockout(boolean accIsLockout) {
        this.accIsLockout = accIsLockout;
    }
}
