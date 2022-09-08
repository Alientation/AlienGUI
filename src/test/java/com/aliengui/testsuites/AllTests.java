package com.aliengui.testsuites;

import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.aliengui")
@ExcludePackages("com.aliengui.testsuites")
public class AllTests {
}
