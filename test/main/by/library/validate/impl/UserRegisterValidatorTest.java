package main.by.library.validate.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterValidatorTest {

    public static Stream<Arguments> userSourceMethod() {
        return Stream.of(
                Arguments.of("abc"),
                Arguments.of(""),
                Arguments.of("123"),
                Arguments.of("QQ")
        );
    }

    @ParameterizedTest
    @MethodSource("userSourceMethod")
    void isCorrectUsername(String username) {
        assertFalse(UserRegisterValidator.isCorrectUsername(username));
    }

    @ParameterizedTest
    @MethodSource("userSourceMethod")
    void isCorrectPassword(String password) {
        assertFalse(UserRegisterValidator.isCorrectPassword(password));
    }

    @ParameterizedTest
    @MethodSource("userSourceMethod")
    void isCorrectName(String name) {
        assertFalse(UserRegisterValidator.isCorrectName(name));
    }

    @ParameterizedTest
    @MethodSource("userSourceMethod")
    void isCorrectEmailAddress(String email) {
        assertFalse(UserRegisterValidator.isCorrectEmailAddress(email));
    }

    @ParameterizedTest
    @MethodSource("userSourceMethod")
    void isCorrectPhoneNumber(String phone) {
        assertFalse(UserRegisterValidator.isCorrectPhoneNumber(phone));
    }
}