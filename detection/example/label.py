'''
Usage: python label.py
'''


import sys
import os
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)
from autoannotate import Labeler

from backend import Crawler
# ### Example data ######
classes = ['banana', 'orange']
input_folder='./data_sample'

# ### Run code ##########
labeler = Labeler()
labeler.label_images(classes, input_folder)


# dataset will be saved at './data_sample_labeled'