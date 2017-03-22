import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.SourceType;


import java.util.*;

public class Database {
    private List<Book> books = new ArrayList<Book>();
    SessionFactory sessionFactory;
    Session session;
    Query query;
    Book book = new Book();
    List<Book> list = new ArrayList<Book>();


    public void Delete(int id){
        sessionFactory = Hibernate.getSessionfactory();
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query q=   session.createQuery("delete From Book where id=:id").setParameter("id",id);
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void Delete(String name){
        sessionFactory = Hibernate.getSessionfactory();
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery("delete From Book where name=:name").setParameter("name",name);
           q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void Update(int  id, String name){
        sessionFactory = Hibernate.getSessionfactory();
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query q=  session.createQuery("update Book set name=:name where id=:id");
            q.setParameter("name",name);
            q.setParameter("id",id);
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

    }
    }
    public void Update(String oldName, String newName){
        sessionFactory = Hibernate.getSessionfactory();
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
           Query q=  session.createQuery("update Book set name=:newName where name=:oldName");
            q.setParameter("oldName",oldName);
            q.setParameter("newName",newName);
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public int FewBooks(String name){
        int count = 0;
        list.clear();
        Iterator<Book> it = books.listIterator();
        while(it.hasNext()){
            book = it.next();
            if(book.getName().equals(name)){
                list.add(book);
                count++;
            }
        }
        return count;
    }
    public void Print() {
            for (Book i : list) {
                System.out.println(i.toString());
            }
        }
    public  Database() {
         sessionFactory = Hibernate.getSessionfactory();
         session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            query = session.createQuery("From Book");
            books = query.list();

            ConsoleWrite();

            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
        } catch (Exception e) {
        }
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public void ConsoleWrite(){
       boolean f= true;
       while(f){
           System.out.println("1.Add, 2.Delete, 3.Update, 4.List, 5.Exit");
           int pos = ScannerNumber();
           switch(pos){
               case 1:{
                   System.out.println(" Write name the book :");
                   book.setName(StrLen());
                   System.out.println(" Write author the book :");
                   book.setAuthor(StrLen());
                   Add(book);
                   break;
               }
               case 2:{
                   System.out.println(" Write name the book which do you want to delete:");
                   String name = StrLen();
                   int count = FewBooks(name);
                   if(count>1) {
                       Print();
                       System.out.println(" Write number name the book which do you want to delete:");
                       book.setId(ScannerNumber());
                       Delete(book.getId());
                       break;
                   }
                   else
                   {
                       Delete(name);
                       break;
                   }

               }
               case 3:{
                   System.out.println(" Write name the book which do you want to update:");
                   String old = StrLen();
                   System.out.println(" Write new name the book which do you want to update:");
                   String newName = StrLen();
                   int count = FewBooks(old);
                   if(count>1) {
                       Print();
                       System.out.println(" Write number name the book which do you want to update:");
                       int id = ScannerNumber();
                       Update(id,newName);
                       break;
                   }
                   else
                   {
                       Update(old,newName);
                       break;
                   }
               }
               case 4:{
                   List();
                   break;
               }
               case 5:{
                   f=false;
                   break;
               }
               default:{
                   f=false;
                   break;
               }
           }

       }
    }
    public int ScannerNumber(){
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        return number;
    }
    public String StrLen(){
       Scanner sc = new Scanner(System.in);
       String str = sc.nextLine();
       return str;
    }
    public void List() {
        query = session.createQuery("From Book");
        books = query.list();
        Collections.sort(books);
        for (Book b : books) {
            System.out.println(b);
        }
        System.out.println();
    }
    public void Add(Book bk){
        sessionFactory = Hibernate.getSessionfactory();
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(bk);
            System.out.println(bk.getName()+" "+bk.getAuthor()+" was added");
            System.out.println();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

