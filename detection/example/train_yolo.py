from ultralytics import YOLO


# ### Example data ############
model = YOLO('yolov8s.pt')
data_yaml = './data_sample_labeled/data.yaml' 
    # data_yaml is created by `autoannotate.py`, using autodistill

# ### Train a model with data ##
results = model.train(data = data_yaml,
                      epochs = 100,
                      optimizer='AdamW',
                      imgsz = 640)
