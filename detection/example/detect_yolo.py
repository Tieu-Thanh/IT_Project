from ultralytics import YOLO
import cv2
from utils import draw_bbox


# ## Example input #####
img_path = '/home/thekhoi/futme/IT_Project/detection/example/data_sample_labeled/valid/images/im2.jpg'
model_path = '/home/thekhoi/futme/IT_Project/runs/detect/train/weights/best.pt'
classes = ['banana', 'orange']


# ## Run detect and save output image #####
img = cv2.imread(img_path)
trained_model = YOLO(model_path)
results = trained_model.predict(img_path)
bboxes = results[0].boxes.data
img_wbbox = draw_bbox(img, bboxes, classes)

outpath = 'result.jpeg'
cv2.imwrite(outpath, img_wbbox)
print(f'Result saved at {outpath}')

