import java.sql.*;
import java.util.*;


public class DataBase {

    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement ps;

    ArrayList<Book> book = new ArrayList<Book>();
    Map<Integer,Book> pos = new HashMap<Integer, Book>();

    public void Connection(){
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        } catch (SQLException sqlEx) {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Cannot connection to DB");
        }
    }
    public DataBase() {
       Connection();
    }

    int CountElem(String name){
        int count=0;
        for(Book b:book){
            String res = b.getName();
            int p = b.getId();
            if(res.equals(name)) {
                count++;
                pos.put(p,new Book(b.getName(),b.getAuthor()));
            }
        }
        return count;
    }

    public void Delete(String name){
        String sql = "DELETE  FROM book  WHERE Name=?";
        int count = CountElem(name);
        if(count>1){
            System.out.println("Exists a few authors from this name " + name);
            for(Map.Entry<Integer,Book> i: pos.entrySet()){
                System.out.println(i.getKey()+"."+i.getValue());
            }
            System.out.println(" Choose only one. Write number:  ");
            Scanner sc =new Scanner(System.in);
            int id = sc.nextInt();
            DeleteById(id);
        }
        else{
             try {
            ps = con.prepareStatement(sql);
            ps.setString(1,name);
            ps.executeUpdate();
            System.out.println("Book was removed");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }
    }

    public void DeleteById(int id) {
        String sql = "DELETE  FROM book  WHERE id="+id+"";
            try {
                ps = con.prepareStatement(sql);
                ps.executeUpdate();
                System.out.println("Book was removed");
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    public void Update(String name,String newName) {
        String sql = "UPDATE book SET  Name='"+newName+"' WHERE Name='"+name+"'";
        try {
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Book " + name + " was updated  on "+newName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void Add(Book b) {
        String sql = "INSERT INTO book (Name,Author) VALUES (?,?)";
        book.add(b);
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, b.getName());
            ps.setString(2, b.getAuthor());
            ps.executeUpdate();
            System.out.println("Book " + b.getName() + " " + b.getAuthor() + " was added ");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToList() {
        book.clear();
        String query = "select * from book";
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                book.add(new Book(rs.getInt(1),rs.getString(2),rs.getString(3)));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ShowList(){
        addToList();
        Collections.sort(book);
        for(Book b:book){
            System.out.println(b.toString());
        }
    }

    public void CloseConnection(){
        try {
            System.out.println("Connection closed");
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
