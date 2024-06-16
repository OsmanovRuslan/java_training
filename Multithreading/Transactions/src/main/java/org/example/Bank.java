package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {

    private Map<String, Account> accounts;
    private ArrayList<Account> blockedAccounts = new ArrayList<>();
    private final Random random = new Random();

    public Bank(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        Account account1 = new Account();
        Account account2 = new Account();
        for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
            if (accountEntry.getValue().getAccNumber().equals(fromAccountNum)) {
                account1 = accountEntry.getValue();
            } else if (accountEntry.getValue().getAccNumber().equals(toAccountNum)) {
                account2 = accountEntry.getValue();
            }
        }
        synchronized (account1) {
            synchronized (account2) {
                if (account1.getMoney() >= amount && !blockedAccounts.contains(account1) && !blockedAccounts.contains(account2)) {
                    account1.setMoney(account1.getMoney() - amount);
                    account2.setMoney(account2.getMoney() + amount);
                    System.out.println("С аккаунта: ".concat(account1.getAccNumber()).concat(" успешно переведены деньги на аккаунт ").concat(account2.getAccNumber()));
                    if (amount > 50_000) {
                        try {
                            if (isFraud(fromAccountNum, toAccountNum, amount)) {
                                blockAccounts(account1, account2);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("Недостаточно средств на счету или ваш аккаунт был заблокирован");
                }
            }
        }
    }

    public void blockAccounts(Account account1, Account account2) {
        blockedAccounts.add(account1);
        blockedAccounts.add(account2);
        System.out.println("Аккаунты: ".concat(account1.getAccNumber().concat(" и ").concat(account2.getAccNumber()).concat(" были заблокированы!")));
    }

    public long getBalance(String accountNum) {
        Account acc = new Account();
        for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
            if (accountEntry.getValue().getAccNumber().equals(accountNum)) {
                acc = accountEntry.getValue();
            }
        }
        return acc.getMoney();
    }

    public long getSumAllAccounts() {
        AtomicLong summary = new AtomicLong();
        accounts.forEach((k, v) -> summary.addAndGet(v.getMoney()));
        return summary.get();
    }
}
