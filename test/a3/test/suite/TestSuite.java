package a3.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    a3.test.ActionTest.class,
    a3.test.CalcTest.class,
    a3.test.antlogic.CarrierLogicTest.class,
    a3.test.CollectiveMemorytest.class,
    a3.test.comparator.FoodCostComparatorTest.class,
    a3.test.OpenListTest.class,
    a3.test.behaviour.ScavengeFoodTest.class,
    a3.test.comparator.ExplorationPropensityComparatorTest.class,
    a3.test.behaviour.ExploreTest.class,
    a3.test.ShortestPathTest.class,})

public class TestSuite {
}
