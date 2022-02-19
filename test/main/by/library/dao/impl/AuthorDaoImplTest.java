package main.by.library.dao.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoImplTest {

    @Test
    void getInstance() {
        AuthorDaoImpl firstInstance = AuthorDaoImpl.getInstance();
        AuthorDaoImpl secondInstance = AuthorDaoImpl.getInstance();
        assertSame(firstInstance,secondInstance);
    }
}