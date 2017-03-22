import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.*;

public class Database {
    private List<Book> books = new ArrayList<Book>();
    SessionFactory sessionFactory;
    Session session;
    Query query;
    Book book = new Book();
    List<Book> list = new ArrayList<Book>();

    public void List() {
        query = session.createQuery("From Book");
        books = query.list();
        Collections.sort(books);
        for (Book b : books) {
            System.out.println(b);
        }
    }
    public void Add(Book bk){
        sessionFactory = Hibernate.getSessionfactory();
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(bk);
            System.out.println(bk.getName()+" "+bk.getAuthor()+" was added");
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void Delete(int id){
        sessionFactory = Hibernate.getSessionfactory();
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Book b = (Book)session.get(Book.class, id);
            session.delete(b);
        System.out.println(b.getName()+" "+b.getAuthor()+" was deleted");
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
        Book b = (Book)session.createQuery("from Book where name='"+name+"'");
        session.delete(b);
        System.out.println(b.getName()+" "+b.getAuthor()+" was deleted");
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
            Book b = (Book)session.get(Book.class, id);
            b.setName(name);
            System.out.println(b.getName()+" "+b.getAuthor()+" was updated on "+name+" "+b.getAuthor());
            session.update(b);
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
            Book b = (Book)session.createQuery("from Book where name='"+oldName+"'");
            b.setName(newName);
            System.out.println(b.getName()+" "+b.getAuthor()+" was updated on "+newName+" "+b.getAuthor());
            session.update(b);
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

            session.close();
            sessionFactory.close();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
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
                   book.setName(StrLen());
                   int count = FewBooks(book.getName());
                   if(count>1) {
                       Print();
                       System.out.println(" Write number name the book which do you want to delete:");
                       book.setId(ScannerNumber());
                       Delete(book.getId());
                       break;
                   }
                   else
                   {
                       Delete(book.getName());
                       break;
                   }

               }
               case 3:{
                   System.out.println(" Write name the book which do you want to update:");
                   book.setName(StrLen());
                   System.out.println(" Write new name the book which do you want to update:");
                   String newName = StrLen();
                   int count = FewBooks(book.getName());
                   if(count>1) {
                       Print();
                       System.out.println(" Write number name the book which do you want to update:");
                       book.setId(ScannerNumber());
                       Update(book.getId(),newName);
                   }
                   else
                   {
                       Update(book.getName(),newName);
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
}

