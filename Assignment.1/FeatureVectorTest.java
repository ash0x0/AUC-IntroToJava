import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FeatureVectorTest extends TestCase {

    public void testGetSize() {
	FeatureVector v, w;

	v = new FeatureVector("V", 10);
	Assert.assertEquals(10, v.getSize());

	w = new FeatureVector("W", 0);
	Assert.assertEquals(0, w.getSize());
    }

    public void testFeatureAt() {
	FeatureVector v = new FeatureVector("V", 10);

	for (int i = 0; i < 10; i++) {
	    v.featureSet(i, (double) i);
	    Assert.assertEquals((double) i, v.featureAt(i));
	}
    }

    public void testFeatureVector() {
	double[] vs = { -Math.PI, 1.6180339887, 2.7182818284, 1.4142135623 };
	FeatureVector v = new FeatureVector("V", vs);

	for (int i = 0; i < vs.length; i++) {
	    Assert.assertEquals(vs[i], v.featureAt(i));
	}

	// What is the purpose of this test?
	// This is an array reference test, the class shouldn't store only a reference
	for (int i = 0; i < vs.length; i++) {
	    double tmp = vs[i];
	    vs[i] = 0.0;
	    Assert.assertEquals(tmp, v.featureAt(i));
	}
    }

    public void testGetDistance() {
	FeatureVector v = new FeatureVector("V", new double[] { 0.0, 2.0, 1.0, 5.0 });
	FeatureVector w = new FeatureVector("W", new double[] { 2.0, 0.0, -1.0, 7.0 });

	Assert.assertEquals(0.0, v.getDistance(v), 0.00001);
	Assert.assertEquals(0.0, w.getDistance(w), 0.00001);

	Assert.assertEquals(4.0, v.getDistance(w), 0.00001);
	Assert.assertEquals(4.0, w.getDistance(v), 0.00001);

	v = new FeatureVector("V", 0);
	w = new FeatureVector("W", 0);

	Assert.assertEquals(0.0, v.getDistance(w), 0.00001);
    }

    public void testEquals() {
	FeatureVector v = new FeatureVector("V", new double[] { 0.0, 2.0, 1.0, 5.0 });
	FeatureVector w = new FeatureVector("W", new double[] { 2.0, 0.0, -1.0 });
	FeatureVector x = new FeatureVector("X", new double[] { 2.0, 0.0, -1.0 });
	FeatureVector y = new FeatureVector("Y", new double[] { 0.0, 2.0, 1.0 });

	Assert.assertTrue(v.equals(v));
	Assert.assertTrue(w.equals(w));
	Assert.assertTrue(w.equals(x));

	Assert.assertFalse(v.equals(w));
	Assert.assertFalse(x.equals(y));
	Assert.assertFalse(x.equals(null));
    }

    public void testPlus() {
	double[] vs = { -1.0, 0.0, 1.0, 2.0 };
	double[] ws = { 1.0, 1.0, 1.0, 1.0 };
	FeatureVector v = new FeatureVector("V", vs);
	FeatureVector w = new FeatureVector("V", ws);

	v.plus(w);

	for (int i = 0; i < vs.length; i++) {
	    Assert.assertEquals((double) i, v.featureAt(i));
	}

    }

    public void testDiv() {
	double[] vs = { 0.0, 2.0, 4.0, 6.0 };
	FeatureVector v = new FeatureVector("V", vs);

	v.div(2.0);

	for (int i = 0; i < vs.length; i++) {
	    Assert.assertEquals((double) i, v.featureAt(i));
	}

    }
    
    public static void main( String[] args ) {
	TestSuite suite = new TestSuite();
	suite.addTestSuite( FeatureVectorTest.class );
	junit.textui.TestRunner.run( suite );
    }

}
