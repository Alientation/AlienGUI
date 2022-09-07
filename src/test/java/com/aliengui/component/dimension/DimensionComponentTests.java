package com.aliengui.component.dimension;


import org.junit.jupiter.api.*;
import org.junit.runners.Suite;

@Suite.SuiteClasses(DimensionComponentTests.class)
public class DimensionComponentTests {

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
    //@Disabled("Just a test")
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
