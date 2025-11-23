package aqa.task6;
//General:
//Install MySQL server (or any SQL like db)
//Make at least two tables (entities from the previous task)
//Make models for those entities (from Task_5)
//Setup Hibernate for those entities
//Check basic CRUD (create, read, update, and delete the BD records using Hibernate)
//Generate a few rows into all tables
import org.hibernate.Session;
import java.util.UUID;

public class Task6 {
    public static void main(String[] args) {
        Session session = null;

        Address newAddress = createAddress();

        session = HibernateUtil.getSessionFactory().openSession();
        Address addressRead = session.get(Address.class, newAddress.getId());
        System.out.println( addressRead);
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        addressRead.setCity("updatedCity");
        session.beginTransaction();
        session.update(addressRead);
        session.getTransaction().commit();
        session.close();

        Address addressToDelete = createAddress();

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(addressToDelete);
        session.getTransaction().commit();
        session.close();

        HibernateUtil.shutdown();
    }

    public static Address createAddress() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Address address = new Address(
                "FrogLand",
                UUID.randomUUID().toString().substring(0, 6),
                12345
        );
        session.save(address);
        session.getTransaction().commit();
        session.close();
        return address;
    }

}
