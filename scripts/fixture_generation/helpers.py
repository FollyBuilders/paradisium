ARTNET_HOST = "192.168.2.51"

def point_component_dict(x, y, z):
    return {
        "type": "points",
        "coords": [{
            "x": x,
            "y": y,
            "z": z
        }]
    }

def strip_component_dict(x, y, z):
    return {
        "type": "strip",
        "x": x,
        "y": y,
        "z": z,
        "numPoints": 1,
        "spacing": 1
    }


def artnet_output_dict(channel, universe=0, num=1, host=ARTNET_HOST):
    return {
        "protocol": "artnet",
        "universe": universe,
        "host": host,
        "channel": channel,
        "num": num
    }


def fixture_dict(
    label,
    tags=[],
    parameters={},
    components=[],
    outputs=[],
    meta={}):
    return {
        "label": label,
        "tags": tags,
        "parameters": parameters,
        "components": components,
        "outputs": outputs,
        "meta": meta
    }
