/* (C) 2022 */
package com.follybuilders.paradisium;

import static org.junit.jupiter.api.Assertions.*;

import heronarts.lx.LX;
import org.junit.jupiter.api.Test;

class ParadisiumAppTest {
  @Test
  void setup() {
    LX.Flags flags = ParadisiumApp.headlessInit(null);
    flags.initialize.initialize(new LX());
  }
}
