import java.util.*;

public class Bank
{
    private Hashtable<String, Account> accounts;
    private final Random random = new Random();

    public Bank(Hashtable<String, Account> accounts) {
        this.accounts = accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount)
    {
        try {
            if (!accounts.containsKey(fromAccountNum) || !accounts.containsKey(toAccountNum) || fromAccountNum.equals(toAccountNum)) {
                throw new IllegalArgumentException("Transaction failed! Wrong account number!");
            }

            Account fromAccount = accounts.get(fromAccountNum);
            Account toAccount = accounts.get(toAccountNum);

            if (amount > fromAccount.getMoney()){
                throw new IllegalArgumentException("Transaction failed! Not enough money in the account.");
            }

            if (fromAccount.isAccIsLockout() || toAccount.isAccIsLockout()) {

                throw new IllegalArgumentException("Transaction failed!"
                        + (fromAccount.isAccIsLockout() ? " Account " + fromAccountNum + " is blocked!" : "")
                        + (toAccount.isAccIsLockout() ? " Account " + toAccountNum + " is blocked!" : ""));
            }

            accounts.get(fromAccountNum).withdrawMoney(amount);
            accounts.get(toAccountNum).addMoney(amount);
            System.out.println(amount + " Transaction complied! " + fromAccountNum + " -> " + toAccountNum);

            if (amount > 50000 && isFraud(fromAccountNum, toAccountNum, amount)){
                accounts.get(fromAccountNum).setAccIsLockout(true);
                accounts.get(toAccountNum).setAccIsLockout(true);
                throw new IllegalArgumentException("Fraud transaction! Accounts are blocked!");
            }

        }
        catch (IllegalArgumentException | InterruptedException ex){
            System.out.println(ex.getMessage());
        }

    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
       return accounts.get(accountNum).getMoney();
    }

    public Hashtable<String, Account> getAccounts() {
        return accounts;
    }
    public void printAccounts() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()){
            System.out.println("Account number: " + entry.getKey() + " Account balance: "
                    + entry.getValue().getMoney() + (entry.getValue().isAccIsLockout() ? " Account is blocked!" : ""));
        }
    }
}
