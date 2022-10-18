//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: ExceptionalShoppingCartTester.java
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import java.util.Scanner;
import java.util.Arrays;

/**
 * This class contains testers for lookupProductById(), lookupProductByName(),
 * addItemToMarketCatalog(), SaveCartSummary(), and loadCartSummary().
 * 
 * @author Marin Suzuki
 */
public class ExceptionalShoppingCartTester {

  /**
   * Main method
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println(testLookupMethods());
    System.out.println(testAddItemToMarketCatalog());
    System.out.println(testSaveCartSummary());
    System.out.println(testParseCartSummaryLine());
    System.out.println(testLoadCartSummary());
    System.out.println(runAllTests());
  }

  /**
   * This method tests lookupProductById() method and lookupProductByName() method
   *
   * @return true if all tests pass, and false if any of your tests fails.
   *
   */
  public static boolean testLookupMethods() {

    try {
      ExceptionalShoppingCart.lookupProductByName(null); // invalid input: null
      return false; // incorrect
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      ExceptionalShoppingCart.lookupProductByName("not existing name"); // invalid
                                                                        // input: not matched name
      return false; // incorrect
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      ExceptionalShoppingCart.lookupProductById(12345); // invalid input: 5 digit int
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      ExceptionalShoppingCart.lookupProductById(99); // invalid input: 2 digit int

      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      ExceptionalShoppingCart.lookupProductById(0000); // invalid input: out of range int

      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      ExceptionalShoppingCart.lookupProductById(4390); // valid input: not matched item

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      ExceptionalShoppingCart.lookupProductById(3330); // invalid input: no matched id
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    return true; // no bug detected
  }

  /**
   * This method tests addItemToMarketCatalog() method
   *
   * @return true if all tests pass, and false if any of your tests fails.
   *
   */
  public static boolean testAddItemToMarketCatalog() {

    try {
      // invalid input: id is not 4 digit
      ExceptionalShoppingCart.addItemToMarketCatalog("100", "itemName", "$0.00");
      return false; // incorrect

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      // invalid input: id is not parsable to int
      ExceptionalShoppingCart.addItemToMarketCatalog("1h1h", "itemName", "$0.00");

      return false; // incorrect

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      // invalid input: id is not parsable to int
      ExceptionalShoppingCart.addItemToMarketCatalog("hhhh", "itemName", "$0.00");
      return false; // incorrect

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      // invalid input: item name is null or empty string
      ExceptionalShoppingCart.addItemToMarketCatalog("1000", null, "$0.00");
      return false; // incorrect

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      // invalid input: item name is null or empty string
      ExceptionalShoppingCart.addItemToMarketCatalog("1000", "", "$0.00");
      return false; // incorrect

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      // invalid input: price is not parsable to positive double
      ExceptionalShoppingCart.addItemToMarketCatalog("1000", "itemName", "$-2.5");

      return false; // incorrect

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      // invalid input: price is not parsable to double
      ExceptionalShoppingCart.addItemToMarketCatalog("1000", "itemName", "$0.0h0h");

      return false; // incorrect

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      return false; // incorrect
    }

    try {
      // valid input
      ExceptionalShoppingCart.addItemToMarketCatalog("1000", "itemName", "$100");
      // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    }

    return true; // no bug detected
  }

  /**
   * This method tests SaveCartSummary() method.
   *
   * @return true if all tests pass, and false if any of your tests fails.
   *
   */
  public static boolean testSaveCartSummary() {
    File newFile = null;
    File newFile1 = null;
    String[] cart = {"Banana"};
    Scanner scnr = null;

    // invalid input: size is less than zero
    try {
      newFile = new File("testFile"); // TODO
      ExceptionalShoppingCart.saveCartSummary(cart, -1, newFile);
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    // valid input
    try {
      newFile1 = new File("testFile1");
      ExceptionalShoppingCart.saveCartSummary(cart, 1, newFile1);
      scnr = new Scanner(newFile1);
      String actualOutput = scnr.nextLine();
      String expectedOutput = "( 1 ) Banana";
      if (!actualOutput.equals(expectedOutput)) {
        return false; // incorrect
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    } finally {
      if (scnr != null) {
        scnr.close();
      }
    }

    // invalid input: cart is null
    try {
      newFile1 = new File("testFile1"); // TODO
      ExceptionalShoppingCart.saveCartSummary(null, 1, newFile1); // invalid input
      scnr = new Scanner(newFile1);
      // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    } finally {
      if (scnr != null) {
        scnr.close();
      }
    }

    // invalid input: file is null
    try {
      ExceptionalShoppingCart.saveCartSummary(cart, 1, newFile1); // invalid input
      scnr = new Scanner(newFile1);
      // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    } finally {
      if (scnr != null) {
        scnr.close();
      }
    }

    return true; // no bug detected
  }

  /**
   * This method tests parseCartSummaryLine() method.
   *
   * @return true if all tests pass, and false if any of your tests fails.
   *
   */
  public static boolean testParseCartSummaryLine() {

    // nbOccurrences is not parsable to int
    try {
      String line = "( h ) Banana";
      String[] cart = {"Banana", null, null};
      int size = 1;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      return false; // incorrect
    } catch (DataFormatException e) {
      System.out.println(e.getMessage()); // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    // nbOccurrences is not positive integer
    try {
      String line = "( -1 ) Banana";
      String[] cart = {"Banana", null, null};
      int size = 1;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      return false; // incorrect
    } catch (DataFormatException e) {
      System.out.println(e.getMessage()); // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    // nbOccurrences is not less than 11
    try {
      String line = "( 11 ) Banana";
      String[] cart = {"Banana", null, null};
      int size = 1;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      return false; // incorrect
    } catch (DataFormatException e) {
      System.out.println(e.getMessage()); // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    // wrong formatting: space
    try {
      String line = "(  10 ) Banana";
      String[] cart = {"Banana", null, null};
      int size = 1;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      return false; // incorrect
    } catch (DataFormatException e) {
      System.out.println(e.getMessage()); // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    // if itemName not found in marketItems
    try {
      String line = "( 10 ) notExistingItem";
      String[] cart = {"Banana", null, null};
      int size = 1;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      return false; // incorrect
    } catch (DataFormatException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    // if cart reaches its capacity
    try {
      String line = "( 10 ) Banana";
      String[] cart = {"Banana", "Banana", "Banana"};
      int size = 3;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      return false; // incorrect
    } catch (DataFormatException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage()); // correct
    }

    // valid input
    try {
      String line = "( 2 ) Banana";
      String[] cart = {"Banana", null, null};
      String[] expectedCart = {"Banana", "Banana", "Banana"};
      int size = 1;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      for (int i = 0; i < cart.length; i++) {
        if (!cart[i].equals(expectedCart[i])) {
          return false; // incorrect
        }
        // correct
      }
    } catch (DataFormatException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    // cart is null
    try {
      String line = "( 10 ) Banana";
      String[] cart = null;
      int size = 3;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      // correct
    } catch (DataFormatException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    // line is null
    try {
      String line = null;
      String[] cart = {"Banana", null, null};
      int size = 1;
      ExceptionalShoppingCart.parseCartSummaryLine(line, cart, size);
      // correct
    } catch (DataFormatException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    }

    return true; // no bug detected
  }

  /**
   * This method tests loadCartSummary() method.
   *
   * @return true if all tests pass, and false if any of your tests fails.
   *
   */
  public static boolean testLoadCartSummary() {
    File newFile2 = null;
    File newFile1 = null;

    // size is less than 0
    try {
      newFile2 = new File("testerFile2");
      String[] cart = {"Banana", null, null};
      int size = -1;

      ExceptionalShoppingCart.loadCartSummary(newFile2, cart, size);
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage()); // correct
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    }

    // file is null
    try {
      String[] cart = {"Banana", null, null};
      int size = 1;

      ExceptionalShoppingCart.loadCartSummary(null, cart, size); // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (Exception e) {
      return false; // incorrect
    }

    // valid input
    try {

      newFile2 = new File("testerFile2");
      FileWriter fileWriter = new FileWriter(newFile2); //TODO
      fileWriter.write("( 2 ) Banana");
      fileWriter.close();

      String[] cart = {"Banana", "Banana", null, null};
      String[] expectedCart = {"Banana", "Banana", "Banana", "Banana"};
      int size = 2;

      ExceptionalShoppingCart.loadCartSummary(newFile2, cart, size);
      for (int i = 0; i < expectedCart.length; i++) {
        if (!expectedCart[i].equals(cart[i])) {
          return false; // incorrect
        }
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage()); // incorrect
      return false;
    } catch (Exception e) {
      return false; // incorrect
    }

    // cart is null
    try {
      FileWriter fileWriter = new FileWriter(newFile2);
      fileWriter.write("( 2 ) Banana");
      fileWriter.close();
      int size = 2;

      ExceptionalShoppingCart.loadCartSummary(newFile2, null, size);
      // correct
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage()); // incorrect
      return false;
    } catch (Exception e) {
      return false; // incorrect
    }

    // cart is full
    try {

      newFile1 = new File("testerFile1");
      FileWriter fileWriter = new FileWriter(newFile1);
      fileWriter.write(" ( 2 ) Banana");
      fileWriter.close();

      String[] cart = {"Banana", "Banana", "Banana"};
      int size = 2;

      ExceptionalShoppingCart.loadCartSummary(newFile1, cart, size);
      return false; // incorrect
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false; // incorrect
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage()); // correct
    } catch (Exception e) {
      return false; // incorrect
    }

    return true; // no bug detected
  }


  public static boolean addItemToMarketCatalogTester() {
    String[][] marketItems =
        new String[][] {{"4390", "Apple", "$1.59"}, {"4046", "Avocado", "$0.59"}};
    try {
      int next = 2;
      if (next == marketItems.length) {
        System.out.println("Full catalog! No further item can be added!");
        String[][] expandedMarketItems = new String[marketItems.length * 2][]; // expand
        for (int i = 0; i < marketItems.length; i++) {
          if (marketItems[i] != null) {
            expandedMarketItems[i] = new String[marketItems[i].length];
            for (int j = 0; j < marketItems[i].length; j++)
              expandedMarketItems[i][j] = marketItems[i][j];
          }
        }
        marketItems = expandedMarketItems;
      } else {
        marketItems[next] = new String[] {"3000", "Na", "$400"};
      }
      System.out.println(marketItems.length);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * This method call all the tester methods.
   *
   * @return true if all tests pass, and false if any of your tests fails.
   *
   */
  public static boolean runAllTests() {
    return testLookupMethods() && testAddItemToMarketCatalog() && testSaveCartSummary()
        && testParseCartSummaryLine() && testLoadCartSummary() && addItemToMarketCatalogTester();
  }
}
