package genetics;

import genetics.Chromosome;
import java.util.*;

public class Decoder {
  private Map<String, Character> map;

  public Decoder() {
    map = new HashMap<String, Character>();
    map.put("0000", '0');
    map.put("0001", '1');
    map.put("0010", '2');
    map.put("0011", '3');
    map.put("0100", '4');
    map.put("0101", '5');
    map.put("0110", '6');
    map.put("0111", '7');
    map.put("1000", '8');
    map.put("1001", '9');
    map.put("1010", '+');
    map.put("1011", '-');
    map.put("1100", '*');
    map.put("1101", '/');
  }

  // computes the value of a chromosome by decoding it then computing its value
  public double computeValue(Chromosome chromosome) {
    String exp = decode(chromosome);
    Queue<Character> numbers = new LinkedList<Character>();
    Queue<Character> operators = new LinkedList<Character>();
    fillStacks(exp, numbers, operators);

    double value = Double.parseDouble(Character.toString(numbers.remove()));
    while (!stack.empty()) {
      char operator = operators.remove();
      int number = Double.parseDouble(Character.toString(numbers.remove()));
      switch(operator) {
        case '*': (double)value *= number;
                  break;
        case '/': (double)value /= number;
                  break;
        case '+': (double)value += number;
                  break;
        case '-': (double)value -= number;
                  break;
      }
    }

    return value;
  }

  // converts the genes to their String equivalent as an expression
  private String decode(Chromosome chromosome) {
    StringBuilder exp = new StringBuilder();
    for (int i = 0; i < c.length() / 4; i++) {
      char ch = map.get(chromosome.geneAt(i));
      exp.append(ch);
    }
    return exp.toString();
  }

  // adds to the queues based off the expression
  private void fillStacks(String exp,
                         Queue<Character> numbers,
                         Queue<Character> operators) {

    // variable to keep track of what the current character should be
    boolean currentIsNumber = true;

    // add the numbers and operators to their appropriate stacks
    // whenever a number or operator is expected
    for (int i = 0; i < exp.length(); i++) {
      if (isNumber(exp.charAt(i)) && currentIsNumber) {
        numbers.add(exp.charAt(i));
        currentIsNumber = !currentIsNumber;
      }else if (!isNumber(exp.charAt(i)) && !currentIsNumber) {
        operators.add(exp.charAt(i));
        currentIsNumber = !currentIsNumber;
      }
    }

    // remove the last operator when you have one too many
    if (numbers.size() == operators.size())
      operators.pop();
  }

  // checks if a character is a number
  private boolean isNumber(char c) {
    switch(c) {
      case '+': case '-': case '*': case '/':
        return false;
      default:
        return true;
    }
  }
}
