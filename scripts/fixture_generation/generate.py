import tree
from constants import TreeTypes
from pprint import pprint
from enum import Enum



if __name__ == "__main__":
    t1 = tree.Tree(
        label="A1",
        gx=1,
        gy=0,
        tree_type=TreeTypes.TYPE_A,
        tree_cluster="b",
        channel_start=0)
    t1._fixtures()