public class Book implements Comparable<Book>{
    private int id;
    private String name;
    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Book(int id,String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }
    public Book(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return   name +" - \""+ author+"\"";
    }

    public int compareTo(Book b) {
        return this.name.compareTo(b.name);
    }
}
