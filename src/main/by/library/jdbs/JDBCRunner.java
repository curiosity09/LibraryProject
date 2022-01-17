package main.by.library.jdbs;

import main.by.library.dao.BookDaoImpl;
import main.by.library.dao.OrderDaoImpl;
import main.by.library.dao.UserDaoImpl;
import main.by.library.entity.*;

import java.util.List;
// добавить лист в Order

public class JDBCRunner {

    public static void main(String[] args) {
        BookDaoImpl bookDao = new BookDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        OrderDaoImpl o = new OrderDaoImpl();
/*        List<Book> witcher = bookDao.findBookByName("Wither");
        System.out.println(witcher);*/
/*        List<Book> bookByName = bookDao.findAllBook();
        System.out.println(bookByName);*/
/*        List<Book> misha = bookDao.findBookByAuthorSurname("Misha");
        System.out.println(misha);*/
/*        List<Book> roman = bookDao.findBookByGenre("Roman");
        System.out.println(roman);*/
/*        List<Order> allOrder = o.findAllOrder();
        System.out.println(allOrder);*/
        User misha = new User("luka123","passkirill",User.ROLE_USER,new UserData("Kirill","Lukoshko","+37544534521","fdfasfa"));
        boolean b = userDao.AddNewUser(misha);
        System.out.println(b);
        List<User> allUsers = userDao.findAllUsers();
        System.out.println(allUsers);
/*
        Order order = new Order(new Book("Harry Potter",new Author("Джоан","Роулинг"),1999),new Reader("login12","pass",ROLE.READER), Date.valueOf(LocalDate.now()));
*/
/*        Order order = new Order(new Book("Wither",new Author("Vasya","Misha"),1993),new Reader("login10","password",User.ROLE_USER));
        boolean b = o.addOrder(order);
        System.out.println(b);*/
/*        Order deleteOrder = new Order(2);
        boolean b = o.deleteOrder(deleteOrder);
        System.out.println(b);*/
/*        Book potter = new Book("Harry Potter",new Author("Джоан","Роулинг"),new Genre("Fantasy"),1999);
        boolean b = bookDao.AddNewBook(potter);
        System.out.println(b);*/
    }
}
