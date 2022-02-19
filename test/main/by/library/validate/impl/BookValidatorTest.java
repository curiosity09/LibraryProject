package main.by.library.validate.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BookValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "  __  ", "."})
    void isCorrectBookName(String bookName) {
        assertFalse(BookValidator.isCorrectBookName(bookName));
    }

    @Test
    void BookNameNotNull() {
        assertFalse(BookValidator.isCorrectBookName(null));
    }
}