
// import peasy.*;
// PeasyCam cam;

final static float HEXGRID_RADIUS = 90; // inches 
final static float FIXTURE_RADIUS = 36; // inches

ArrayList<Tree3D> trees = new ArrayList<Tree3D>();

final static int SIZE_X = 9;
final static int SIZE_Y = 8;

void setup() {
  //cam = new PeasyCam(this, 400); 
  size(840,840,P3D);
  textFont(createFont("Zurich Extra Black Bttf", 32));
  defineHexGrid();
  dumpCSV();  
  dumpPointFile("_RGB",0);
  dumpMainFile();
  dumpFixtureFiles();
}

void draw() {
  background(0);
  pushMatrix();
  translate(HEXGRID_RADIUS,HEXGRID_RADIUS);
  scale(0.5);
  // draw origin
  //fill(255,0,0);
  //ellipse(0,0,1,1);
  // draw everything
  drawTreeHexagons();
  drawTreeFixtures();
  drawTreeLabels();
  popMatrix();
}

void defineHexGrid() {
  // These are created in an order that was based on the hex grid coordinate system
  // This should make it easy to adapt to any last minute layout changes
  trees.add(new Tree3D("A1",1,0,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_1));  
  trees.add(new Tree3D("B2",2,0,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_1));  
  trees.add(new Tree3D("E3",3,0,TreeTypeEnum.TYPE_E,TreeClusterEnum.CLUSTER_1));    
  trees.add(new Tree3D("A4",4,0,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_1));    
  trees.add(new Tree3D("B5",4,1,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_1));      
  trees.add(new Tree3D("A6",4,2,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_1));      
  trees.add(new Tree3D("D7",3,2,TreeTypeEnum.TYPE_D,TreeClusterEnum.CLUSTER_1));      
  trees.add(new Tree3D("A8",2,2,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_1));      
  trees.add(new Tree3D("B9",1,2,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_1));      
  trees.add(new Tree3D("A10",2,4,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("B11",1,4,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("A12",0,5,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("C13",1,5,TreeTypeEnum.TYPE_C,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("B14",1,6,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("A15",2,7,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("A16",3,5,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("D17",3,4,TreeTypeEnum.TYPE_D,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("B18",4,6,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_2));      
  trees.add(new Tree3D("A19",5,4,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("D20",5,3,TreeTypeEnum.TYPE_D,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("A21",6,3,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("B22",6,2,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("B23",6,5,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("A24",7,5,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("C25",7,4,TreeTypeEnum.TYPE_C,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("B26",8,4,TreeTypeEnum.TYPE_B,TreeClusterEnum.CLUSTER_3));      
  trees.add(new Tree3D("A27",8,3,TreeTypeEnum.TYPE_A,TreeClusterEnum.CLUSTER_3));      
}

void drawTreeHexagons() {
  for (Tree3D t : trees) {
    stroke(255);
    strokeWeight(2);
    fill(0);
    beginShape();
    for (int j = 0 ; j < 6 ; j++) {
      vertex(t.centerX + (cos(radians(j*60)) * HEXGRID_RADIUS),t.centerY + (sin(radians(j*60)) * HEXGRID_RADIUS),0);     
    }
    endShape(CLOSE);        
  }
}

void dumpCSV() {
  java.io.PrintWriter out1 = createWriter("paradisium.csv");
  for (int ti = 0 ; ti < trees.size() ; ti++) {
    Tree3D t = trees.get(ti);
    for (int fi = 0 ; fi < t.fixtures.size() ; fi++) {
      Fixture3D f = t.fixtures.get(fi);
      f.dmx = 1 + (ti * 18) + (fi * 6);
      out1.write("F." + t.id + "." + f.position + "," + f.dmx + "," + f.x + "," + f.y + "," + f.z + "\n");
    }
  }
  out1.close();
}

void dumpMainFile() {
  java.io.PrintWriter out1 = createWriter("PV1.lxf");
  out1.write("{\n");
  out1.write("\"label\": \"PV1\",\n");
  out1.write("\"tags\": [ \"PV1\" ],\n");
  out1.write("\"parameters\": {},\n");
  out1.write("\"components\": [ \n");
  int totalFixtures = trees.size() * 3;
  int fixtureCount = 0;
  for (Tree3D t : trees) {
   for (Fixture3D f : t.fixtures) {  
    out1.write("{ \"type\": \"" + "PV1_" + String.format("%02d", fixtureCount + 1) + "_" + f.id() + "_RGB" + "\", \"x\": 0, \"y\": 0, \"z\": 0 }  ");
    out1.write(",\n"); 
    out1.write("{ \"type\": \"" + "PV1_" + String.format("%02d", fixtureCount + 1) + "_" + f.id() + "_WHITE" + "\", \"x\": 0, \"y\": 0, \"z\": 0 }  ");
    if (fixtureCount != totalFixtures - 1) {
       out1.write(",\n"); 
    }
    fixtureCount++;
   } 
  }
  out1.write("\n],\n");
  out1.write("\"meta\": {\"key1\": \"val2\", \"key3\": \"val4\"}\n");
  out1.write("}\n");
  out1.close();
}

void dumpFixtureFiles() {
  int fixtureCount = 0;
  for (Tree3D t : trees) {
    for (Fixture3D f : t.fixtures) {  
      dumpFixture(f,"RGB",0,fixtureCount);
      dumpFixture(f,"WHITE",3,fixtureCount);
      fixtureCount++;
    }
  }
}

String ARTNET_HOST = "192.168.2.25";

void dumpFixture(Fixture3D f,String suffix,int offset,int fixtureCount) {
  java.io.PrintWriter out1 = createWriter("PV1_" + String.format("%02d", fixtureCount + 1) + "_" + f.id() + "_" + suffix +".lxf");
  out1.write("{\n");
  out1.write("\"label\": \"" + f.id()  + "_" + suffix + "\",\n");  
  out1.write("\"tags\": [");
  out1.write("\"" + suffix +  "\"");
  out1.write("],\n");
  out1.write("\"parameters\": {},\n");
  out1.write("\"components\": [ \n");    
  out1.write("{ \"type\": \"strip\", \"x\": " + (f.x + offset) + " , \"y\": " + (f.y) + ", \"z\": " + f.z + ", \"numPoints\": " + 1 + ", \"spacing\": " + 1 + " } \n");
  out1.write("\n],\n");
  out1.write("\"outputs\": [{\"protocol\": \"artnet\", \"universe\": " + 0 + ", \"host\": \"192.168.2.25\", \"channel\": " + (f.dmx - 1 + offset)   + ", \"num\": " + 1 + "}],\n");
  out1.write("\"meta\": {\"key1\": \"val2\", \"key3\": \"val4\"}\n");
  out1.write("}\n"); 
  out1.close();  
}


void dumpPointFile(String suffix, int offset) {
  java.io.PrintWriter out1 = createWriter("Paradisium_1_Points" + suffix + ".lxf");
  out1.write("{\n");
  out1.write("\"label\": \"Paradisium_1_Points" + suffix + "\",\n");
  out1.write("\"tags\": [ \"Paradisium_1_Points" + suffix + "\" ],\n");
  out1.write("\"parameters\": {},\n");
  out1.write("\"components\": [ \n");  
  int totalFixtures = trees.size() * 3;
  int fixtureCount = 0;
  for (Tree3D t : trees) {
    for (Fixture3D f : t.fixtures) {  
      out1.write("{ \"type\": \"points\", \"coords\": [ { \"x\":" + f.x + ", \"y\": " + (-1.0 * f.y) + ", \"z\": " + f.z + " } ] }");
      if (fixtureCount != totalFixtures - 1) {
         out1.write(",\n");
      }
      fixtureCount++;
    }    
  }
  out1.write("\n],\n");  
  out1.write("\"outputs\": [{\"protocol\": \"artnet\", \"universe\": " + 0 + ", \"host\": \"127.0.0.1\", \"start\": " + (0+offset) + ", \"num\": " + fixtureCount  + ", \"stride\": " + 1 + "}],\n");
  out1.write("\"meta\": {\"key1\": \"val2\", \"key3\": \"val4\"}\n");
  out1.write("}\n");
  out1.close();
}

void drawTreeLabels() {
  textAlign(CENTER);
  textSize(64);
  for (Tree3D t : trees) {
    switch(t.type) {
      case TYPE_A: fill(0,0,255); break;
      case TYPE_B: fill(255,0,0); break;
      case TYPE_C: fill(255,0,255); break;
      case TYPE_D: fill(255,100,0); break;
      case TYPE_E: fill(0,255,0); break;
    }    
    text("" + t.id,t.centerX,t.centerY + 18);
  }
}

void drawTreeFixtures() {
  for (Tree3D t : trees) {
    int fixtureCount = 0;
    for (Fixture3D f : t.fixtures) {
      stroke(255);
      strokeWeight(1);
      fill(0);
      ellipse(f.x,f.y,HEXGRID_RADIUS * 0.3,HEXGRID_RADIUS * 0.3); 
      fill(255);
      textSize(24);
      text(""+f.position,f.x,f.y+8);
      fixtureCount++;
    }
  }
}

class Fixture3D {
  public float x = 0.0;
  public float y = 0.0;
  public float z = 0.0;  
  public int position = 0;
  public Tree3D tree;
  public int dmx = 0;
  public Fixture3D (Tree3D t, float nx, float ny, float nz, int np) {
     tree = t;
     x = nx;
     y = ny;
     z = nz;
     position = np; 
  }
  public String id() {
    return "F." + tree.id + "." + this.position;
  }
}

class Tree3D {
  public int gridX = 0;
  public int gridY = 0;
  public float centerX = 0.0;
  public float centerY = 0.0;
  public float fr = FIXTURE_RADIUS;
  public TreeTypeEnum type;
  public TreeClusterEnum cluster;
  public String id;
  ArrayList<Fixture3D> fixtures = new ArrayList<Fixture3D>();
  private final float hexW = 2.0 * HEXGRID_RADIUS;
  private final float hexH = sqrt(3.0) * HEXGRID_RADIUS; 
  public Tree3D(String sid, int gx, int gy, TreeTypeEnum tt, TreeClusterEnum tc) {
    gridX = gx;
    gridY = gy;
    centerX = (0.5 * HEXGRID_RADIUS) + (gridX * 0.75 * hexW);
    centerY = (((0.5 * ((gridX % 2) + 1)) * hexH) + (gridY * hexH)); 
    id = sid;
    type = tt;
    float fz = 0.0;
    fr = FIXTURE_RADIUS; // maybe look this up based on tree type?
    switch(type) {
      case TYPE_A:
        fz = 12 * 12; fr = 26 / 2; break;
      case TYPE_B:
        fz = 16 * 12; fr = 26 / 2; break;
      case TYPE_C:
        fz = 21 * 12; fr = 26 / 2; break;
      case TYPE_D:
        fz = 23 * 12; fr = 50 / 2; break;
      case TYPE_E:
        fz = 23 * 12; fr = 50 / 2; break;
    }
    cluster = tc;
    for (int i = 0 ; i < 3 ; i++) {
      float fx = centerX + (cos(radians(-30 - i * -120)) * fr);
      float fy = centerY + (sin(radians(-30 - i * -120)) * fr);
      fixtures.add(new Fixture3D(this,fx,fy,fz,2+i*2));
    }
  }
}

enum TreeTypeEnum {
  TYPE_A,
  TYPE_B,
  TYPE_C,
  TYPE_D,
  TYPE_E;
}

enum TreeClusterEnum {
  CLUSTER_1,
  CLUSTER_2,
  CLUSTER_3;
}

//// OLD STUFF

//void drawBackground() {
//  // BASED ON "ODD-Q" LAYOUT
//  for (int j = 0 ; j < SIZE_Y ; j++) {
//    for (int i = 0 ; i < SIZE_X ; i++) {
//      if (hexGrid[i][j] > 0) {
//        drawBackgroundHex(i,j);
//      }
//    }
//  }
//}

//void drawBackgroundHex(int x, int y) {
//  if (x >= SIZE_X || y >= SIZE_Y) {
//     return; 
//  }
//  float hexW = 2.0 * HEXGRID_RADIUS;
//  float hexH = sqrt(3.0) * HEXGRID_RADIUS;
//  float centerPointX = (0.5 * HEXGRID_RADIUS) + (x * 0.75 * hexW);
//  float centerPointY = 0.0;
//  if (x % 2 == 0) {
//    centerPointY = ((0.5 * hexH) + (y * hexH));  
//  } else {
//    centerPointY = ((1.0 * hexH) + (y * hexH));
//  }
//  // draw tree type
//  strokeWeight(4); 
//  if (hexGrid[x][y] == 1) {
//    stroke(0,0,255);
//  } else if (hexGrid[x][y] == 2) {
//    stroke(255,0,0);
//  } else if (hexGrid[x][y] == 3) {
//    stroke(255,0,255);
//  } else if (hexGrid[x][y] == 4) {
//    stroke(255,100,0);
//  } else if (hexGrid[x][y] == 5) {
//    stroke(0,255,0);  
//  } else {
//    stroke(255);
//  }
//  //ellipse(centerPointX,centerPointY,HEXGRID_RADIUS*0.25,HEXGRID_RADIUS*0.25);
  
//  // draw light positions
//  //stroke(255);
//  //strokeWeight(4);
//  //int i = 0;
//  //stroke(255,0,0);
//  //ellipse(centerPointX + (cos(radians(-30 - i * -120)) * FIXTURE_RADIUS),centerPointY + (sin(radians(-30 - i * -120)) * FIXTURE_RADIUS),HEXGRID_RADIUS * 0.1,HEXGRID_RADIUS * 0.1); 
//  //i = 1; 
//  //stroke(0,255,0);
//  //ellipse(centerPointX + (cos(radians(-30 - i * -120)) * FIXTURE_RADIUS),centerPointY + (sin(radians(-30 - i * -120)) * FIXTURE_RADIUS),HEXGRID_RADIUS * 0.1,HEXGRID_RADIUS * 0.1); 
//  //i = 2;
//  //stroke(0,0,255);
//  //ellipse(centerPointX + (cos(radians(-30 - i * -120)) * FIXTURE_RADIUS),centerPointY + (sin(radians(-30 - i * -120)) * FIXTURE_RADIUS),HEXGRID_RADIUS * 0.1,HEXGRID_RADIUS * 0.1); 
//  // draw background hex
//  stroke(255);
//  strokeWeight(2);
//  fill(0);
//  beginShape();
//  for (int j = 0 ; j < 6 ; j++) {
//    vertex(centerPointX + (cos(radians(j*60)) * HEXGRID_RADIUS),centerPointY + (sin(radians(j*60)) * HEXGRID_RADIUS),0);     
//  }
//  endShape(CLOSE);    
//}
