
import sys
import os
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)
from IT_Project.detection.yolo import Model_YOLO


# ### Example data ######
classes = ['banana', 'orange']
input_folder='./data_sample'


model1 = Model_YOLO('/home/thekhoi/futme/IT_Project/detection/example/train/weights/best.pt')

model1.detect_image('/home/thekhoi/futme/IT_Project/detection/example/Bananavarieties.jpg', classes = ['banana', 'oranges'])