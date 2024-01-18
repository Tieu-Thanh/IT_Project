from ultralytics import YOLO
from argparse import ArgumentParser

parser = ArgumentParser(description='Train YOLOv8 with custom dataset')
parser.add_argument('--data', type=str, default=None, help='(Required) data.yaml path')
parser.add_argument('--model', type=str, default='yolov8s.pt', help='model.pt path')
args = parser.parse_args()


data = args.data
model = args.model
model = YOLO(model)

if not data:
    raise ValueError("data yaml is required")


results = model.train(data = data,
                      epochs = 100,
                      optimizer='AdamW',
                      imgsz = 640)
