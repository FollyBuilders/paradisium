/* (C)2022 */
package com.follybuilders.paradisium.pattern;

import com.follybuilders.paradisium.ParadisiumCategory;
import com.follybuilders.paradisium.pattern.ParadisiumBasePattern;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXModel;
import heronarts.lx.modulator.SinLFO;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.DiscreteParameter;

@LXCategory(ParadisiumCategory.TESTPATTERN)
public class TreeTesterPattern extends ParadisiumBasePattern {

    public TreeTesterPattern(LX lx) {
        super(lx);
        sinLfo = new SinLFO(0.0,1.0,2000.0);
        sinLfo.start();
        this.addModulator(sinLfo);
        addParameter("face2", this.face2Param);
        addParameter("face4", this.face4Param);
        addParameter("face6", this.face6Param);
        addParameter("tree", this.tree);
    }

    private SinLFO sinLfo;

    public final BooleanParameter face2Param =
            new BooleanParameter("Face 2",true)
                    .setDescription("Enables the fixture on tree face 2");

    public final BooleanParameter face4Param =
            new BooleanParameter("Face 4",true)
                    .setDescription("Enables the fixture on tree face 4");

    public final BooleanParameter face6Param =
            new BooleanParameter("Face 6",true)
                    .setDescription("Enables the fixture on tree face 6");

    public final DiscreteParameter tree = new DiscreteParameter("Tree #", 1, 1, 28)
            .setDescription("Number of the tree to test");

    public void run(double deltaMs) {
        String tag = "T." + tree.getValuei();
        for (LXModel point : model.sub("RGB")) {
            setColor(point,0x00000000);
        }
        for (LXModel point : model.sub(tag+".2.RGB")) {
            if (face2Param.getValueb()) {
                setColor(point, LXColor.rgb((int) (sinLfo.getValue() * 255.0),0,0));
//              setColor(point,0xffffffff);
            } else {
                setColor(point,0x00000000);
            }
        }
        for (LXModel point : model.sub(tag+".4.RGB")) {
           if (face4Param.getValueb()) {
                setColor(point, LXColor.rgb((int) (sinLfo.getValue() * 255.0),0,0));
//                setColor(point,0xffffffff);
           } else {
                setColor(point,0x00000000);
           }
        }
        for (LXModel point : model.sub(tag+".6.RGB")) {
            if (face6Param.getValueb()) {
                setColor(point, LXColor.rgb((int) (sinLfo.getValue() * 255.0),0,0));
//                setColor(point,0xffffffff);
            } else {
                setColor(point,0x00000000);
            }
        }
    }
}
