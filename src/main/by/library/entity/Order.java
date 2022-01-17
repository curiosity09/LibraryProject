package main.by.library.entity;

import java.time.LocalDate;
import java.util.Date;

public class Order {

    private int id;
    private Book book;
    private User user;
    private Date rentalTime;
    private LocalDate rentalPeriod;

    public Order(int id,Book book, User reader, Date rentalTime, LocalDate rentalPeriod) {
        this.id = id;
        this.book = book;
        this.user = reader;
        this.rentalTime = rentalTime;
        this.rentalPeriod = rentalPeriod;
    }

    public Order(int id, Book book, User reader, Date rentalTime) {
        this.id = id;
        this.book = book;
        this.user = reader;
        this.rentalTime = rentalTime;
    }

    public Order(Book book, User reader) {
        this.book = book;
        this.user = reader;
    }

    public Order(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (book != null ? !book.equals(order.book) : order.book != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (rentalTime != null ? !rentalTime.equals(order.rentalTime) : order.rentalTime != null) return false;
        return rentalPeriod != null ? rentalPeriod.equals(order.rentalPeriod) : order.rentalPeriod == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (rentalTime != null ? rentalTime.hashCode() : 0);
        result = 31 * result + (rentalPeriod != null ? rentalPeriod.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", rentalTime=" + rentalTime +
                ", rentalPeriod=" + rentalPeriod +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(Date rentalTime) {
        this.rentalTime = rentalTime;
    }

    public LocalDate getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(LocalDate rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }
}
