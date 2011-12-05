/**
 * Copyright (c) 2011 CoreMedia AG, Hamburg. All rights reserved.
 */
package org.example.dstrasse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * TODO[dst] javadoc
 */
public class MultiCatch {

  public void multiCatch() {
    try {
      FileInputStream fis = new FileInputStream(new File("."));
      Integer.parseInt("34");
    } catch (FileNotFoundException e) {
      System.out.println("test");
    }  catch (NumberFormatException e) {
      System.out.println("test");
    }
  }
}
