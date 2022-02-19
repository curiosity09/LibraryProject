package main.by.library.validate.impl;

import main.by.library.entity.Author;
import main.by.library.entity.Book;
import main.by.library.entity.Genre;
import main.by.library.entity.Section;
import main.by.library.util.PageUtil;
import main.by.library.validate.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class BookValidator implements Validator<Book>, PageUtil {

    @Override
    public Optional<Book> validate(HttpServletRequest req) {
        String bookName = req.getParameter(BOOK_NAME_PARAMETER);
        String authorFullName = req.getParameter(AUTHOR_PARAMETER);
        String genre = req.getParameter(GENRE_PARAMETER);
        String section = req.getParameter(SECTION_PARAMETER);
        int quantity = Integer.parseInt(req.getParameter(QUANTITY_PARAMETER));
        int year = Integer.parseInt(req.getParameter(YEAR_PARAMETER));
        if (Objects.nonNull(bookName) && !bookName.isBlank() && isCorrectBookName(bookName)
                && Objects.nonNull(authorFullName)
                && Objects.nonNull(genre) && Objects.nonNull(section)
                && quantity != 0 && year != 0) {
            return Optional.of(Book.builder().name(bookName)
                    .author(Author.builder().name(authorFullName).build())
                    .genre(Genre.builder().name(genre).build())
                    .section(Section.builder().name(section).build()).quantity(quantity).publicationYear(year).build());
        }
        return Optional.empty();
    }

    /**
     * Returns true if the string fulfills the condition
     * @param name String
     * @return true or false
     */
    public static boolean isCorrectBookName(String name) {
        return Objects.nonNull(name) && Pattern.matches("^[A-ZА-Яа-яa-z0-9\\s]+$", name);
    }
}
