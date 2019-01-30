import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//    Initialization
//    o Create k clusters and let D designate this set of clusters.
//    o Randomly select , without replacement, k examples from the n input examples.
//        Each randomly selected example becomes the centroid of a cluster.
//        Repeat
//    o Create k clusters and let D′ designate this set of clusters.
//    o For each input example e, let i be the cluster of D such that the distance e to the
//        centroid of Di is minimum. Assign e to D′i, where D′i designates the i-th cluster
//        of D′.
//    o Updating D.
//    § Compare the corresponding clusters of D and D′.
//    § If the mVectorSize or the centroid has changed, then the respective cluster
//        of D′ replaces that of D.
//    § If none of the clusters have changed, the algorithm terminates and returns
//        the current clusters.
//    § Otherwise, the algorithm resumes its outer loop.
//
//            Note: An object of the class java.util.Random has a method nextInt(int n) that returns a
//    randomly generated number in the range [0,n[
//            Note: When a Cluster has only one example, that example is centroid!

public class Clustering {

    private static final String LOG_TAG = Clustering.class.getSimpleName();
    private static final Logger logger = Logger.getLogger(LOG_TAG);

    private static int index = 0;
    private static Random random = new Random();
    private static double minDistance = Double.MAX_VALUE;
    private static double distance = 0.0;

    public static Cluster[] kmeans(FeatureVector[] examples, int k){

        FeatureVector[] vectors = new FeatureVector[examples.length];
        Cluster[] clusters = new Cluster[k];

        // Copy the examples to a new vector for mutation
        for (int i = 0; i < vectors.length; i++) {
            vectors[i] = examples[i].copy();
        }

        // Random population cycle
        initialize(k, clusters, vectors);
        populate(vectors, clusters);

        for (int i = 0; i < vectors.length; i++) {
            vectors[i] = examples[i].copy();
        }

        // Initialize deterministic cycle
        for (int i = 0; i < k; i++ ) {
            FeatureVector centroid = clusters[i].getCentroid();
            clusters[i] = new Cluster(vectors.length);
            minDistance = Double.MAX_VALUE;
            index = 0;
            for (int j = 0; j < vectors.length; j++) {
                if (vectors[j] == null) continue;
                distance = centroid.getDistance(vectors[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    index = j;
                }
            }
            clusters[i].setCentroid(vectors[index]);
            vectors[index] = null;
        }

        populate(vectors, clusters);

        return clusters;
    }

    // Fill the cluster centers, i.e. fill each cluster with one vector
    private static void initialize(int k, Cluster[] clusters, FeatureVector[] vectors) {
        for (int i = 0; i < k; i++ ) {
            clusters[i] = new Cluster(vectors.length);
            do {
                index = random.nextInt(vectors.length);
            } while (vectors[index] == null);
            clusters[i].setCentroid(vectors[index]);
            vectors[index] = null;
        }
    }

    // Populate the clusters with the remaining FeatureVector objects
    private static void populate(FeatureVector[] vectors, Cluster[] clusters) {
        for (int i = 0; i < vectors.length; i++) {
            if (vectors[i] == null) continue;
            minDistance = Double.MAX_VALUE;
            index = 0;
            for (int j = 0; j < clusters.length; j++) {
                distance = vectors[i].getDistance(clusters[j].getCentroid());
                if (distance < minDistance) {
                    minDistance = distance;
                    index = j;
                }
            }
            clusters[index].add(vectors[i]);
            vectors[i] = null;
        }
    }
}
