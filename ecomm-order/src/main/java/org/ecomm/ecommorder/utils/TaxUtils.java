package org.ecomm.ecommorder.utils;

public class TaxUtils {

  static double taxRate = 10; // in percentage

  public static double calculateTax(double subTotal) {
    return subTotal * taxRate / 100;
  }
}
