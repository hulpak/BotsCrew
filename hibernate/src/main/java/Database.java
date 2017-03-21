import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Database {
    private List<Book> books = new ArrayList<Book>();
    SessionFactory sessionFactory;
    Session session;
    Query query;
    Book book = null;

    public void List() {
        Collections.sort(books);
        for (Book b : books) {
            System.out.println(b);
        }
    }
    public void Add(Book bk){
        session.save(bk);
    }
    public void Delete(int id){
            Book b = (Book)session.get(Book.class, id);
            session.delete(b);
    }

    public void Update(int  id, String name){
            Book b = (Book)session.get(Book.class, id);
            b.setName(name);
            session.update(b);
    }

    public  Database() {
         sessionFactory = Hibernate.getSessionfactory();
         session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Add(new Book("CCC","Disel"));
            query = session.createQuery("From Book");
            books = query.list();
            List();
            //add
            //session.save(book);
//hql - add
//            Book book = new Book("Bero","Ferro");
//            session.save(book);
//            int t = 2;
//            Query query = session.createQuery("FROM Book where id = " + t + "");
//            Object i = query.uniqueResult();
//            System.out.println(i);


//
//            String name = "Bero";
//            Query query1 = session.createQuery("FROM Book where name = '"+name+"'");
//            String d = "Xer";
//            Query query2 = session.createQuery("Delete FROM Book where name = '"+d+"'");
//            books = query2.list();


            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}

