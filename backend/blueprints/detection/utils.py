'''
    Support detection
'''
import numpy as np
from ultralytics.utils.plotting import Annotator, colors


def draw_bbox(image, preds, classes , hide_conf=False, hide_labels=False):
    '''
        Draw bounding box, for YOLO-type prediction
            `preds`: in each line: x1, y1, x2, y2, class
    '''

    print("\nCombining Image and Labels...")
    im0 = np.array(image)
    imgsz = im0.shape[:2] # h w 
    h, w = imgsz

    line_thickness = 1 * int(imgsz[0] / 640)
    global annotator
    # Process predictions, plot onto image
    for i, bbox in enumerate(preds):
        if not hide_conf:
            xyxy, conf, c = bbox[:4], float(bbox[4]), int(float(bbox[5]))
        else:
            xyxy, c = bbox[:4], int(float(bbox[4]))

        if float(xyxy[0]) <1 and float(xyxy[3]) < 1:
            # convert ratio to pixel
            print('xyyx is ratio')
            xyxy_copy = xyxy.copy()
            xyxy[0], xyxy[2] = float(xyxy_copy[0])*w, float(xyxy_copy[2])*w
            xyxy[1], xyxy[3] = float(xyxy_copy[1])*h, float(xyxy_copy[3])*h

        annotator = Annotator(im0, line_width=line_thickness)
        label=None
        label = (
            None
            if hide_labels
            else (classes[c] if hide_conf else f"{classes[c]} {conf:.2f}")
        )
        # if not hide_conf:
            # print(' xyxy,  conf, cls:', xyxy, conf, c )
        # else:
            # print(' xyxy,  cls:', xyxy, c )
        annotator.box_label(xyxy, label, color=colors(c, True))

    img_with_bboxes = annotator.result()
    return img_with_bboxes
