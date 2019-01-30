import java.util.logging.Logger;

abstract class AbstractCluster {
    abstract int getSize();
    abstract boolean add(FeatureVector elem);
    abstract FeatureVector getElementAt(int index);
    abstract FeatureVector getCentroid();
    abstract void setCentroid(FeatureVector centroid);
    abstract double getVariance();
    public abstract String toString();
}

class Cluster extends AbstractCluster {

    private static final String LOG_TAG = Cluster.class.getSimpleName();
    private static final Logger logger = Logger.getLogger(LOG_TAG);

    private FeatureVector mCentroid;
    private FeatureVector[] mVectorsArray;
    private int mCapacity;
    private int mNextIndex;
    private int mSize;

    Cluster(int capacity) {
        this.mCapacity = capacity;
        this.mNextIndex = 0;
        this.mSize = 0;
        this.mVectorsArray = new FeatureVector[capacity];
    }

    //returns the number of FeatureVectors
    @Override
    int getSize() {
        return this.mNextIndex;
    }

    @Override
    boolean add(FeatureVector elem) {
        if(mNextIndex == mCapacity) return false;
        else {
            mVectorsArray[mNextIndex++] = elem.copy();
            return true;
        }
    }

    //returns FeatureVector at the given index
    @Override
    FeatureVector getElementAt(int index) {
        return mVectorsArray[index];
    }

    @Override
    FeatureVector getCentroid() {
        if (this.mNextIndex == 0) return null;    //if the cluster is empty
        if (this.mCentroid == null)
            this.mCentroid = new FeatureVector("ClusterCentroid", this.mVectorsArray[0].getSize());
        for (int i = 0; i < this.mCentroid.getSize(); i++)
            this.mCentroid.featureSet(i, 0);
        for (int i = 0; i < this.mNextIndex; i++) {
            for (int j = 0; j < this.mVectorsArray[i].getSize(); j++) {
                this.mCentroid.featureSet(j, this.mCentroid.featureAt(j) + this.mVectorsArray[i].featureAt(j));
            }
        }
        for (int i = 0; i < this.mCentroid.getSize(); i++) this.mCentroid.featureSet(i, this.mCentroid.featureAt(i) / mNextIndex);
        return this.mCentroid;
    }

    @Override
    void setCentroid(FeatureVector centroid) {
        this.add(centroid);
        this.mCentroid = centroid;
    }

    @Override
    double getVariance() {
        double distance, sum = 0.0;
        FeatureVector centroid = this.getCentroid();
        for (int i = 0; i < this.mSize; i++) {
            distance = 0.0;
            for (int j = 0; j < this.mVectorsArray[0].getSize(); j++) {
                distance += Math.sqrt(Math.pow(centroid.featureAt(j) - this.mVectorsArray[i].featureAt(j), 2));
            }
            sum += Math.pow(distance, 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        String value;
        value = "Cluster : {";
        for (int i = 0; i < this.getSize(); i++) {
            value += this.mVectorsArray[i].toString() + ", ";
        }
        return value.substring(0, value.length() - 2) + "}";
    }
}