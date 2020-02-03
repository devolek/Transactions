import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Random random = new Random();
        Hashtable<String, Account> accounts = new Hashtable<>();
        for (int i = 0; i < 100; i++){
            long money = random.nextInt(50) * 10000;
            String accNumber = Integer.toString(i);
            Account account = new Account(money, accNumber);
            accounts.put(accNumber, account);
        }
        Bank bank = new Bank(accounts);

        ArrayList<Thread> threads = new ArrayList<>();
        String acc1 = "15";
        String acc2 = "20";
        System.out.println("Balance 15 and 20:  " + (bank.getBalance(acc1) + bank.getBalance(acc2)));
        for (int i = 0; i < 10; i++)
        {
            threads.add(new Thread(() ->
            {
                for (int j = 0; j < 10; j++) {
                    long amount = random.nextInt(55) * 1000;
                    System.out.println("Balance " + acc1 + " - " + bank.getBalance(acc1) + "; Balance "
                            + acc2 + " - " + bank.getBalance(acc2));
                    if (random.nextBoolean()){
                        bank.transfer(acc1, acc2, amount);
                    }
                    else {
                        bank.transfer(acc2, acc1, amount);
                    }
                    System.out.println("Balance " + acc1 + " - " + bank.getBalance(acc1) + "; Balance "
                            + acc2 + " - " + bank.getBalance(acc2));
                }
            }));
        }
        threads.forEach(Thread::start);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Balance 15 and 20:  " + (bank.getBalance(acc1) + bank.getBalance(acc2)));
    }
}
