from autodistill_grounded_sam import GroundedSAM
from autodistill.detection import CaptionOntology
import argparse
import json 


'''
Usage:          python auoto-annotate.py    --classes ['banana','orange']\
                                            --input_folder path/to/images
'''

parser = argparse.ArgumentParser(description='Auto-annotate images and create dataset.')
parser.add_argument('--classes', type=str, default='', help="(Required) List of classes to label, e.g. ['banana','orange']. Note: no blank space in between")
parser.add_argument('--input_folder', type=str, default='', help='(Required) Path to folder of images to be annotated')
parser.add_argument('--output_folder', type=str, default=None, help='Path to folder where annotated images(dataset) will be saved')
parser.add_argument('--extension', type=str, default='.jpeg', help="Extension of images to be annotated, e.g. ['jpeg', 'png', 'jpg']")

args = parser.parse_args()
print(args)

classes_str = args.classes
classes = classes_str.strip()[1:-1].split(',')
# print(classes)
description_label_map = dict(zip(classes, classes))
input_folder = args.input_folder
output_folder = args.output_folder
extension = args.extension

def label_images(description_label_map: dict, input_folder: str, output_folder: str = None, extension: str = 'jpeg'):
    # Check required values
    if not description_label_map:
        raise ValueError("description_label_map is required")
    if not input_folder:
        raise ValueError("input_folder is required")

    print("Load SAM model...")
    # Load SAM model that do the detection and annotation
    base_model = GroundedSAM(ontology=CaptionOntology(
        description_label_map
    ))

    # Annotation
    print("Start annotation...")
    dataset = base_model.label(
        input_folder = input_folder, 
        output_folder = output_folder,
        extension=extension,
        record_confidence=True)

    print("Dataset created done.")
    return dataset


if __name__ == '__main__':
    label_images(description_label_map, input_folder, output_folder, extension)