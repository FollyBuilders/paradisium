/* (C)2020-2022 */
package com.follybuilders.paradisium;

import com.follybuilders.paradisium.effects.ParadisiumBaseEffect;
import com.follybuilders.paradisium.pattern.ParadisiumBasePattern;
import com.follybuilders.paradisium.props.*;
import heronarts.lx.LX;
import heronarts.lx.LXPlugin;
import heronarts.lx.studio.LXStudio;
import java.io.File;
import java.util.ArrayList;
import javax.annotation.Nullable;
import org.reflections.Reflections;
import processing.core.PApplet;

/**
 * This is an example top-level class to build and run an LX Studio application via an IDE. The
 * main() method of this class can be invoked with arguments to either run with a full Processing 3
 * UI or as a headless command-line only engine.
 */
public class ParadisiumApp extends PApplet implements LXPlugin {

  private static final String WINDOW_TITLE = "Paradisium";

  Reflections reflections = new Reflections("com.follybuilders");

  private static int WIDTH = 1280;
  private static int HEIGHT = 800;
  private static boolean FULLSCREEN = false;

  private static int WINDOW_X = 0;
  private static int WINDOW_Y = 0;

  private static boolean HAS_WINDOW_POSITION = false;

  private ArrayList<File> objFiles = null;
  VisibilityParameterHelper visibilityHelper = null;

  @Override
  public void settings() {
    if (FULLSCREEN) {
      fullScreen(PApplet.P3D);
    } else {
      size(WIDTH, HEIGHT, PApplet.P3D);
    }
    pixelDensity(displayDensity());
  }

  @Override
  public void setup() {
    LXStudio.Flags flags = new LXStudio.Flags(this);
    flags.resizable = true;
    flags.useGLPointCloud = false;
    flags.startMultiThreaded = true;

    new LXStudio(this, flags);
    this.surface.setTitle(WINDOW_TITLE);
    if (!FULLSCREEN && HAS_WINDOW_POSITION) {
      this.surface.setLocation(WINDOW_X, WINDOW_Y);
    }
  }

  final String ARTNET_IP = "192.168.2.25";

  @Override
  public void initialize(LX lx) {
    // Here is where you should register any custom components or make modifications
    // to the LX engine or hierarchy. This is also used in headless mode, so note that
    // you cannot assume you are working with an LXStudio class or that any UI will be
    // available.

    // NOTE(G3): Magic use of reflections to load all effects and patterns ;)
    reflections.getSubTypesOf(ParadisiumBaseEffect.class).forEach(lx.registry::addEffect);
    reflections.getSubTypesOf(ParadisiumBasePattern.class).forEach(lx.registry::addPattern);

    File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "OBJs");
    if (dataDirectory == null) { // TODO: THIS DOES NOT WORK AS EXPECTED WHEN data DOES NOT EXIST
      LX.error(" directory does not exist in root director");
      objFiles = new ArrayList<>(); // create empty lists to avoid null pointer later
    } else {
      objFiles = getFilesWithExtension(dataDirectory, ".obj");
    }
    visibilityHelper = new VisibilityParameterHelper();
    visibilityHelper.addFoundFiles(objFiles);
    lx.engine.registerComponent("visibilityHelper", visibilityHelper);
  }

  public void initializeUI(LXStudio lx, LXStudio.UI ui) {
    // Here is where you may modify the initial settings of the UI before it is fully
    // built. Note that this will not be called in headless mode. Anything required
    // for headless mode should go in the raw initialize method above.
  }

  public void onUIReady(LXStudio lx, LXStudio.UI ui) {
    // At this point, the LX Studio application UI has been built. You may now add
    // additional views and components to the UI hierarchy.
    UIPropList pl =
        new UIPropList(lx, ui, ui.leftPane.global.getWidth(), objFiles, visibilityHelper);
    pl.addToContainer(ui.leftPane.global);
  }

  @Override
  public void draw() {
    // All handled by core LX engine, do not modify, method exists only so that Processing
    // will run a draw-loop.
  }

  /**
   * Main interface into the program. Two modes are supported, if the --headless flag is supplied
   * then a raw CLI version of LX is used. If not, then we embed in a Processing 4 applet and run as
   * such.
   *
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    LX.log("Initializing LX version " + LXStudio.VERSION);
    boolean headless = false;
    File projectFile = null;
    for (int i = 0; i < args.length; ++i) {
      if ("--help".equals(args[i])) {
      } else if ("--headless".equals(args[i])) {
        headless = true;
      } else if ("--fullscreen".equals(args[i]) || "-f".equals(args[i])) {
        FULLSCREEN = true;
      } else if ("--width".equals(args[i]) || "-w".equals(args[i])) {
        try {
          WIDTH = Integer.parseInt(args[++i]);
        } catch (Exception x) {
          LX.error("Width command-line argument must be followed by integer");
        }
      } else if ("--height".equals(args[i]) || "-h".equals(args[i])) {
        try {
          HEIGHT = Integer.parseInt(args[++i]);
        } catch (Exception x) {
          LX.error("Height command-line argument must be followed by integer");
        }
      } else if ("--windowx".equals(args[i]) || "-x".equals(args[i])) {
        try {
          WINDOW_X = Integer.parseInt(args[++i]);
          HAS_WINDOW_POSITION = true;
        } catch (Exception x) {
          LX.error("Window X command-line argument must be followed by integer");
        }
      } else if ("--windowy".equals(args[i]) || "-y".equals(args[i])) {
        try {
          WINDOW_Y = Integer.parseInt(args[++i]);
          HAS_WINDOW_POSITION = true;
        } catch (Exception x) {
          LX.error("Window Y command-line argument must be followed by integer");
        }
      } else if (args[i].endsWith(".lxp")) {
        try {
          projectFile = new File(args[i]);
        } catch (Exception x) {
          LX.error(x, "Command-line project file path invalid: " + args[i]);
        }
      }
    }
    if (headless) {
      // We're not actually going to run this as a PApplet, but we need to explicitly
      // construct and set the initialize callback so that any custom components
      // will be run
      headlessInit(projectFile);
    } else {
      PApplet.main("com.follybuilders.paradisium.ParadisiumApp", args);
    }
  }

  public static LX.Flags headlessInit(@Nullable File projectFile) {
    LX.Flags flags = new LX.Flags();
    flags.initialize = new ParadisiumApp();

    if (projectFile == null) {
      LX.log("WARNING: No project filename was specified for headless mode!");
    }
    LX.headless(flags, projectFile);

    return flags;
  }

  private ArrayList<File> getFilesWithExtension(File file, String suffix) {
    if (file.isDirectory()) {
      String names[] = file.list();
      ArrayList<File> files = new ArrayList<File>();
      for (int i = 0; i < names.length; i++) {
        if (names[i].endsWith(suffix.toLowerCase()) || names[i].endsWith(suffix.toUpperCase())) {
          files.add(new File((file.getAbsolutePath() + File.separator + names[i])));
        }
      }
      return files;
    } else {
      LX.error("path passed into getFilesWithExtension is not a directory");
      return null;
    }
  }
}
