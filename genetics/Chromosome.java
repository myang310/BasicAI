package genetics;

import java.lang.Math;
import java.lang.StringBuilder;

public class Chromosome {
  private StringBuilder chromosome;

  public Chromosome(String chromosome) {
    this.chromosome = new StringBuilder(chromosome);
  }

  public Chromosome(StringBuilder chromosome) {
    this.chromosome = new StringBuilder(chromosome.toString());
  }

  public void crossover(Chromosome other, double rate) {
    int startIndex = Math.random() * length();
    for (int i = startIndex; i < length(); i++)
      if (chromosome.charAt(i) != other.chromosome.charAt(i))
        if (Math.random() < rate) {
          this.swap(i);
          other.swap(i);
        }
  }

  public String toString() {
    return chromosome.toString();
  }

  public String geneAt(int index) {
    int startIndex = index * 4;
    return chromosome.substring(startIndex, startIndex+4);
  }

  public static Chromosome getRandom(int length) {
    length -= length % 4;
    StringBuilder chromosome = new StringBuilder();
    for (int i = 0; i < length; i++)
      chromosome.append(randomBit());
    return new Chromosome(chromosome);
  }

  public int length() {
    return chromosome.length();
  }

  public void mutate(double rate) {
    for (int i = 0; i < chromosome.length(); i++)
      if (Math.random() < rate) {
        swap(i);
  }

  private static char randomBit() {
    if (Math.random() < 0.5)
      return '0';
    else
      return '1';
  }

  private void swap(int index) {
    if (chromosome.charAt(index) == '0') {
      chromosome.setCharAt(index, '1');
    }else {
      chromosome.setCharAt(index, '0');
    }
  }
}
