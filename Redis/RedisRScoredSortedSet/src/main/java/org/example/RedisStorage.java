package org.example;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.client.protocol.ScoredEntry;
import org.redisson.config.Config;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class RedisStorage {

    private RedissonClient redisson;

    private RKeys rKeys;

    private RScoredSortedSet<String> users;

    private Random random = new Random();

    private final static String KEY = "USERS";

    private double getTs() {
        return (double) new Date().getTime() / 1000;
    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException e){
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(e.getMessage());
        }
        rKeys = redisson.getKeys();
        users = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void shutdown() {
        redisson.shutdown();
    }

    void creatingUser(int user_id) {
        users.add(getTs(), String.valueOf(user_id));
    }
    // Получаем пользователя и печатаем текст
    void gettingUser(int number) {
        List<ScoredEntry<String>> user = users.entryRange(number, number).stream().toList();
        System.out.println("На главной странице показываем пользователя " + user.get(0).getValue());
    }
    // Шанс 10% что пользователь купит услугу
    boolean moveUp() {
        return random.nextFloat() <= 0.10f;
    }
    // Получаем рандомного пользователя, который по score между min и max, который купил услугу
    int randomGetNumber(int min, int max) {
        max -= min;
        int number = (int) (Math.random() * ++max) + min;
        List<ScoredEntry<String>> user = users.entryRange(number, number).stream().toList();
        return Integer.parseInt(user.get(0).getValue());
    }

    // Обновляем счета пользователей, которые находятся между тем кто купил услугу и тем, который был последним которого показали на главной странице
    void updateUsersScore(int number, int prev) {
        List<ScoredEntry<String>> usersList = users.entryRange(0, 19).stream().toList();
        for (int i = prev + 1; i <= number; i ++){
            users.add(i + 1, usersList.get(i).getValue());
        }
        users.add(prev + 1, String.valueOf(number));
        System.out.println("Пользователь " + number + " оплатил платную услугу");
    }

    // Обновляем систему счета
    void updateScoreSystem() {
        List<ScoredEntry<String>> usersList = users.entryRange(0, 19).stream().toList();
        for (int i = 0; i <= 19; i++){
            users.add(i, usersList.get(i).getValue());
        }
    }

}