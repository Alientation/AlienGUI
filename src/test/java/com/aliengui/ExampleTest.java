package com.aliengui;

import org.junit.jupiter.api.*;

public class ExampleTest {
    @BeforeAll
    public static void setup() {
        System.out.println("setup tests");
    }

    @BeforeEach
    public void init() {
        System.out.println("starting a test");
    }

    @Test
    @DisplayName("testing creating a test")
    public void test() {
        System.out.println("running a test");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("finished a test");
    }

    @AfterAll
    public static void done() {
        System.out.println("done with all tests");
    }
}
