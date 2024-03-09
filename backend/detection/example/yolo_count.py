import sys
import os
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)
from yolo import Model_YOLO

import numpy as np

polygon = np.array([
    [1725, 1550],
    [2725, 1550],
    [3500, 2160],
    [1250, 2160]
])
input_video_path = "/home/thekhoi/futme/IT_Project/detection/example/data_sample/mall.mp4"

model1 = Model_YOLO('/home/thekhoi/futme/IT_Project/detection/example/train/weights/best.pt')
model1.count_bytetrack(input_video_path=input_video_path,polygon=polygon)