
import cv2

import sys
import os
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)
from utils import draw_bbox

imgpath = f'{HOME}/data_sample_labeled/train/images/im3.jpg'
lblpath = f'{HOME}/data_sample_labeled/train/labels/im3.txt'

classes = ['banana', 'orange']
img = cv2.imread(imgpath)
with open(lblpath, 'r') as f:
    bboxes = [bbox.split() for bbox in f.read().split('\n')]

# convert bbox from [xcen, ycen, wbox, hbox] to xyxy
bboxes_new = []
imgsz = img.shape[:2] # h w 
h, w = imgsz
for bbox in bboxes:
    if len(bbox) == 0:
        continue
    c, center_x, center_y, boxw, boxh = bbox
    xmin = (float(center_x) - float(boxw)/2 ) * w
    xmax = (float(center_x) + float(boxw)/2 )* w
    ymin = (float(center_y) - float(boxh)/2)* h
    ymax = (float(center_y) + float(boxh)/2)* h
    bbox_new = [xmin, ymin, xmax, ymax, c]
    bboxes_new.append(bbox_new)
print('bboxes: ', bboxes_new)

img_wbbox = draw_bbox(img, bboxes_new, classes, hide_conf=True)

outpath = 'result.jpeg'
cv2.imwrite(outpath, img_wbbox)
print(f'Result saved at {outpath}')