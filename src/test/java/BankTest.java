import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class BankTest extends TestCase
{
    Hashtable<String, Account> accounts;
    long bankBalanceNew = 0;
    long bankBalance = 0;
    Bank bank;
    ArrayList<Thread> threads;

    @Override
    protected void setUp() throws Exception {
        Random random = new Random();
        accounts = new Hashtable<>();
        for (int i = 0; i < 10; i++){
            long money = random.nextInt(50) * 10000;
            String accNumber = Integer.toString(i);
            Account account = new Account(money, accNumber);
            accounts.put(accNumber, account);
        }
        bank = new Bank(accounts);

        for (Map.Entry<String, Account> entry : accounts.entrySet()){
            bankBalance += entry.getValue().getMoney();
        }

        threads = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            threads.add(new Thread(() ->
            {
                for (int j = 0; j < 1000; j++)
                {
                    long amount = random.nextInt(52) * 1000;
                    String acc1 = Integer.toString(random.nextInt(10));
                    String acc2 = Integer.toString(random.nextInt(10));
                    if (random.nextBoolean())
                    {
                        bank.transfer(acc1, acc2, amount);
                    }
                    else
                    {
                        bank.transfer(acc2, acc1, amount);

                    }

                }
            }));
        }

    }

    public void testTransfer() throws InterruptedException {
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        for (Map.Entry<String, Account> entry : bank.getAccounts().entrySet()){
            bankBalanceNew += entry.getValue().getMoney();
        }
        long actual = bankBalanceNew;
        long expected = bankBalance;
        assertEquals(expected, actual);
    }
}
