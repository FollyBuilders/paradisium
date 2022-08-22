"""
Each fixture has 6 DMX channels
1 - red
2 - green
3 - blue
4 - white
5 - amber
6 - ultraviolete

LX Has no notion of WAUV, so we need to overload this by placing two
point fixtures on top of one another, then creating a meta fixture that
references these two point fixtures.

Each fixture will have 3 channels
RGB  -> 1,2,3 | tags=rgb
WAUV -> 4,5,6 | tags=wauv
"""

import helpers

class ShedFixture:
    def __init__(self, x, y, z, id, channel_start, label, tags=[], universe=0, host=helpers.ARTNET_HOST, meta={}):
        self.channel_start = channel_start
        self.universe = universe
        self.host = host
        self.tags = tags
        self.meta = meta
        self.label = "{}_{}".format(id, label)

        self.x = x
        self.y = y
        self.z = z

        self.start_rgb = channel_start
        self.start_wauv = channel_start + 3
    
    def _output(self, channel):
        return helpers.artnet_output_dict(
            universe=self.universe,
            host=self.host,
            channel=channel
        )
    
    def _fixture(self, channel_start, tags, label):
        return helpers.fixture_dict(
            label=label,
            tags=tags,
            meta={
                "fixture": False
            },
            components=[
                helpers.point_component_dict(
                    x=self.x,
                    y=self.y,
                    z=self.z
                )
            ],
            outputs=[self._output(channel=channel_start)]
        )
    
    def rgb_fixture(self):
        kind = "RGB"
        tags = self.tags + [kind]

        return self._fixture(
            label = "{}_{}".format(self.label, kind),
            channel_start=self.start_rgb,
            tags=tags,
        )
    
    def wauv_fixture(self):
        kind = "WAUV"
        tags = self.tags + [kind]
        
        return self._fixture(
            label = "{}_{}".format(self.label, kind),
            channel_start=self.start_wauv,
            tags=tags,
        )
    
    def meta_fixture(self ):
        _meta = {
            "programming_channel": self.channel_start,
            "fixture": True
        }
        return {
            "label": self.label,
            "tags": self.tags,
            "meta": {**self.meta, **_meta},
            "parameters": {},
            "components": [
                {
                    "type": _component["label"],
                    "x": 0,
                    "y": 0,
                    "z": 0
                }
                for _component in [
                    self.rgb_fixture(),
                    self.wauv_fixture()
                ]
            ]
        }
    
    def fixtures(self):
        return [
                self.meta_fixture(),
                self.rgb_fixture(),
                self.wauv_fixture()
        ]
