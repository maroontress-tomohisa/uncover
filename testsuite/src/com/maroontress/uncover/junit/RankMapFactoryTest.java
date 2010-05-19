package com.maroontress.uncover.junit;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.RankMapFactory;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RankMapFactoryTest {

    private RankMapFactory factory;
    private Function func1;
    private Function func2;
    private Function func3;

    @Before public void init() {
	factory = new RankMapFactory() {
	    public int getIntValue(final Function function) {
		return function.getComplexity();
	    }
	};
	func1 = createFunction(1);
	func2 = createFunction(2);
	func3 = createFunction(3);
    }

    private Function createFunction(final int complexity) {
	return new Function(new TestFunctionSource() {
	    @Override public int getComplexity() {
		return complexity;
	    }
	});
    }

    @Test public void create123() {
	Function[] allFunctions = {func1, func2, func3};
	Map<Function, Integer> map = factory.create(allFunctions);

	assertEquals(3, map.size());
	assertEquals(1, (int) map.get(func1));
	assertEquals(2, (int) map.get(func2));
	assertEquals(3, (int) map.get(func3));
    }

    @Test public void create113() {
	Function func1d = new Function(func1);
	Function[] allFunctions = {func1, func1d, func2};
	Map<Function, Integer> map = factory.create(allFunctions);

	assertEquals(3, map.size());
	assertEquals(1, (int) map.get(func1));
	assertEquals(1, (int) map.get(func1d));
	assertEquals(3, (int) map.get(func2));
    }

    @Test public void create0() {
	Function[] allFunctions = {};
	Map<Function, Integer> map = factory.create(allFunctions);

	assertEquals(0, map.size());
    }
}
