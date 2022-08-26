package com.follybuilders.paradisium.pattern.test;

import com.follybuilders.paradisium.ParadisiumCategory;
import com.follybuilders.paradisium.pattern.ParadisiumBasePattern;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.BooleanParameter;

@LXCategory(ParadisiumCategory.TESTPATTERN)
public class ColorTester extends ParadisiumBasePattern {

    public final BooleanParameter redToggle =
      new BooleanParameter("Red").setDescription("Toggle Red");

    public final BooleanParameter greenToggle =
      new BooleanParameter("Green").setDescription("Toggle Green");

    public final BooleanParameter blueToggle =
        new BooleanParameter("Blue").setDescription("Toggle Red");

    public final BooleanParameter whiteToggle =
      new BooleanParameter("White").setDescription("Toggle White");

    public final BooleanParameter amberToggle =
      new BooleanParameter("Amber").setDescription("Toggle Amber");

    public final BooleanParameter UVToggle =
        new BooleanParameter("UV").setDescription("Toggle UV");

    public ColorTester(LX lx) {
        super(lx);
        addParameter("RedToggle", redToggle);
        addParameter("GrenToggle", greenToggle);
        addParameter("BlueToggle", blueToggle);
        addParameter("WhiteToggle", whiteToggle);
        addParameter("AmberToggle", amberToggle);
        addParameter("UVToggle", UVToggle);
    }

    int red;
    int green;
    int blue;
    int amber;
    int white;
    int uv;

    @Override
    public void run(double deltaMs) {
      red =  redToggle.isOn() ? 255 : 0;
      blue =  blueToggle.isOn() ? 255 : 0;
      green =  greenToggle.isOn() ? 255 : 0;
      white =  whiteToggle.isOn() ? 255 : 0;
      amber =  amberToggle.isOn() ? 255 : 0;
      uv =  UVToggle.isOn() ? 255 : 0;

      for (LXModel fixture: model.sub("RGB")) {
          for (LXPoint p: fixture.points) {
              colors[p.index] = LXColor.rgb(red, green, blue);
          }
      }


      for (LXModel fixture: model.sub("WHITE")) {
        for (LXPoint p: fixture.points) {
            colors[p.index] = LXColor.rgb(white, amber, uv);
        }
    }
    }
    
}
