from ultralytics import YOLO
import os
from .autoannotate import Labeler
import cv2
from .utils import draw_bbox


class Model_YOLO():
    def __init__(self, model_file=None):
        """
            model_file: path to .pt file of the trained model, leave None to init a new fresh model.
        """
        self.model = YOLO(model_file) if model_file else YOLO('yolov8s.pt')
        self.data_yaml = ''
        self.classes = []
        pass

    def _annotate_dataset(self, classes: list, input_folder: str, output_folder: str = None, extension: str = '.jpeg'):
        labeler = Labeler()
        labeler.label_images(classes, input_folder, output_folder, extension)

        output_folder = output_folder if output_folder else input_folder + '_labeled'
        data_yaml = os.path.join(output_folder, 'data.yaml')
        return data_yaml

    def train(self, classes: list, input_folder: str, output_folder: str = None, extension: str = '.jpeg',
              save_dir='.'):
        """
            Train the model, when user click `train`, this will load the dataset, train, then produce a model, e.g. `yolov8s.pt`.
                classes: classes of objects to label, e.g. ['banana', 'apple']
                input_folder: path to image-folder
                (optional) output_folder, extension, savedir.
        """
        self.classes = classes
        self.data_yaml = self._annotate_dataset(classes, input_folder, output_folder, extension)  # produce a dataset

        self.model.train(data=self.data_yaml,
                         epochs=100,
                         optimizer='AdamW',
                         imgsz=640,
                         project=save_dir,
                         exist_ok=True)

    def detect(self, input_source, is_image=False):
        """
            Detect image, video, ...
                input_data: image, video, Youtube, .etc.
                    See other types at: https://docs.ultralytics.com/modes/predict/#inference-sources
        """
        results = self.model(input_source, save=True, imgsz=640, conf=0.5)

    def detect_image(self, image_source, classes: list = []):
        """
            Same as detect, but only detect image.
                image_source: path to image
                classes: e.g. ['banana', 'orange']
        """
        try:
            img = cv2.imread(image_source)
        except:
            print("Can not load image, please re-check the source and try again.")
            return None

        if len(self.classes) == 0:
            if len(classes) == 0:
                print("Unknown class name. Please run again with classes.")
                return None
            else:
                self.classes = classes

        results = self.model(image_source, save=True, imgsz=640, conf=0.5)
        bboxes = results[0].boxes.data
        img_wbbox = draw_bbox(img, bboxes, self.classes)

        outpath = 'result.jpeg'
        cv2.imwrite(outpath, img_wbbox)
        print(f'Result saved at {outpath}')

        return img_wbbox
