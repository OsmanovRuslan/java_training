package org.example;

import org.example.dataclasses.LinkedPurchaseList;
import org.example.dataclasses.LinkedPurchaseListKey;
import org.example.dataclasses.PurchaseList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<PurchaseList> purchaseList = session.createQuery("from PurchaseList", PurchaseList.class).list();
        List<LinkedPurchaseList> linkedPurchaseList = new ArrayList<>();

        for (PurchaseList pl : purchaseList){
            Object[] temp = session.createQuery("select s.studentId, s.courseId \n" +
                    "from Subscription s\n" +
                    "join Course c ON s.courseId=c.id\n" +
                    "join Student st ON st.id=s.studentId\n" +
                    "where st.name=:studentName and c.name=:courseName and s.subscriptionDate=:subscriptionDate", Object[].class)
                    .setParameter("studentName", pl.getStudentName())
                    .setParameter("courseName", pl.getCourseName())
                    .setParameter("subscriptionDate", pl.getSubscriptionDate()).getSingleResult();
            if (temp!=null){
                int studentId = Integer.parseInt(temp[0].toString());
                int courseId = Integer.parseInt(temp[1].toString());
                LinkedPurchaseListKey key = new LinkedPurchaseListKey(studentId, courseId);
                LinkedPurchaseList row = new LinkedPurchaseList();
                row.setId(key);
                linkedPurchaseList.add(row);
            }
        }
        for (LinkedPurchaseList lpl : linkedPurchaseList){
            session.persist(lpl);
        }
        transaction.commit();
        sessionFactory.close();
    }
}