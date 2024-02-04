'''
    Support detection
'''
import numpy as np
from ultralytics.utils.plotting import Annotator, colors


def draw_bbox(image, preds, classes , hide_conf=False, hide_labels=False):
    print("\nCombining Image and Labels...")
    im0 = np.array(image)
    imgsz = im0.shape[:2] # h w 
    h, w = imgsz

    line_thickness = 1 * int(imgsz[0] / 640)

    # Process predictions, plot onto image
    for i, bbox in enumerate(preds):
        xyxy, conf, c = bbox[:4], float(bbox[4]), int(float(bbox[5]))

        annotator = Annotator(im0, line_width=line_thickness)
        label=None
        label = (
            None
            if hide_labels
            else (classes[c] if hide_conf else f"{classes[c]} {conf:.2f}")
        )
        # print(' xyxy,  conf, cls:', xyxy, conf, c )
        annotator.box_label(xyxy, label, color=colors(c, True))

    img_with_bboxes = annotator.result()
    return img_with_bboxes
