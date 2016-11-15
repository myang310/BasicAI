package genetics;

import java.util.HashMap<K,V>;
import genetics.Decoder;
import genetics.Chromosome;

public class Problem {
  private Decoder decoder;
  private Chromosome[] chromosomes;
  private double[] fitnesses;
  private double[] probabilities;
  private double target;
  private int chromosomeLength;

  public Problem(int population, double target, int chromosomeLength) {
    decoder = new Decoder();
    chromosomes = new Chromosome[population];
    fitnesses = new double[population];
    probabilities = new double[population];
    this.target = target;
    this.chromosomeLength = chromosomeLength;

    for (int i = 0; i < population; i++)
      chromosomes[i] = Chromosome.getRandom();
  }

  public String generateSolution() {
    Chromosome solution = null;

    findAllFitness();
    computeProbs();
    solution = checkForSolution();
    while (solution == null) {
      Chromosome c1 = selectOne();
      Chromosome c2 = selectOne();

      c1.crossover(c2, 0.7);
      c1.mutate(0.001);
      c2.mutate(0.001);

      findAllFitness();
      computeProbs();
      solution = checkForSolution();
    }

    return solution.toString();
  }

  private void findAllFitness() {
    for (int i = 0; i < fitnesses.length(); i++)
      fitnesses[i] = getFitness(chromosomes[i]);
  }

  private void computeProbs() {
    double sum = 0;
    for (int i = 0; i < probabilities.length(); i++)
      sum += fitnesses[i];

    probabilities[i] = fitnesses[i] / sum;
    for (int i = 1; i < probabilities.length(); i++) {
      probabilities[i] = (fitnesses[i] / sum) + probabilities[i-1];
    }
  }

  private Chromosome checkForSolution() {
    for (int i = 0; i < fitnesses.length(); i++) {
      if (fitnesses[i] == Double.MAX_VALUE)
        return chromosomes[i];
    }

    return null;
  }

  // returns a fitness value determinant on how far the current
  // chromosome is away from being a possible solution; returns
  // MAX_VALUE if it is a possible solution
  private double getFitness(Chromosome chromosome) {
    double value = decoder.computeValue(chromosome);
    if (target - value == 0)
      return Double.MAX_VALUE;
    else
      return 1 / (target - value);
  }

  private Chromosome selectOne() {
    double random = Math.random();
    int index = 0;

    while (random > probabilities[index] && index < probabilities.length())
      index++;
    for (int i = 1; i < probabilities.length(); i++) {
      if (random < probabilities[i])
        return chromosomes[i-1]
    }
    return chromosomes[chromosomes.length()-1];
  }
}
