package com.follybuilders.paradisium.pattern.gerald;

import com.follybuilders.paradisium.ParadisiumCategory;
import com.follybuilders.paradisium.pattern.ParadisiumBasePattern;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.parameter.BoundedParameter;

@LXCategory(ParadisiumCategory.GERALD)
public class Random extends ParadisiumBasePattern {
    BoundedParameter red = new BoundedParameter("Red", 0, 255);
    BoundedParameter green = new BoundedParameter("Red", 0, 255);
    BoundedParameter blue = new BoundedParameter("Red", 0, 255);
    public Random(LX lx) {
        super(lx);
    }

    public void run(double deltaMs) {

    }
}
