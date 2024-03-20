from ultralytics import YOLO
import os
from autoannotate import Labeler
import cv2
from utils import draw_bbox
import numpy as np
import supervision as sv

class Model_YOLO():
    def __init__(self, model_file=None):
        '''
            model_file: path to .pt file of the trained model, leave None to init a new fresh model.
        '''
        self.model = YOLO(model_file) if model_file else YOLO('yolov8s.pt')
        self.data_yaml = ''
        self.classes = []
        pass

    def _annotate_dataset(self, classes: list, input_folder: str, output_folder: str = None, extension: str = '.jpeg'):
        labeler = Labeler()
        labeler.label_images(classes, input_folder, extension=extension, output_folder=output_folder)
        print("aaa")
        output_folder = output_folder if output_folder else input_folder+'_labeled'
        data_yaml = os.path.join(output_folder, 'data.yaml')
        return data_yaml


    def train(self, classes: list, input_folder: str, output_folder: str = None, extension: str = '.jpeg', save_dir='.'):
        '''
            Train the model, when user click `train`, this will load the dataset, train, then produce a model, e.g. `yolov8s.pt`.
                classes: classes of objects to label, e.g. ['banana', 'apple']
                input_folder: path to image-folder
                (optional) output_folder, extension, savedir.
        '''
        self.classes = classes
        self.data_yaml = self._annotate_dataset(classes, input_folder, output_folder, extension) # produce a dataset
        
        self.model.train(data = self.data_yaml,
                            epochs = 100,
                            optimizer='AdamW',
                            imgsz = 640,
                            project=save_dir,
                            exist_ok = True)
    
    def detect(self, input_source, is_image = False):
        '''
            Detect image, video, ...
                input_data: image, video, Youtube, .etc. 
                    See other types at: https://docs.ultralytics.com/modes/predict/#inference-sources
        '''
        results = self.model(input_source, save=True, imgsz=640, conf=0.5)


    def detect_image(self, image_source, classes: list= []):
        '''
            Same as detect, but only detect image.
                image_source: path to image
                classes: e.g. ['banana', 'orange']
        '''
        try:
            img = cv2.imread(image_source)
        except:
            print("Can not load image, please re-check the source and try again.")
            return None

        # if len(self.classes) ==0 :
        #     if len(classes) == 0:
        #         print("Unknown class name. Please run again with classes.")
        #         return None
        #     else:
        #         self.classes = classes
        self.classes = self.model.names
        results = self.model(image_source, save=True, imgsz=640, conf=0.5)
        bboxes = results[0].boxes.data
        detected_classes = list(bboxes[:, 5])
        counts = {c: detected_classes.count(c) for c in set(detected_classes)}
        counts_classes = {self.classes[int(k)]: v for k, v in counts.items()}
        counts_classes_str = '\n'.join([f"{k}: {v}" for k, v in counts_classes.items()])
        # print(counts_classes)
        img_wbbox = draw_bbox(img, bboxes, self.classes, counts_classes_str)

        outpath = 'result.jpeg'
        cv2.imwrite(outpath, img_wbbox)
        print(f'Result saved at {outpath}')

        return img_wbbox



    def count_bytetrack(self, input_video_path: str = None, 
                    polygon: np.array = None,
                    output_video_path : str = None):
        '''
            Count the number of objects in the polygon zone of the input video.
            Args:
                input_video_path: str
                    Path to the input video file
                polygon: np.array
                    The polygon zone to count the number of objects
                    e.g. np.array([ [x1, y1], ..., [x4, y4] ])
                output_video_path: str
                    Path to the output video file, optional
        
        '''
        if not input_video_path:
            print("Invalid input_video_path, please check.")
            return
        if polygon is None:
            print("Invalid polygon, please check.")
            return

        if not output_video_path:
            # ./video.mp4 -> ./video-result.mp4
            output_video_path = os.path.join (os.path.dirname(input_video_path),os.path.basename(input_video_path).split('.')[0] + "-result.mp4")


        video_info = sv.VideoInfo.from_video_path(input_video_path)
        print('Processing... Video: ', os.path.basename(input_video_path))
        zone = sv.PolygonZone(polygon=polygon, frame_resolution_wh=video_info.resolution_wh)

        # initiate annotators
        zone_annotator = sv.PolygonZoneAnnotator(zone=zone, color=sv.Color.WHITE, thickness=6, text_thickness=6, text_scale=4)

        model = self.model

        def _process_frame(frame: np.ndarray, _) -> np.ndarray:
            # detect
            results = model(frame, imgsz=1280)[0]
            detections = sv.Detections.from_ultralytics(results)
            # detections = detections[detections.class_id == 0]
            zone.trigger(detections=detections)

            # annotate
            box_annotator = sv.BoxAnnotator(thickness=4, text_thickness=4, text_scale=2)
            # print(detections)
            labels = [f"{model.names[class_id]} {confidence:0.2f}" for _, _, confidence, class_id, _, _ in detections]
            frame = box_annotator.annotate(scene=frame, detections=detections, labels=labels)
            frame = zone_annotator.annotate(scene=frame)

            return frame
        sv.process_video(source_path=input_video_path, target_path=output_video_path, callback=_process_frame)

        print('Done. Output video saved to: ', output_video_path)