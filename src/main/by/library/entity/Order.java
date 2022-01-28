package main.by.library.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {

    private int id;
    private List<Book> book;
    private User user;
    private LocalDateTime rentalTime;
    private LocalDateTime rentalPeriod;

    public Order(int id, List<Book> book, User user, LocalDateTime rentalTime, LocalDateTime rentalPeriod) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.rentalTime = rentalTime;
        this.rentalPeriod = rentalPeriod;
    }

    public Order(int id, List<Book> book, User user, LocalDateTime rentalTime) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.rentalTime = rentalTime;
    }

    public Order(List<Book> book, User reader, LocalDateTime rentalTime, LocalDateTime rentalPeriod) {
        this.book = book;
        this.user = reader;
        this.rentalTime = rentalTime;
        this.rentalPeriod = rentalPeriod;
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
        if (!Objects.equals(book, order.book)) return false;
        if (!Objects.equals(user, order.user)) return false;
        if (!Objects.equals(rentalTime, order.rentalTime)) return false;
        return Objects.equals(rentalPeriod, order.rentalPeriod);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(LocalDateTime rentalTime) {
        this.rentalTime = rentalTime;
    }

    public LocalDateTime getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(LocalDateTime rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }
}
