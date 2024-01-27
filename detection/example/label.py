'''
Usage: python label.py
'''
import sys
import os
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)

from autoannotate import label_images

classes = ['banana', 'orange']
description_label_map = dict(zip(classes, classes))
label_images(description_label_map=description_label_map, input_folder='data_sample')