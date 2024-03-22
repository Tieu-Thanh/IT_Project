from autodistill_detic import DETIC
from autodistill.detection import CaptionOntology
import argparse

'''
Usage:          python auoto-annotate.py    --classes ['banana','orange']\
                                            --input_folder path/to/images
'''

class Labeler():
    def __init__(self):
        pass

    def label_images(self, classes: list, input_folder: str, output_folder: str = None, extension: str = '.jpeg'):
        '''
        Do detection on images, with desired classes.

        Args:
            classes: object names to be detected
            input_folder: path to raw images
            output_folder: path to output dataset (if leave empty, it will be automatically created)
            extension: extension of image types to be process, default: jpeg image only.
        '''
        # Check required values
        if not classes:
            raise ValueError("classes is required")
        if not input_folder:
            raise ValueError("input_folder is required")


        description_label_map = dict(zip(classes, classes))

        print("Loading SAM model...")
        base_model = DETIC(ontology=CaptionOntology(
            description_label_map
        ))

        print("Start annotation...")
        dataset = base_model.label(
            input_folder = input_folder, 
            output_folder = output_folder,
            extension=extension)
        return dataset

parser = argparse.ArgumentParser(description='Auto-annotate images and create dataset.')
parser.add_argument('--classes', type=str, default='', help="(Required) List of classes to label, e.g. ['banana','orange']. Note: no blank space in between")
parser.add_argument('--input_folder', type=str, default='', help='(Required) Path to folder of images to be annotated')
parser.add_argument('--output_folder', type=str, default=None, help='Path to folder where annotated images(dataset) will be saved')
parser.add_argument('--extension', type=str, default='.jpeg', help="Extension of images to be annotated, e.g. ['jpeg', 'png', 'jpg']")

if __name__ == '__main__':

    args = parser.parse_args()
    print(args)

    classes_str = args.classes
    classes = classes_str.strip()[1:-1].split(',')
    input_folder = args.input_folder
    output_folder = args.output_folder
    extension = args.extension

    labeler = Labeler()
    labeler.label_images(classes, input_folder, output_folder, extension)