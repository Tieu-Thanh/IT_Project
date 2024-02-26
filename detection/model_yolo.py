from ultralytics import YOLO

class Model_YOLO():
    def __init__(self, model_name='yolov8s.pt', data_yaml = ''):
        self.model = YOLO(model_name)
        self.data_yaml = data_yaml
        pass

    def _annotate_dataset(self):
        data_yaml = ''
        return data_yaml

    def train(self):
        '''
            Train the model, when user click `train`, this will load the dataset, train, then produce a model, e.g. `yolov8s.pt`.
        '''

        self.data_yaml = self._annotate_dataset() # produce a dataset
 
        model_ready = self.model.train(data = self.data_yaml,
                            epochs = 100,
                            optimizer='AdamW',
                            imgsz = 640)
        # A file yolov8s.pt will be produced

    def detect(self):
        '''
            Use the trained model to detect new images.
        '''
        pass