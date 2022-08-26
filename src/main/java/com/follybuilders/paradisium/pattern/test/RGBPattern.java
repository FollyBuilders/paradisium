/* (C)2022 */
package com.follybuilders.paradisium.pattern.test;

import com.follybuilders.paradisium.ParadisiumCategory;
import com.follybuilders.paradisium.pattern.ParadisiumBasePattern;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXModel;
import heronarts.lx.parameter.BoundedParameter;

@LXCategory(ParadisiumCategory.RGBWAUVPATTERN)
public class RGBPattern extends ParadisiumBasePattern {

  BoundedParameter red = new BoundedParameter("Red", 0, 255);
  BoundedParameter green = new BoundedParameter("Green", 0, 255);
  BoundedParameter blue = new BoundedParameter("Blue", 0, 255);

  public RGBPattern(LX lx) {
    super(lx);
    addParameter("red", red);
    addParameter("green", green);
    addParameter("blue", blue);
  }

  public void run(double deltaMs) {
    for (LXModel fixture : model.sub("RGB")) {
      setColor(fixture, LXColor.RED);
    
    }
  }
}
