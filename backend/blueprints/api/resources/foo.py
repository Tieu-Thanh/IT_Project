from PIL import Image
import os

# Define the directory path
# directory_path = "D:/Workspace/IT_Project\\backend\\blueprints\\detection\\Images\\3321\\C"

# # Iterate through files in the directory
# for filename in os.listdir(directory_path):
#     # Construct the full path of the file
#     file_path = os.path.join(directory_path, filename)
#
#     # Check if it's an image file (extension check)
#     if filename.lower().endswith((".jpg", ".jpeg", ".png", ".bmp", ".gif")):
#         try:
#             # Open the image
#             image = Image.open(file_path)
#             # You can now access image properties like width, height, format, etc.
#             # For example, print image width:
#             print(f"Image width of {filename}: {image.width}")
#             # To display the image (requires additional libraries like matplotlib)
#             # you'd need further processing.
#         except (IOError, OSError) as e:
#             print(f"Error opening image {filename}: {e}")

import cv2

# Define the directory path
directory_path = r"D:/Workspace/IT_Project/backend/blueprints/detection/Images/3321/C"

# Iterate through files in the directory
# for filename in os.listdir(directory_path):
#     # Construct the full path of the file
#     file_path = os.path.join(directory_path, filename)
#
#     # Check if it's an image file (extension check)
#     if filename.lower().endswith((".jpg", ".jpeg", ".png", ".bmp")):
#         try:
#             # Read the image using cv2.imread
#             image = cv2.imread(file_path)
#             # Check if image is loaded successfully
#             if image is not None:
#                 # Display the image
#                 cv2.imshow(filename, image)
#                 # Wait for a key press to close the window
#                 cv2.waitKey(0)
#                 # Close the window after key press
#                 cv2.destroyAllWindows()
#         except (IOError, OSError) as e:
#             print(f"Error opening image {filename}: {e}")

# os.chdir("../")
HOME = os.getcwd()
img_folder = os.path.join(HOME, "Images")
print(img_folder)
