import java.util.concurrent.atomic.AtomicLong;

public class Account implements Comparable<Account>
{
    private long money;
    private String accNumber;
    private volatile boolean accIsLockout = false;
     private boolean canTransfer = true;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
    }

    public synchronized long getMoney(){
        return money;
    }

    public void  depositMoney(long money) {

            this.money += money;

    }
    public void withdrawMoney(long money){

            if (money > this.money) {
                throw new IllegalArgumentException("Transaction failed! Not enough money in the account.");
            }
            this.money -= money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public synchronized boolean isAccIsLockout() {
        return accIsLockout;
    }

    public synchronized void setAccIsLockout(boolean accIsLockout) {
        this.accIsLockout = accIsLockout;
    }
    public synchronized void compliedOperation(){
        canTransfer = true;
        notifyAll();
    }

    public boolean isCanTransfer() {
        return canTransfer;
    }

    public void setCanTransfer(boolean canTransfer) {
        this.canTransfer = canTransfer;
    }

    @Override
    public int compareTo(Account o) {
        return Long.compare(Long.parseLong(getAccNumber()), Long.parseLong(o.getAccNumber()));
    }
}
