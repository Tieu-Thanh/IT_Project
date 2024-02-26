import cv2

import sys
import os
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)
from utils import draw_bbox, draw_bbox_SAM

imgpath = '/home/thekhoi/futme/IT_Project/detection/example/data_sample_labeled/train/images/im3.jpg'
lblpath = '/home/thekhoi/futme/IT_Project/detection/example/data_sample_labeled/train/labels/im3.txt'

classes = ['banana', 'orange']
img = cv2.imread(imgpath)
with open(lblpath, 'r') as f:
    bboxes_segment = [bbox.split() for bbox in f.read().split('\n')]

img_wbbox = draw_bbox_SAM(img, bboxes_segment, classes, hide_conf=True)

outpath = 'result.jpeg'
cv2.imwrite(outpath, img_wbbox)
print(f'Result saved at {outpath}')