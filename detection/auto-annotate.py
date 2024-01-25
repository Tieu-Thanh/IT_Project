from autodistill_grounded_sam import GroundedSAM
from autodistill.detection import CaptionOntology
import argparse
import json 

parser = argparse.ArgumentParser(description='Auto-annotate images and create dataset.')
parser.add_argument('--description_label_map', type=str, default=None, help="(Required) Path to json, dictionary mapping from description to label, e.g. {'white horse with black strips':'zebra'}")
parser.add_argument('--input_folder', type=str, default=None, help='(Required) Path to folder of images to be annotated')
parser.add_argument('--output_folder', type=str, default=None, help='Path to folder where annotated images(dataset) will be saved')
parser.add_argument('--extension', type=str, default='.jpeg', help="Extension of images to be annotated, e.g. ['jpeg', 'png', 'jpg']")

args = parser.parse_args()
print(args)

map_file = args.description_label_map
description_label_map = json.load(open(map_file, 'r'))
input_folder = args.input_folder
output_folder = args.output_folder
extension = args.extension

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
