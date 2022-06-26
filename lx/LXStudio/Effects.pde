


public class SpiralEffect extends LXEffect {  
  
  public SpiralEffect(LX lx) {
    super(lx);
    this.basePetalParam.setMappable(true);
    this.leftSpiralParam.setMappable(true);
    this.rightSpiralParam.setMappable(true);
    addParameter("base", this.basePetalParam);
    addParameter("left", this.leftSpiralParam);
    addParameter("right", this.rightSpiralParam);
  }
  
  int[] reorder = {0, 5, 2, 7, 4, 1, 6, 3}; 
  
  // This is a parameter with default value 5, range 0-100
  public final CompoundParameter basePetalParam =
    new CompoundParameter("Base", 0, 0, 8)
    .setDescription("Base petal for spiral");

  public final BooleanParameter leftSpiralParam =
    new BooleanParameter("Left")
    .setDescription("Left handed spiral");
    
  public final BooleanParameter rightSpiralParam =
    new BooleanParameter("Right")
    .setDescription("Right handed spiral");

  public void run(double deltaMs, double enabledAmount) {
     for (int i = 0 ; i < model.points.length ; i++) {
        LXPoint p = model.points[i];
        if (leftSpiralParam.getValueb() && (i % 8) == reorder[(int) basePetalParam.getValue() % 8]) {
          //leave alone
        } else if (rightSpiralParam.getValueb() && (i % 13) == reorder[(int) basePetalParam.getValue() % 8])  {
          // leave alone
        } else {
          colors[p.index] = 0;
        }
     }
  }
  
}
