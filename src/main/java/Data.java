import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.interval.ConfidenceInterval;

import java.util.ArrayList;

public class Data {

    ArrayList<Double> dataList = new ArrayList<>();
    String variable;
    double geometricMean;
    double arithmeticMean;
    double standardDeviationEstimation;
    double range;
    double covarianceCoefficient;
    int length;
    double variationCoefficient;
    double varianceEstimation;
    ConfidenceInterval confidenceInterval;
    double minimum;
    double maximum;

    public Data(String variable) {
        this.variable = variable;
    }

    public void calculateAll(double[] secondArray) {
        this.geometricMean = calculateGeometricMean();
        this.arithmeticMean = calculateArithmeticMean();
        this.standardDeviationEstimation = calculateStandardDeviationEstimation();
        this.range = calculateRange();
        this.covarianceCoefficient = calculateCovarianceCoefficient(secondArray);
        this.length = calculateLength();
        this.variationCoefficient = calculateVariationCoefficient();
        this.varianceEstimation = calculateVarianceEstimation();
        this.confidenceInterval = calculateConfidenceInterval();
        this.minimum = calculateMinimum();
        this.maximum = calculateMaximum();
    }

    public double[] getDataInArray() {
        return this.dataList.stream().mapToDouble(d -> d).toArray();
    }

    public double calculateGeometricMean() {
        return StatUtils.geometricMean(getDataInArray());
    }

    public double calculateArithmeticMean() {
        return StatUtils.mean(getDataInArray());
    }

    public double calculateStandardDeviationEstimation() {
        return new StandardDeviation().evaluate(getDataInArray());
    }

    public double calculateRange() {
        return StatUtils.max(getDataInArray()) - StatUtils.min(getDataInArray());
    }

    public double calculateCovarianceCoefficient(double[] secondArray) {
        return new Covariance().covariance(getDataInArray(), secondArray);
    }

    public int calculateLength() {
        return dataList.size();
    }

    public double calculateVariationCoefficient() {
        return new StandardDeviation().evaluate(getDataInArray()) / StatUtils.mean(getDataInArray());
    }

    public ConfidenceInterval calculateConfidenceInterval() {
        NormalDistribution normalDistribution = new NormalDistribution();
        double z = normalDistribution.inverseCumulativeProbability(1.0 - 0.06 / 2.0);
        double marginOfError = z * calculateStandardDeviationEstimation() / Math.sqrt(dataList.size());
        return new ConfidenceInterval(
                calculateArithmeticMean() - marginOfError,
                calculateArithmeticMean() + marginOfError,
                1.0 - 0.06);
    }

    public double calculateVarianceEstimation() {
        return new Variance().evaluate(getDataInArray());
    }

    public double calculateMinimum() {
        return StatUtils.min(getDataInArray());
    }

    public double calculateMaximum() {
        return StatUtils.max(getDataInArray());
    }
}