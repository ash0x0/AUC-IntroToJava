import java.util.logging.Logger;

abstract class AbstractFeatureVector {
    abstract String getName();
    abstract int getSize();
    abstract double featureAt(int index);
    abstract void featureSet(int index, double value);
    abstract FeatureVector copy();
    abstract double getDistance(FeatureVector other);
    abstract void plus(FeatureVector other);
    abstract void div(FeatureVector other);
    abstract void div(double value);
    public abstract String toString();
    abstract boolean equals(FeatureVector other);
}

public class FeatureVector extends AbstractFeatureVector {

    private static final String LOG_TAG = FeatureVector.class.getSimpleName();
    private static final Logger logger = Logger.getLogger(LOG_TAG);

    private static boolean verbose; //flag to print verbose or not
    private String mVectorName;
    private int mVectorSize;
    private double[] mVectorElements;

    static void setVerbose(boolean value) {
        verbose = value;
    }

    FeatureVector(String name, int size) {
        this.mVectorName = name;
        this.mVectorSize = size;
        this.mVectorElements = new double [size];
    }

    FeatureVector(String name, double[] elems) {
        this.mVectorName = name;
        this.mVectorSize = elems.length;
        this.mVectorElements = new double[mVectorSize];
        for (int i = 0; i < elems.length; i ++) {
            this.mVectorElements[i] = elems[i];
        }
    }

    @Override
    String getName() {
        return this.mVectorName;
    }

    @Override
    int getSize() {
        return this.mVectorSize;
    }

    @Override
    double featureAt(int index) {
        return mVectorElements[index];
    }

    @Override
    void featureSet(int index, double value) {
        mVectorElements[index]=value;
    }

    @Override
    FeatureVector copy() {
        double copy[] = new double[mVectorElements.length];
        for (int i = 0; i < mVectorElements.length; i++) {
            copy[i] = mVectorElements[i];
        }
        return new FeatureVector(this.mVectorName, copy);
    }

    @Override
    double getDistance(FeatureVector other) {
        double sum = 0.0;
        for (int i = 0; i < mVectorElements.length; i++) {
            sum += Math.pow(this.featureAt(i) - other.featureAt(i), 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    void plus(FeatureVector other) {
        for(int i = 0; i < mVectorElements.length; i++) {
            this.mVectorElements[i] = this.featureAt(i) + other.featureAt(i);
        }
    }

    @Override
    void div(FeatureVector other) {
        for(int i = 0; i < mVectorElements.length; i++) {
            this.mVectorElements[i] = this.featureAt(i)/other.featureAt(i);
        }
    }

    @Override
    void div(double value) {
        for(int i = 0; i < mVectorElements.length; i++) {
            this.mVectorElements[i] = this.featureAt(i)/value;
        }
    }

    @Override
    public String toString() {
        String value;
        if (verbose) {
            value = this.mVectorName + ": {";
            for (int i = 0; i < this.getSize(); i++) {
                value += this.mVectorElements[i] + ", ";
            }
            value = value.substring(0, value.length() - 2) + "}";
        } else value = this.mVectorName;
        return value;
    }

    @Override
    boolean equals(FeatureVector other) {
        if (other == null) return false;
        if (this.getSize()==other.getSize()) {
            for(int i=0;i<this.getSize();i++) {
                if (this.featureAt(i) !=other.featureAt(i)) return false;
            }
        } else return false;
        return true;
    }
}
