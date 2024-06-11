package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
// Получение списка объектов ResultType,
// где есть поля:
// название курса, кол-во приобретенных подписок, номер самого большого месяца когда была сделана покупка.
        List<ResultType> resultTypeList = session.createQuery(
                        "select pl.courseName, " +
                                "count(*) as total_sub, " +
                                "max(month(pl.subscriptionDate)) as total_month " +
                                "from PurchaseList pl " +
                                "where pl.subscriptionDate >= :startDate and pl.subscriptionDate < :endDate " +
                                "group by pl.courseName", ResultType.class)
                .setParameter("startDate", LocalDate.parse("2018-01-01").atStartOfDay())
                .setParameter("endDate", LocalDate.parse("2019-01-01").atStartOfDay())
                .list();
        session.close();

        resultTypeList.forEach(resultType ->
                System.out.printf("Среднее кол-во покупок в месяц на курсе: " +
                        resultType.getCourseName() + " - " + "%.2f" + "\n", ((float) resultType.getTotalSub() / (float) resultType.getTotalMonth()) ));
    }
}
