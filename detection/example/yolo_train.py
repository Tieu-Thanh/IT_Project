
import sys
import os
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)
from model_yolo import Model_YOLO


# ### Example data ######
classes = ['banana', 'orange']
input_folder='./data_sample'


model1 = Model_YOLO()

model1.train(classes, input_folder)

model1.detect()