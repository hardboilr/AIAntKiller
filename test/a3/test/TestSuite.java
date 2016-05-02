package a3.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    a3.test.ActionTest.class,
    a3.test.CalcTest.class,
    a3.test.CarrierLogicTest.class,
    a3.test.CollectiveMemorytest.class,
    a3.test.ComparatorTest.class,
    a3.test.OpenListTest.class,
    a3.test.ScavengeFoodTest.class,
    a3.test.ShortestPathTest.class,})
public class TestSuite {

}
