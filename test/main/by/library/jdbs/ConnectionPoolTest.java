package main.by.library.jdbs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionPoolTest {

    @Test
    void getInstance() {
        ConnectionPool firstInstance = ConnectionPool.getInstance();
        ConnectionPool secondInstance = ConnectionPool.getInstance();
        assertSame(firstInstance, secondInstance);
    }


}