package org.example;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Account> accountsHashMap = new HashMap<>();
        Account account1 = new Account(60_000, "1");
        Account account2 = new Account(80_000, "2");
        Account account3 = new Account(160_000, "3");
        Account account4 = new Account(180_000, "4");
        Bank bank = new Bank(accountsHashMap);

        new Thread(() ->{
            accountsHashMap.put("1", account1);
            accountsHashMap.put("2", account2);
        }).start();

        new Thread(() ->{
            accountsHashMap.put("3", account3);
            accountsHashMap.put("4", account4);
        }).start();

        new Thread(() ->{
            bank.transfer("1", "2", 60_000);
            bank.transfer("2", "1", 40_000); // Перевод на аккаунт 1, может быть не выполнен, если аккаунт заблокирован после предыдущего перевода.
            synchronized (bank){
                System.out.println("Баланс на аккаунте ".concat(account1.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("1"))));
                System.out.println("Баланс на аккаунте ".concat(account2.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("2"))));
                System.out.println("Баланс на аккаунте ".concat(account3.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("3"))));
                System.out.println("Баланс на аккаунте ".concat(account4.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("4"))));
                System.out.println("Сумма в банке: ".concat(Long.toString(bank.getSumAllAccounts())));
            }

        }).start();

        new Thread(() ->{
            bank.transfer("3", "4", 30_000);
            bank.transfer("4", "1", 20_000); // Перевод на аккаунт 1, может быть не выполнен, если аккаунт заблокирован.
            synchronized (bank){
                System.out.println("Баланс на аккаунте ".concat(account1.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("1"))));
                System.out.println("Баланс на аккаунте ".concat(account2.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("2"))));
                System.out.println("Баланс на аккаунте ".concat(account3.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("3"))));
                System.out.println("Баланс на аккаунте ".concat(account4.getAccNumber()).concat(" : ").concat(Long.toString(bank.getBalance("4"))));
                System.out.println("Сумма в банке: ".concat(Long.toString(bank.getSumAllAccounts())));
            }
        }).start();

    }
}
