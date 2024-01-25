from ultralytics import YOLO
import argparse
import cv2
import numpy as np
import torch
from ultralytics.utils.plotting import Annotator, colors
names = ['zebra', 'crocodile']

def make_image_wbbox(image, preds, hide_conf=False, hide_labels=False):
    print("\nCombining Image and Labels...")
    im0 = np.array(image)
    imgsz = im0.shape[:2] # h w 
    h, w = imgsz

    line_thickness = 1 * int(imgsz[0] / 640)

    # Process predictions, plot onto image
    for i, bbox in enumerate(preds):
        xyxy, conf, c = bbox[:4], float(bbox[4]), int(float(bbox[5]))

        annotator = Annotator(im0, line_width=line_thickness)
        label=None
        label = (
            None
            if hide_labels
            else (names[c] if hide_conf else f"{names[c]} {conf:.2f}")
        )
        # print(' xyxy,  conf, cls:', xyxy, conf, c )
        annotator.box_label(xyxy, label, color=colors(c, True))

    img_with_bboxes = annotator.result()
    return img_with_bboxes


parser = argparse.ArgumentParser(description='Load YOLO model, then predict')
parser.add_argument('--model', type=str, default='yolov8s.pt', help='model.pt path')
parser.add_argument('--img', type=str, default=None, help='Path to image')
args = parser.parse_args()

img_path = args.img
img = cv2.imread(img_path)
model_path = args.model
trained_model = YOLO(model_path)


results = trained_model.predict(img_path)
bboxes = results[0].boxes.data

img_wbbox = make_image_wbbox(img, bboxes)

