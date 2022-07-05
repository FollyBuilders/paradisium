/* (C)2022 */
package com.follybuilders.paradisium.effects;

import heronarts.lx.LX;
import heronarts.lx.effect.LXEffect;

public abstract class ParadisiumBaseEffect extends LXEffect {
  public ParadisiumBaseEffect(LX lx) {
    super(lx);
  }

  @Override
  public void loop(double deltaMs) {
    if (isEnabled()) {
      super.loop(deltaMs);
    }
  }
}
