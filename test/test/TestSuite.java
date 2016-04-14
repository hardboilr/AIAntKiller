package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    test.ActionTest.class,
    test.CalcTest.class,
    test.CarrierLogicTest.class,
    test.CollectiveMemorytest.class,
    test.ComparatorTest.class,
    test.OpenListTest.class,
    test.ScavengeFoodTest.class,
    test.ShortestPathTest.class,})
public class TestSuite {

}
