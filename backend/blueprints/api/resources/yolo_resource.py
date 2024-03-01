from flask import request
from flask_restful import Resource, reqparse
from blueprints.detection.Temp import Temp  # example import

# from blueprints.detection.yolo import Model_YOLO


class YoloResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        # json parameters
        # self.parser.add_argument('')

    def post(self):
        args = self.parser.parse_args()
        # get parameters

        # data-handling logic
        # example call
        temp = Temp()
        foo = temp.getTemp()

        # return
        return {'message': 'Hello', 'data': foo}, 201
