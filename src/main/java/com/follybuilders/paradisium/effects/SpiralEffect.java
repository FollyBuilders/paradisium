/* (C)2022 */
package com.follybuilders.paradisium.effects;

import heronarts.lx.LX;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.CompoundParameter;

public class SpiralEffect extends ParadisiumBaseEffect {
  int[] reorder = {0, 5, 2, 7, 4, 1, 6, 3};

  public final CompoundParameter basePetalParam =
      new CompoundParameter("Base", 0, 0, 8).setDescription("Base petal for spiral");

  public final BooleanParameter leftSpiralParam =
      new BooleanParameter("Left").setDescription("Left handed spiral");

  public final BooleanParameter rightSpiralParam =
      new BooleanParameter("Right").setDescription("Right handed spiral");

  public SpiralEffect(LX lx) {
    super(lx);
  }

  public void run(double deltaMs, double enabledAmount) {
    for (int i = 0; i < model.points.length; i++) {
      LXPoint p = model.points[i];
      if (leftSpiralParam.getValueb() && (i % 8) == reorder[(int) basePetalParam.getValue() % 8]) {
        // leave alone
      } else if (rightSpiralParam.getValueb()
          && (i % 13) == reorder[(int) basePetalParam.getValue() % 8]) {
        // leave alone
      } else {
        colors[p.index] = 0;
      }
    }
  }
}
