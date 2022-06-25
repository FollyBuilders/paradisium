
@LXCategory("Fixtures")
public static class RGBPattern extends LXPattern {
  public RGBPattern(LX lx) {
    super(lx);
  }
  public void run(double deltaMs) {
    String tag = "RGB";
    for (LXModel strip : model.sub(tag)) {
      setColor(strip, 0xffffffff);
    }
  }
}


@LXCategory("Fixtures")
public static class WhitePattern extends LXPattern {
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
