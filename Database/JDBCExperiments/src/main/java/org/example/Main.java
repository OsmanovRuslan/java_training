package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        String hql = "From " + Course.class.getSimpleName() + " Where price > 120000";
        List<Course> courseList = session.createQuery(hql).getResultList();
        System.out.println(courseList.size());
        courseList.forEach(course -> System.out.println(course.getName()));

// Hibernate Query Builder
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<Course> query =  builder.createQuery(Course.class);
//        Root<Course> root = query.from(Course.class);
//        query.select(root).where(builder.greaterThan(root.get("price"), 100_000)).orderBy(builder.desc(root.get("price")));
//        List<Course> courseList = session.createQuery(query).setMaxResults(5).getResultList();
//        courseList.forEach(course -> System.out.println(course.getName() + " - " + course.getPrice()));

//        query.select(root);

//        List<Course> courseList = session.createQuery(query).getResultList();
//        courseList.forEach(course -> System.out.println(course.getName())); // System.out.println("course.getName() + " - " + course.getTeacher().getName()"); показывает как работает ленивая загрузка, тоесть при каждом запросе курса только тогда происходит запрос к каждому учителю, а не срауз ко всем учителям, то fetch = FetchType.EAGER.
// End



//        Transaction transaction = session.beginTransaction();
//        Course course = session.get(Course.class, 1);
//        List<Student> students = course.getStudents();
//        students.forEach(student -> System.out.println(student.getName()));
//        System.out.println(course.getStudents().size());

        // UPDATE
//        newCourse.setName("Совсем новый курс");
//        session.persist(newCourse);
        //DELETE
//        session.remove(newCourse);
        //CREATE
//        Course course = new Course();
//        course.setName("Новый курс");
//        course.setType(CourseType.BUSINESS);
//        course.setTeacherId(1);
//        session.persist(course);

        //transaction.commit();

//        Course course = session.get(Course.class, 1);
//        System.out.println(course.getName());
        sessionFactory.close();
    }



    //String url = "jdbc:mysql://localhost:3306/skillbox";
//        String user = "root";
//        String password = "B@%hG9j4nWOzF%bw";
//
//        try{
//            Connection connection = DriverManager.getConnection(url, user, password);
//
//            Statement statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM courses");
//            while (resultSet.next()) {
//                Course course = new Course();
//
//
////                course.setId(resultSet.getInt("id"));
////                course.setName(resultSet.getString("name"));
                    //   Либо назначать для каждого либо использовать ORM Hibernate
////                String courseName = resultSet.getString("name");
////                System.out.println(courseName);
//            }
//
//            resultSet.close();
//            statement.close();
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
}