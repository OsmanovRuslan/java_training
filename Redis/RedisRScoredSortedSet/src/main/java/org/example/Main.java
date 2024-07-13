package org.example;

public class Main {
    // Промежуток между регисстрацией пользователей на сайте
    private static final int SLEEP = 1000; // 1 секунда

    public static void main(String[] args) throws InterruptedException {
        RedisStorage redis = new RedisStorage();
        redis.init();
        int minId = 0;
        int maxId = 19;
        for (int i = minId; i <= maxId; i++){
            redis.creatingUser(i);
            Thread.sleep(SLEEP);
        }
        redis.updateScoreSystem();
        for (;;){
            for (int i = minId; i <= maxId; i++){
                if (redis.moveUp()) {
                    redis.updateUsersScore(redis.randomGetNumber(i, maxId), i - 1);
                }
                redis.gettingUser(i);
            }
            System.out.println("LOOP");
            Thread.sleep(SLEEP);
        }
    }
}