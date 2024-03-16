
from ultralytics import YOLO
import os
from .autoannotate import Labeler
import cv2
from .utils import draw_bbox
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

        output_folder = output_folder if output_folder else input_folder + '_labeled'
        data_yaml = os.path.join(output_folder, 'data.yaml')
        return data_yaml

    def train(self, classes: list, input_folder: str, output_folder: str = None, extension: str = '.jpeg',
              save_dir='.'):
        '''
            Train the model, when user click `train`, this will load the dataset, train, then produce a model, e.g. `yolov8s.pt`.
                classes: classes of objects to label, e.g. ['banana', 'apple']
                input_folder: path to image-folder
                (optional) output_folder, extension, savedir.
        '''
        self.classes = classes
        self.data_yaml = self._annotate_dataset(classes, input_folder, output_folder, extension)  # produce a dataset

        self.model.train(data=self.data_yaml,
                         epochs=100,
                         optimizer='AdamW',
                         imgsz=640,
                         project=save_dir,
                         exist_ok=True)

    def detect(self, input_source):
        '''
            Detect image, video, ...
                input_data: image, video, Youtube, .etc.
                    See other types at: https://docs.ultralytics.com/modes/predict/#inference-sources
        '''
        results = self.model(input_source, save=True, imgsz=640, conf=0.5)

    def count_bytetrack(self, input_video_path: str = None,
                        output_video_path: str = None)->str:
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
            return ""

        self.video_count = 0
        if not output_video_path:
            # ./video.mp4 -> ./video-result.mp4
            output_video_path = os.path.join(os.path.dirname(input_video_path),
                                             os.path.basename(input_video_path).split('.')[0] + "_result.mp4")
        print(input_video_path)
        video_info = sv.VideoInfo.from_video_path(input_video_path)
        w = video_info.resolution_wh[0]
        h = video_info.resolution_wh[1]
        polygon = np.array([
            [0, 0],
                             [w,0],
                             [w,h],
                             [0,h]
                            ])
        print('Processing... Video: ', os.path.basename(input_video_path))
        zone = sv.PolygonZone(polygon=polygon, frame_resolution_wh=video_info.resolution_wh)
        # initiate annotators

        model = self.model

        def _process_frame(frame: np.ndarray, _) -> np.ndarray:
            # detect
            results = model(frame, imgsz=1280)[0]

            detections = sv.Detections.from_ultralytics(results)
            detections = detections[detections.confidence > 0.7]
            zone.trigger(detections)
            # annotate
            box_annotator = sv.BoxAnnotator(thickness=4, text_thickness=4, text_scale=2)
            # print(detections)
            labels = [f"{model.names[class_id]} {confidence:0.7f}" for _, _, confidence, class_id, _, _ in detections]
            frame = box_annotator.annotate(scene=frame, detections=detections, labels=labels)
            self.video_count+=zone.current_count
            print("current",zone.current_count)
            return frame

        sv.process_video(source_path=input_video_path, target_path=output_video_path, callback=_process_frame)

        print('Done. Output video saved to: ', output_video_path)
        return output_video_path
