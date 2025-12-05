//    package aqa.task8;
//    //General:
//    //Install MySQL server (or any SQL like db)
//    //Make at least two tables (entities from the previous task)
//    //Make models for those entities (from Task_5)
//    //Setup Hibernate for those entities
//    //Check basic CRUD (create, read, update, and delete the BD records using Hibernate)
//    //Generate a few rows into all tables
//
//    import aqa.db.HibernateUtil;
//    import org.hibernate.Session;
//
//    public class Task6 {
//
//        public static Address createAddress(String city, String state) {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//
//            Address address = new Address(
//                    city,
//                    state,
//                    (int) (Math.random() * 90000 + 10000)
//            );
//
//            session.save(address);
//            session.getTransaction().commit();
//            session.close();
//            return address;
//        }
//
//        public static User createUser(String name, int age, Address address) {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//
//            User user = new User(name, age, address);
//            session.save(user);
//
//            session.getTransaction().commit();
//            session.close();
//            return user;
//        }
//
//        public static Address readAddress(Integer id) {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            Address address = session.get(Address.class, id);
//            session.close();
//            return address;
//        }
//
//        public static User readUser(Integer id) {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            User user = session.get(User.class, id);
//            session.close();
//            return user;
//        }
//
//        public static void updateAddress(Address address) {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.update(address);
//            session.getTransaction().commit();
//            session.close();
//        }
//
//        public static void deleteAddress(Address address) {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.delete(address);
//            session.getTransaction().commit();
//            session.close();
//        }
//
//        public static void deleteUser(User user) {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.delete(user);
//            session.getTransaction().commit();
//            session.close();
//        }
//    }
//
