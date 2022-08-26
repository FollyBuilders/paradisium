from tree import Tree as TreeStructure
from constants import TreeClusters, TreeTypes

from enum import Enum

import csv
import json
import os

tree_map = [
  {"label": "A1", "gx": 1, "gy": 0, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "B2", "gx": 2, "gy": 0, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "E3", "gx": 3, "gy": 0, "tree_type": TreeTypes.TYPE_E, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "A4", "gx": 4, "gy": 0, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "B5", "gx": 4, "gy": 1, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "A6", "gx": 4, "gy": 2, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "D7", "gx": 3, "gy": 2, "tree_type": TreeTypes.TYPE_D, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "A8", "gx": 2, "gy": 2, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "B9", "gx": 1, "gy": 2, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_1},
  {"label": "A10", "gx": 2, "gy": 4, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "B11", "gx": 1, "gy": 4, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "A12", "gx": 0, "gy": 5, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "C13", "gx": 1, "gy": 5, "tree_type": TreeTypes.TYPE_C, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "B14", "gx": 1, "gy": 6, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "A15", "gx": 2, "gy": 7, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "A16", "gx": 3, "gy": 5, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "D17", "gx": 3, "gy": 4, "tree_type": TreeTypes.TYPE_D, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "B18", "gx": 4, "gy": 6, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_2},
  {"label": "A19", "gx": 5, "gy": 4, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "D20", "gx": 5, "gy": 3, "tree_type": TreeTypes.TYPE_D, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "A21", "gx": 6, "gy": 3, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "B22", "gx": 6, "gy": 2, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "B23", "gx": 6, "gy": 5, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "A24", "gx": 7, "gy": 5, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "C25", "gx": 7, "gy": 4, "tree_type": TreeTypes.TYPE_C, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "B26", "gx": 8, "gy": 4, "tree_type": TreeTypes.TYPE_B, "tree_cluster": TreeClusters.CLUSTER_3},
  {"label": "A27", "gx": 8, "gy": 3, "tree_type": TreeTypes.TYPE_A, "tree_cluster": TreeClusters.CLUSTER_3},
]

if __name__ == "__main__":
  all_fixtures = []

  csv_data = []

  i = 0
  DMX_CHANNELS_PER_TREE = 3 * 6 # 3 lights of 6 channels each
  
  for sub_tree in tree_map:
    _tree = TreeStructure(
        label=sub_tree["label"],
        gx=sub_tree["gx"],
        gy=sub_tree["gy"],
        id = 3 * i + 1, # NOTE(G3): Start with 1 instead of 0
        tree_type=sub_tree["tree_type"],
        tree_cluster=sub_tree["tree_cluster"],
        channel_start=DMX_CHANNELS_PER_TREE * i
    )
    all_fixtures.append(_tree._fixtures())
    i += 1

    main_fixtures = []

    # Write fixture files, per each light 3 files
    # - main fixture file that references:
    #   - RGB fixture point file
    #   - WAUV fixture point file
    for fix in all_fixtures:
      for subfix in fix:
        if not subfix["meta"]["fixture"]:
          main_fixtures.append(subfix)
        filename = "./Fixtures/{}.lxf".format(subfix["label"])
        with open(filename, 'w') as fp:
          json.dump(subfix, fp, indent=2)

  # In order to make loading and starting the fixture in the project
  # file "simplier" we can load each main fixture file into a parent
  # fixture file
  main_file = {
    "label": "paradisium",
    "tags": ["paradisium"],
    "parameters": {},
    "components": [
      {"type": _fix["label"], "x":0,"y":0,"z":0}
      for _fix in main_fixtures
    ]
  }

  with open("./Fixtures/paradisium.lxf", 'w') as fp:
    json.dump(main_file, fp, indent=2)

  header = ["tree", "face", "dmx channel"]

  for subfix in main_fixtures:
    csv_data.append([
      subfix["meta"]["tree"],
      subfix["meta"]["face_index"],
      subfix["meta"]["programming_channel"],
    ])
  
  with open('programming.csv', 'w', encoding='UTF8', newline='') as fp:
    writer = csv.writer(fp)
    writer.writerow(header)
    writer.writerows(csv_data)