import com.sun.org.apache.xpath.internal.SourceTree;
import org.omg.CORBA.TypeCodePackage.BadKind;

import java.util.Scanner;

public class Console {

    DataBase dataBase = new DataBase();
    Book b  = new Book();
    private boolean f= true;

    public Console(){
        int r;
        while(isF()){
            System.out.println("1: Add, 2: Update, 3:Delete, 4:List, 5: Exit");
            r = Scann();
            menu(r);
            System.out.println();
        }
    }

    public String TypeString(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        return str;
    }
    public int Scann(){
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        return i;
    }
    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }
    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public void menu(int i){

        switch (i){
            case 1:{
                System.out.println("Add:");
                System.out.println("Write Name");
                b.setName(TypeString());
                System.out.println("Write Author");
                b.setAuthor(TypeString());
                getDataBase().Add(b);
                break;
            }
            case 2:{
                System.out.println("Update:");
                System.out.println("Write old Name");
                b.setName(TypeString());
                System.out.println("Write new Name");
                String newName=TypeString();
                getDataBase().Update(b.getName(),newName);
                break;
            }
            case 3:{
                System.out.println("Delete:");
                System.out.println("Write Name");
                b.setName(TypeString());
                getDataBase().Delete(b.getName());
                break;
            }
            case 4:{
                System.out.println("List:");
                getDataBase().ShowList();
                break;
            }
            case 5:{
                System.out.println("Exit");
                setF(false);
                break;
            }
            default:
                System.out.println("It does not exit !!! GoodBye ");
                setF(false);
                break;
        }
    }
}
