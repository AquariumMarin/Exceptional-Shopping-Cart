//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: ExceptionalShoppingCart.java
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import java.util.Scanner;

/**
 * This class contains the methods for getting copy of items in the market, looking up product by
 * name or id and returning a string representation of the item, finding the index of the first null
 * position that can be used to add new market, getting its price of a given item, adding or
 * removing item to a cart, getting the price in dollars of a market item, appending an item to a
 * given cart, counting the number of occurrences of a given item within a cart, checking whether a
 * cart contains at least one occurrence of a given item, calculating the total value in dollars of
 * the cart, removing all the items in cart, and getting summary of the contents of a given cart,
 * saving the cart summary to a file, parsing one line of cart summary and then adding item to cart,
 * and loading the cart summary from the file.
 * 
 * @author Marin Suzuki
 */
public class ExceptionalShoppingCart {

  // Define final parameters (constants)
  private static final double TAX_RATE = 0.05; // sales tax

  // a perfect-size two-dimensional array that stores the list of available items in a given market
  // MarketItems[i][0] refers to a String representation of the item key (unique identifier)
  // MarketItems[i][1] refers the item name
  // MarketItems[i][2] a String representation of the unit price of the item in dollars
  private static String[][] marketItems =
      new String[][] {{"4390", "Apple", "$1.59"}, {"4046", "Avocado", "$0.59"},
          {"4011", "Banana", "$0.49"}, {"4500", "Beef", "$3.79"}, {"4033", "Blueberry", "$6.89"},
          {"4129", "Broccoli", "$1.79"}, {"4131", "Butter", "$4.59"}, {"4017", "Carrot", "$1.19"},
          {"3240", "Cereal", "$3.69"}, {"3560", "Cheese", "$3.49"}, {"3294", "Chicken", "$5.09"},
          {"4071", "Chocolate", "$3.19"}, {"4363", "Cookie", "$9.5"}, {"4232", "Cucumber", "$0.79"},
          {"3033", "Eggs", "$3.09"}, {"4770", "Grape", "$2.29"}, {"3553", "Ice Cream", "$5.39"},
          {"3117", "Milk", "$2.09"}, {"3437", "Mushroom", "$1.79"}, {"4663", "Onion", "$0.79"},
          {"4030", "Pepper", "$1.99"}, {"3890", "Pizza", "$11.5"}, {"4139", "Potato", "$0.69"},
          {"3044", "Spinach", "$3.09"}, {"4688", "Tomato", "$1.79"}, null, null, null, null};

  /**
   * Creates a deep copy of the market catalog
   * 
   * @return Returns a deep copy of the market catalog 2D array of strings
   */
  public static String[][] getCopyOfMarketItems() { //TODO
    String[][] copy = new String[marketItems.length][];
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null) {
        copy[i] = new String[marketItems[i].length];
        for (int j = 0; j < marketItems[i].length; j++)
          copy[i][j] = marketItems[i][j];
      }
    }
    return copy;
  }

  /**
   * Returns a string representation of the item whose name is provided as input TODO
   *
   * @param name name of the item to find
   * @return "itemId name itemPrice" if an item with the provided name was found
   * @throws NoSuchElementException with descriptive error message if no match found
   */
  public static String lookupProductByName(String name) throws NoSuchElementException {
    String s = "No match found";

    // when item is found
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null && name != null && name.equals(marketItems[i][1])) {
        return marketItems[i][0] + " " + marketItems[i][1] + " " + marketItems[i][2];
      }
    }

    // throws NoSuchElementException with descriptive error message if no match found
    throw new NoSuchElementException(s); // matched item was not found
  }

  /**
   * Returns a string representation of the item whose id is provided as input. If key is not a
   * 4-digits int, IllegalArgumentException will be thrown. If matched key is not found in the
   * market, NoSuchElementException will be thrown.
   *
   * @param key id of the item to find
   * @return "itemId name itemPrice" if an item with the provided name was found
   * @throws IllegalArgumentException with descriptive error message if key is not a 4-digits int
   * @throws NoSuchElementException   with descriptive error message if no match found
   */
  public static String lookupProductById(int key)
      throws IllegalArgumentException, NoSuchElementException {

    // throws IllegalArgumentException with descriptive error message if key is not a 4-digits int
    if (key > 9999 || key < 1000) {
      throw new IllegalArgumentException("id should be 4-digits int");
    }

    // throws NoSuchElementException with descriptive error message if no match found
    String s = "No match found";

    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null) {
        if (marketItems[i][0].equals(String.valueOf(key)))
          return marketItems[i][0] + " " + marketItems[i][1] + " " + marketItems[i][2];
      }
    }
    // key of item is not found in market
    throw new NoSuchElementException(s);
  }

  /**
   * Returns the index of the first null position that can be used to add new market items returns
   * the length of MarketItems if no available null position is found
   * 
   * @return returns an available position to add new market items or the length of market items if
   *         no available positions are found
   */
  private static int indexOfInsertionPos() {
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] == null)
        return i;
    }
    return marketItems.length;
  }

  /**
   * Add a new item to market items array, expand the capacity of marketitems if it is full when
   * trying to add new item, use indexofInsertionPos() to find the position to add. If id is not
   * parsable to 4-digits int, name is null or empty string, price is not parsable to double,
   * IllegalArgumentException will be thrown.
   * 
   * @param id    id of the item to add
   * @param name  name of the item to add
   * @param price price of the item to add
   * @throws java.lang.IllegalArgumentException with descriptive error message if id is not parsable
   *                                            to 4-digits int, name is null or empty string, price
   *                                            is not parsable to double
   * 
   */
  public static void addItemToMarketCatalog(String id, String name, String price) {

    int next = indexOfInsertionPos();

    // throws IllegalArgumentException with descriptive error message if id is not parsable to int
    try {
      int idInt = Integer.parseInt(id);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("id is not parsable to int");
    }

    // throws IllegalArgumentException with descriptive error message if id is not
    // 4-digits int in string
    if ((Integer.parseInt(id) > 9999) || (Integer.parseInt(id) < 1000)) {
      throw new IllegalArgumentException("id should be 4 digit int in String");
    }

    // throws IllegalArgumentException with descriptive error message if name is null
    // or empty string
    if (name == null || name == "") {
      throw new IllegalArgumentException("name should not be null or empty string");
    }

    // throws IllegalArgumentException with descriptive error message if price is not
    // parsable to double
    try {
      Double idDouble = Double.parseDouble(price.substring(1)); // index 0 of price is $
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("price is not parsable to double");
    }

    // throws IllegalArgumentException with descriptive error message if price is not
    // parsable to positive double
    if (Double.parseDouble(price.substring(1)) < 0) { // first index is $
      throw new IllegalArgumentException("price is not parsable to positive double");
    }

    // marketItem is full
    if (next == marketItems.length) {
      System.out.println("Full catalog! No further item can be added!");
      String[][] expandedMarketItems = new String[marketItems.length * 2][]; // expand
      for (int i = 0; i < marketItems.length; i++) {
        if (marketItems[i] != null) {
          expandedMarketItems[i] = new String[marketItems[i].length];
          for (int j = 0; j < marketItems[i].length; j++)
            expandedMarketItems[i][j] = marketItems[i][j];
          expandedMarketItems[next] = new String[] {id, name, price};
        }
      }
      marketItems = expandedMarketItems;
    } else {
      marketItems[next] = new String[] {id, name, price};
    }
  }

  /**
   * Returns the price in dollars (a double value) of a market item given its name. If the price
   * does not exist in market, NoSuchElementException will be thrown.
   * 
   * @param name name of the item to get the price
   * @return the price of the item
   * @throws java.util.NoSuchElementException with descriptive error message if price not found
   */
  public static double getProductPrice(String name) {
    // throws NoSuchElementException with descriptive error message if price not found

    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null && name.equals(marketItems[i][1])) {
        return Double.parseDouble(marketItems[i][2].substring(1));
      }
    }
    throw new NoSuchElementException("matched price was not found");
  }

  /**
   * Appends an item to a given cart (appends means adding to the end), and return the new size of
   * cart. If the cart is already full (meaning its size equals its length), IllegalStateException
   * wil be thrown. If the size is less than zero, IllegalArgumentException will be thrown.
   * 
   * @param item the name of the product to be added to the cart
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the size of the oversize array cart after trying to add item to the cart.
   * @throws java.lang.IllegalArgumentException with descriptive error message if size is less than
   *                                            zero
   * @throws java.lang.IllegalStateException    with descriptive error message if this cart is full
   */
  public static int addItemToCart(String item, String[] cart, int size) {
    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    // throws IllegalStateException with descriptive error message if this cart is full
    if (size == cart.length) {
      throw new IllegalStateException("the cart is already full");
    }

    cart[size] = item;
    size++;
    return size;
  }

  /**
   * Returns the number of occurrences of a given item within a cart. This method must not make any
   * changes to the contents of the cart. If size is less than zero, IllegalArgumentException will
   * be thrown.
   * 
   * @param item the name of the item to search
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the number of occurrences of item (exact match) within the oversize array cart. Zero or
   *         more occurrences of item can be present in the cart.
   * @throws java.lang.IllegalArgumentException with descriptive error message if size is less than
   *                                            zero
   */
  public static int nbOccurrences(String item, String[] cart, int size) {
    int count = 0;

    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    for (int i = 0; i < size; i++) {
      if (cart[i].equals(item)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Checks whether a cart contains at least one occurrence of a given item. This method must not
   * make any changes to the contents of the cart. This returns true if there is a match (exact
   * match) of item within the provided cart, and false otherwise. If size is less than zero,
   * IllegalArgumentException will be thrown.
   * 
   * @param item the name of the item to search
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns true if there is a match (exact match) of item within the provided cart, and
   *         false otherwise.
   * @thrown java.lang.IllegalArgumentException with descriptive error message if size is less than
   *         zero
   */
  public static boolean contains(String item, String[] cart, int size) {
    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    for (int i = 0; i < size; i++) {
      if (cart[i].equals(item)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes one occurrence of item from a given cart. This returns the size of the oversize array
   * cart after trying to remove item from the cart. If size is less than zero,
   * IllegalArgumentException will be thrown. If item is not found in the cart,
   * java.util.NoSuchElementException will be thrown.
   * 
   * @param item the name of the item to remove
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the oversize array cart after trying to remove item from the cart.
   * @throws java.lang.IllegalArgumentException with descriptive error message if size is less than
   *                                            zero
   * @throws java.util.NoSuchElementException   with descriptive error message if item not found in
   *                                            the cart
   */
  public static int removeItem(String[] cart, String item, int size) {
    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    for (int i = 0; i < size; i++) {
      if (cart[i].equals(item)) {
        cart[i] = cart[size - 1]; //TODO
        cart[size - 1] = null;
        return size - 1;
      }
    }

    // throws NoSuchElementException with descriptive error message if item not found in the cart
    throw new NoSuchElementException("item was not found in the cart");
  }

  /**
   * Removes all items from a given cart. The array cart must be empty (contains only null
   * references) after this method returns. If size is less than zero, IllegalArgumentException will
   * be thrown. If cart is null, NullPointerException will be thrown.
   * 
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the cart after removing all its items.
   * @throws java.lang.IllegalArgumentException with descriptive error message if size is less than
   *                                            zero
   * @throws java.lang.NullPointerException     with descriptive error message if cart is null
   */
  public static int emptyCart(String[] cart, int size) {
    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    // throws NullPointerException with descriptive error message if cart is null
    if (cart == null) {
      throw new NullPointerException("cart should not be null");
    }

    for (int i = 0; i < cart.length; i++) {
      cart[i] = null;
    }
    return 0;
  }

  /**
   * This method returns the total value in dollars of the cart. All products in the market are
   * taxable (subject to TAX_RATE). If size is less than zero, IllegalArgumentException will be
   * thrown.
   * 
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the total value in dollars of the cart accounting taxes.
   * @throws java.lang.IllegalArgumentException with descriptive error message if size is less than
   *                                            zero
   */
  public static double checkout(String[] cart, int size) {
    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    double total = 0.0;
    for (int i = 0; i < size; i++) {
      total += getProductPrice(cart[i]) * (1 + TAX_RATE);
    }
    return total;
  }

  /**
   * Returns a string representation of the summary of the contents of a given cart. The format of
   * the returned string contains a set of lines where each line contains the number of occurrences
   * of a given item, between spaces and parentheses, followed by one space followed by the name of
   * a unique item in the cart. ( #occurrences ) name1 ( #occurrences ) name2 etc. If size is less
   * than zero, IllegalArgumentException will be thrown.
   * 
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns a string representation of the summary of the contents of the cart
   * @throws java.lang.IllegalArgumentException with descriptive error message if size is less than
   *                                            zero
   */
  public static String getCartSummary(String[] cart, int size) {
    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    String s = "";
    for (int i = 0; i < size; i++) {
      if (!contains(cart[i], cart, i)) { //TODO
        s = s + "( " + nbOccurrences(cart[i], cart, size) + " ) " + cart[i] + "\n";
      }
    }
    return s.trim(); //TODO
  }

  /**
   * Save the cart summary to a file.
   * 
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @param file the file to save the cart summary
   * @throws java.lang.IllegalArgumentException with descriptive error message if size is less than
   *                                            zero
   */
  public static void saveCartSummary(String[] cart, int size, File file)
      throws IllegalArgumentException {

    FileWriter fileWriter = null;

    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    // NO other exception should be thrown by this method
    // Use finally block to close any resource used to write the cart summary into file
    try {
      fileWriter = new FileWriter(file);
      fileWriter.write(getCartSummary(cart, size)); // write to file
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    } catch (IOException e) {
      System.out.println("IOException occured");
    } catch (Exception e) {
      // NO other exception should be thrown by this method
    } finally {
    try {
      if (fileWriter != null) {
        fileWriter.close(); // close resource
      }
    } catch (IOException e) {
      System.out.println("IOException occured");
    }
    }
  }

  /**
   * Parse one line of cart summary and add nbOccurrences of item to cart Correct formatting for
   * line:"( " + nbOccurrences + " ) " + itemName Delimiter: one space (multiple spaces: wrong
   * formatting)
   *
   * @param line a line of the cart summary to be parsed into one item to be added
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the cart after adding items to the cart
   * @throws DataFormatException      with descriptive error message if wrong formatting (including
   *                                  nbOccurrences not parsable to a positive integer less or equal
   *                                  to 10)
   * @throws IllegalArgumentException with descriptive error message if itemName not found in
   *                                  marketItems
   * @throws IllegalStateException    with descriptive error message if cart reaches its capacity
   */
  protected static int parseCartSummaryLine(String line, String[] cart, int size)
      throws DataFormatException, IllegalArgumentException, IllegalStateException {

    int newSize = size;
    String[] parsedLine;

    try {

      parsedLine = line.split(" "); //TODO

      // throws DataFormatException with descriptive error message if wrong formatting
      if ((parsedLine.length != 4) || (!parsedLine[0].equals("("))
          || (!parsedLine[2].equals(")"))) {
        throw new DataFormatException("summary has a wrong formatting");
      }

      // Check if nbOccurrences is parsable to int
      try {
        int idInt = Integer.parseInt(parsedLine[1]);
      } catch (NumberFormatException e) {
        throw new DataFormatException("wrong formatting");
      }

      // Check if nbOccurrences is positive integer and less than 11
      if (Integer.parseInt(parsedLine[1]) <= 0 || Integer.parseInt(parsedLine[1]) > 10) {
        throw new DataFormatException("wrong formatting");
      }

      // throws IllegalArgumentException with descriptive error message if itemName not found in
      // marketItems
      boolean itemNameFound = false;

      for (int i = 0; i < marketItems.length; i++) {
        if (marketItems[i] != null && parsedLine[3] != null
            && parsedLine[3].equals(marketItems[i][1])) {
          itemNameFound = true; // matched item found
        }
      }
      if (itemNameFound == false) { // matched item was not found
        throw new IllegalArgumentException("No matched name of item found in market");
      }

      // throws IllegalStateException with descriptive error message if cart reaches its capacity
      for (int i = 0; i < Integer.parseInt(parsedLine[1]); i++) {
        if (cart.length == newSize) { // cart is full
          throw new IllegalStateException("cart is already full");
        }
        newSize = size + i + 1; // size increased
        cart[size + i] = parsedLine[3]; // add item TODO
      }
    } catch (IllegalStateException e) {
      throw new IllegalStateException("cart is already full");
    } catch (DataFormatException e) {
      throw new DataFormatException("wrong formatting");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No matched name of item found in market");
    } catch (Exception e) {
      // NO other exception should be thrown by this method
    }

    return newSize;
  }

  /**
   * Load the cart summary from the file. For each line of summary, add nbOccurrences of item to
   * cart. Must call parseCartSummaryLine to operate. If size is less than zero,
   * IllegalArgumentException will be thrown. If cart is full, IllegalStateException will be thrown.
   * 
   * @param file file to load the cart summary from
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the cart after adding items to the cart
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   * @throws IllegalStateException    with descriptive error message if cart reaches its capacity
   */
  public static int loadCartSummary(File file, String[] cart, int size)
      throws IllegalArgumentException, IllegalStateException {

    int newSize = size;
    Scanner scnr = null;

    // throws IllegalArgumentException with descriptive error message if size is less than zero
    if (size < 0) {
      throw new IllegalArgumentException("size should be more than or equal to zero");
    }

    // This method MUST call parseCartSummaryLine to operate (to parse each line in file)
    // throws IllegalStateException with descriptive error message if cart reaches its capacity
    // NO other exception should be thrown by this method
    // Use finally block to close any resource used to read from file
    try {
      try {
        scnr = new Scanner(file);
      } catch (IOException e) {
        System.out.println("IOEception occured");
      }
      while (scnr.hasNextLine() == true) {
        try {
          newSize = parseCartSummaryLine(scnr.nextLine().trim(), cart, newSize);
        } catch (DataFormatException e) { // catch exception thrown by parseCartSummaryLine
        } catch (IllegalArgumentException e) {
          // catch exception thrown by parseCartSummaryLine
        } catch (IllegalStateException e) {
          throw new IllegalStateException("cart is full");
        } catch (Exception e) {
          // NO other exception should be thrown by this method
        }
      }
    } catch (IllegalStateException e) {
      throw new IllegalStateException("cart is full");
    } catch (Exception e) {
      // NO other exception should be thrown by this method
    } finally {
      if (scnr != null) {
        scnr.close();
      }
    }

    return newSize;

  }

}
