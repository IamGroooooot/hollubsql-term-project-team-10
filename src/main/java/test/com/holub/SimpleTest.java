package test.com.holub;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTest {
    @Test
    void plus() {
        int result = 1 + 2;
        assertEquals(3, result);
    }
}