import math

from constants import TreeTypes
from sheds_fixture import ShedFixture

HEXGRID_RADIUS_IN = 90

class Tree:
    def __init__(self, label, channel_start, gx, gy, tree_type, tree_cluster):
        self.grid_x = gx
        self.grid_y = gy
        self.label = label
        self.channel_start = channel_start
        self.tree_type = tree_type
        self.tree_cluster = tree_cluster

        self.hexgrid_w = 2.0  * HEXGRID_RADIUS_IN
        self.hexgrid_h = math.sqrt(3.0) * HEXGRID_RADIUS_IN

        self.center_x, self.center_y = self.set_center()
    
    def set_center(self):
        center_x = (0.5 * HEXGRID_RADIUS_IN) + (0.75 * self.grid_x * self.hexgrid_w)
        center_y = ((0.5 * ((self.grid_x % 2) + 1)) * self.hexgrid_h) + (self.grid_y * self.hexgrid_h)
        return center_x, center_y
    
    def get_fixture_params(self):
        if self.tree_type == TreeTypes.TYPE_A:
            fr = 26 / 2
            fz = 12 * 12
            return fr, fz
        elif self.tree_type == TreeTypes.TYPE_B:
            fr = 26 / 2
            fz = 16 * 12
            return fr, fz
        elif self.tree_type == TreeTypes.TYPE_C:
            fr = 26 / 2
            fz = 21 * 12
            return fr, fz
        elif self.tree_type == TreeTypes.TYPE_D:
            fr = 50 / 2
            fz = 23 * 12
            return fr, fz
        elif self.tree_type == TreeTypes.TYPE_E:
            fr = 50 / 2
            fz = 23 * 12
            return fr, fz

    
    def _fixtures(self):
        fixtures = []
        dmx = 0
        fr, fz = self.get_fixture_params()
        for i in range(3):
            fx = self.center_x + (math.cos(math.radians(-30 - i * -120)) * fr)
            fy = self.center_y + (math.sin(math.radians(-30 - i * -120)) *fz)
            fz = fz
            print(self.label, self.hexgrid_w, self.hexgrid_h, fx, fy)
            fix = ShedFixture(
                label="{}_{}".format(self.label, i),
                x=fx,
                y=fy,
                z=fz,
                channel_start=1+i*6+i*self.channel_start,
                tags = [self.tree_type.value]
            )
            dmx += 1
            fixtures.append(fix.fixtures())
        
        flat_fixtures = []
        for subfix in fixtures:
            for fix in subfix:
                flat_fixtures.append(fix)
        
        return flat_fixtures
