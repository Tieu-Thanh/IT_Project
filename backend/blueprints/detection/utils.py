'''
    Support detection
'''
import numpy as np
from ultralytics.utils.plotting import Annotator, colors


def draw_bbox(image, preds, classes, hide_conf=False, hide_labels=False):
    """
        Draw bounding box, for YOLO-type prediction
            `preds`: in each line: x1, y1, x2, y2, class
    """

    print("\nCombining Image and Labels...")
    im0 = np.array(image)
    imgsz = im0.shape[:2]  # h w
    h, w = imgsz

    line_thickness = 1 * int(imgsz[0] / 640)

    # Process predictions, plot onto image
    for i, bbox in enumerate(preds):
        if not hide_conf:
            xyxy, conf, c = bbox[:4], float(bbox[4]), int(float(bbox[5]))
        else:
            xyxy, c = bbox[:4], int(float(bbox[4]))

        if float(xyxy[0]) < 1 and float(xyxy[3]) < 1:
            # convert ratio to pixel
            xyxy_copy = xyxy.copy()
            xyxy[0], xyxy[2] = float(xyxy_copy[0]) * w, float(xyxy_copy[2]) * w
            xyxy[1], xyxy[3] = float(xyxy_copy[1]) * h, float(xyxy_copy[3]) * h

        annotator = Annotator(im0, line_width=line_thickness)
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


def draw_bbox_SAM(image, preds, classes, hide_conf=False, hide_labels=False):
    """
        Draw bounding box, for SAM-type prediction, which is segmentation
            `preds`: format [[class, x1, y1, x2, y2, ...], [...], ...]
                Example of 1 bbox: [0 0.48913 0.10383 0.48188 0.11475 0.48188 0.12022, ...]
    """
    # Convert segment to bounding box
    bboxes_rect = []
    for bbox in preds:
        if len(bbox) == 0:
            continue
        c = int(float(bbox[0]))
        xylist = [float(i) for i in bbox[1:]]  # convert string to float

        xlist = xylist[0::2]  # index 0, 2, 4, 6, ...
        ylist = xylist[1::2]  # index 1, 3, 5, 7, ...

        xmax, ymax = max(xlist), max(ylist)
        xmin, ymin = min(xlist), min(ylist)

        bbox_rect = [xmin, ymin, xmax, ymax, c]
        bboxes_rect.append(bbox_rect)

    img_with_bboxes = draw_bbox(image, bboxes_rect, classes, hide_conf, hide_labels)
    return img_with_bboxes
