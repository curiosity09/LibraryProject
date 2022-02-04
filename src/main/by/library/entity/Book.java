package main.by.library.entity;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name;
    private Author author;
    private Genre genre;
    private Section section;
    private int quantity;
    private int publicationYear;

    public Book(String name, Author author, Genre genre, int publicationYear) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }

    public Book(int id) {
        this.id = id;
    }

    public Book(int id, String name, Author author, int publicationYear) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public Book(String name, Author author, int publicationYear) {
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public Book(String name, Author author, int publicationYear, int quantity) {
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
    }

    public Book(String name, Author author, Genre genre, Section section, int quantity, int publicationYear) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.section = section;
        this.quantity = quantity;
        this.publicationYear = publicationYear;
    }

    public Book(int id, String name, Author author, Genre genre, Section section, int quantity, int publicationYear) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.section = section;
        this.quantity = quantity;
        this.publicationYear = publicationYear;
    }

    public Book() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (quantity != book.quantity) return false;
        if (publicationYear != book.publicationYear) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (genre != null ? !genre.equals(book.genre) : book.genre != null) return false;
        return section != null ? section.equals(book.section) : book.section == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (section != null ? section.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + publicationYear;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", section=" + section +
                ", quantity=" + quantity +
                ", publicationYear=" + publicationYear +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
