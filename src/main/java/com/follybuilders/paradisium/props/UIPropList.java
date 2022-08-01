/* (C)2022 */
package com.follybuilders.paradisium.props;

import heronarts.lx.LX;
import heronarts.lx.studio.LXStudio;
import heronarts.p4lx.P4LX;
import heronarts.p4lx.ui.component.UIButton;
import heronarts.p4lx.ui.component.UICollapsibleSection;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UIPropList extends UICollapsibleSection {

  List<UIProp> props = new ArrayList<UIProp>();

  public UIPropList(
      P4LX lx, LXStudio.UI ui, float w, List<File> objs, VisibilityParameterHelper vh) {
    super(ui, 0, 0, w, 32);
    setTitle("OBJ PROPS");
    if (objs != null) {
      for (int i = 0; i < objs.size(); i++) {
        File f = objs.get(i);
        LX.log("Adding prop for file: " + f.getName());
        UIProp p = new UIProp(lx, f);
        props.add(p);
        ui.preview.addComponent(p);
        new UIButton(0, i * 18, w - 8, 16)
            .setParameter(p.visible)
            .setLabel(p.name())
            .setActiveColor(ui.theme.getSecondaryColor())
            .addToContainer(this);
        vh.attachParameterForFilename(f.getName(), p.visible);
      }

      setContentHeight(18 * objs.size());
    }
  }
}
