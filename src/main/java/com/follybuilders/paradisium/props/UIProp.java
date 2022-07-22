/* (C)2022 */
package com.follybuilders.paradisium.props;

import heronarts.p4lx.P4LX;
import heronarts.p4lx.ui.UI;
import heronarts.p4lx.ui.component.UIPShape;
import java.io.File;
import processing.core.PGraphics;

public class UIProp extends UIPShape {

  private float tx = 45.0f;
  private float ty = -78.0f;
  private float tz = 0.0f;

  private float rx = 0; // (float) Math.PI / -2.0f;
  private float ry = 0; // (float) Math.PI;
  private float rz = 0;

  // THIS ISN'T NECESSARY BECAUSE visible IS INHERITED FROM UI3dComponent
  //    public final BooleanParameter propVisible =
  //            new BooleanParameter("Visible", true)
  //                    .setDescription("Whether prop is visible in the simulation");

  private File file;

  public UIProp(P4LX lx, File f) {
    super(lx, f.getAbsolutePath());
    file = f;
  }

  public String name() {
    return file.getName();
  }

  public void onDraw(UI ui, PGraphics pg) {
    pg.pushMatrix();
//    pg.scale(sc);
    pg.scale(-1.0f,1.0f,1.0f);
    pg.translate(tx, ty, tz);
    pg.rotateX(rx);
    pg.rotateY(ry);
    pg.rotateZ(rz);
    shape.setFill(0x44888888);
    shape.setStroke(0x88ffffff);
    shape.setStrokeWeight(0.3f);
    super.onDraw(ui, pg);
    pg.popMatrix();
  }

  // THIS ISN'T NECESSARY BECAUSE visible == false WILL DISABLE LOOP TASKS LIKE DRAW
  //    public void onDraw(UI ui, PGraphics pg) {
  //        if (visible.getValueb()) {
  //            super.onDraw(ui,pg);
  //        }
  //    }

}
