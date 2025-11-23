package aqa.task6;

import org.hibernate.Session;

public class Task6_8 {

    public static void main(String[] args) {
        Address newAddress = createAddress("FrogLand", "Frog");

        Address readAddress = read(newAddress.getId());

        readAddress.setCity("updatedCity");
        update(readAddress);

        Address addressToDelete = createAddress("deleteCity", "deleteState");

        delete(addressToDelete.getId());
    }

    public static Address createAddress(String city, String state) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Address address = new Address(
                city,
                state,
                (int)(Math.random() * 100000)
        );
        session.save(address);
        session.getTransaction().commit();
        session.close();
        System.out.println("created: " + address);
        return address;
    }

    public static Address read(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Address address = session.get(Address.class, id);
        session.close();
        System.out.println("read: " + address);
        return address;
    }

    public static void update(Address address) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(address);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Address address = session.get(Address.class, id);
        if (address != null) {
            session.delete(address);
            System.out.println("deleted: " + address);
        }
        session.getTransaction().commit();
        session.close();
    }
}