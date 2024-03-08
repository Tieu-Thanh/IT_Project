from celery import current_app as celery_app
from blueprints.detection.yolo import Model_YOLO


@celery_app.task(name="train_yolo_model")
def train_yolo_model(classes, img_folder, extension):
    # The actual training implementation
    # yolo = Model_YOLO()
    # result = yolo.train(classes, input_folder=img_folder, extension=extension)
    # return result
    print("Start training YOLO model")
    print(classes)
    print(img_folder)
    print(extension)
