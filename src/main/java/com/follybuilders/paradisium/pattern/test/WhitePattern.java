/* (C)2022 */
package com.follybuilders.paradisium.pattern.test;

import com.follybuilders.paradisium.ParadisiumCategory;
import com.follybuilders.paradisium.pattern.ParadisiumBasePattern;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.model.LXModel;

@LXCategory(ParadisiumCategory.TESTPATTERN)
public class WhitePattern extends ParadisiumBasePattern {

  public WhitePattern(LX lx) {
    super(lx);
  }

  public void run(double deltaMs) {
    String tag = "WHITE";
    for (LXModel strip : model.sub(tag)) {
      setColor(strip, 0xffff0000);
    }
  }
}
