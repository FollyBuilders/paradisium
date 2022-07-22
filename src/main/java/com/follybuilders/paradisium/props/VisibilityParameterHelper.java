/* (C)2022 */
package com.follybuilders.paradisium.props;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import heronarts.lx.LX;
import heronarts.lx.LXComponent;
import heronarts.lx.LXSerializable;
import heronarts.lx.parameter.BooleanParameter;
import java.io.File;
import java.util.*;

public class VisibilityParameterHelper extends LXComponent implements LXSerializable {

  ArrayList<File> filenames = new ArrayList<File>();
  HashMap<String, BooleanParameter> parameters = new HashMap<String, BooleanParameter>();

  public void addFoundFiles(List<File> f) {
    filenames.addAll(f);
  }

  public void attachParameterForFilename(String fn, BooleanParameter vp) {
    parameters.put(fn, vp);
  }

  public void load(LX lx, JsonObject j) {
    // LX.log("INSIDE VISIBILITY HELPER LOAD(): " + j.toString());
    for (Map.Entry<String, JsonElement> entry : j.entrySet()) {
      String k = entry.getKey();
      BooleanParameter b = parameters.get(k);
      if (b != null) {
        b.setValue(entry.getValue().getAsBoolean());
      }
    }
  }

  public void save(LX lx, JsonObject j) {
    for (Map.Entry<String, BooleanParameter> entry : parameters.entrySet()) {
      j.addProperty(entry.getKey(), entry.getValue().getValueb());
    }
    // LX.log("INSIDE VISIBILITY HELPER SAVE() " + j.toString());
  }
}
