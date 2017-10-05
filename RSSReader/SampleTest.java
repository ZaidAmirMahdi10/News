package project;

import junit.framework.TestCase;

public class SampleTest extends TestCase {

	public static void main(String[] args) {

	}

	@SuppressWarnings("rawtypes")
	private java.util.List emptyList;

	/**
	 * Sets up the test fixture. * (Called before every test case method.)
	 */
	@SuppressWarnings("rawtypes")
	protected void setUp() {
		emptyList = new java.util.ArrayList();
	}

	/**
	 * * Tears down the test fixture. * (Called after every test case method.)
	 */
	protected void tearDown() {
		emptyList = null;
	}

	public void testSomeBehavior() {
		assertEquals("Empty list should have 0 elements", 0, emptyList.size());
	}

	public void testForException() {
		try {
			emptyList.get(0);
			fail("Should raise an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException success) {

		}
	}

	{

	}

}
