import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ClusterTest extends TestCase {

    public void testAdd() {

	FeatureVector[] examples;
	examples = new FeatureVector[3];
	examples[0] = new FeatureVector("A", new double[] { 0.0, 1.0 });
	examples[1] = new FeatureVector("B", new double[] { 1.0, 0.0 });
	examples[2] = new FeatureVector("C", new double[] { 1.0, 1.0 });

	Cluster c;
	c = new Cluster(3);
	Assert.assertEquals(0, c.getSize());

	for (int i = 0; i < examples.length; i++) {
	    c.add(examples[i]);
	    Assert.assertEquals(i + 1, c.getSize());
	}

	for (int i = 0; i < examples.length; i++) {
	    Assert.assertTrue(c.getElementAt(i).equals(examples[i]));
	}

    }

    public void testGetCentroid() {
	Cluster c;
	c = new Cluster(3);
	c.add(new FeatureVector("A", new double[] { 0.0, 1.0 }));
	c.add(new FeatureVector("B", new double[] { 2.0, 1.0 }));
	Assert.assertTrue(c.getCentroid().equals(new FeatureVector("C", new double[] { 1.0, 1.0 })));
	c.add(new FeatureVector("D", new double[] { 4.0, 7.0 }));
	Assert.assertTrue(c.getCentroid().equals(new FeatureVector("E", new double[] { 2.0, 3.0 })));
    }

    public static void main( String[] args ) {
	TestSuite suite = new TestSuite();
	suite.addTestSuite( ClusterTest.class );
	junit.textui.TestRunner.run( suite );
    }

}
