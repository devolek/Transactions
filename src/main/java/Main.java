import java.util.*;

public class Main
{
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        Hashtable<String, Account> accounts = new Hashtable<>();
        for (int i = 0; i < 10; i++){
            long money = random.nextInt(50) * 10000;
            String accNumber = Integer.toString(i);
            Account account = new Account(money, accNumber);
            accounts.put(accNumber, account);
        }
        Bank bank = new Bank(accounts);
        long bankBalance = 0;

        for (Map.Entry<String, Account> entry : accounts.entrySet()){
            bankBalance += entry.getValue().getMoney();
        }

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            threads.add(new Thread(() ->
            {
                for (int j = 0; j < 1000; j++)
                {
                    long amount = random.nextInt(55) * 1000;
                    String acc1 = Integer.toString(random.nextInt(10));
                    String acc2 = Integer.toString(random.nextInt(10));
                            if (random.nextBoolean())
                            {
                                long b1 = bank.getBalance(acc1);
                                long b2 = bank.getBalance(acc2);
                                boolean transferSuccessful = bank.transfer(acc1, acc2, amount);
                                long b1n = bank.getBalance(acc1);
                                long b2n = bank.getBalance(acc2);
                                boolean isRight = b1 + b2 == b1n + b2n;
                                if (transferSuccessful)
                                {
                                    System.out.println(j + " Operation is right: " + isRight);
                                }
                            }
                            else
                                {
                                long b1 = bank.getBalance(acc1);
                                long b2 = bank.getBalance(acc2);
                                boolean transferSuccessful = bank.transfer(acc2, acc1, amount);
                                long b1n = bank.getBalance(acc1);
                                long b2n = bank.getBalance(acc2);
                                boolean isRight = b1 + b2 == b1n + b2n;
                                if (transferSuccessful)
                                {
                                    System.out.println(j +" Operation is right: " + isRight);
                                }
                                 }

                    bank.getAccounts().get(acc1).compliedOperation();
                    bank.getAccounts().get(acc2).compliedOperation();
                }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        long bankBalanceNew = 0;
        for (Map.Entry<String, Account> entry : bank.getAccounts().entrySet()){
            bankBalanceNew += entry.getValue().getMoney();
        }
        System.out.println(bankBalance + " - " + bankBalanceNew);
    }
}
