# TODO:
# Crawl and store folder images
# Read images: cv2

# Trainer

import os
import requests
from Crawler import Crawler
import cv2


def create_download_folder(download_folder="Images"):
    if not os.path.exists(download_folder):
        os.makedirs(download_folder)


def download_image(url, filename, download_folder="Images"):
    response = requests.get(url)
    if response.status_code == 200:
        with open(os.path.join(download_folder, filename), 'wb') as file:
            file.write(response.content)
        print(f"Downloaded: {filename}")
    else:
        print(f"Failed to download: {filename}")


def read_images_from_folder(folder_path="Images"):
    images = []
    for filename in os.listdir(folder_path):
        if filename.endswith(('.jpg', '.jpeg', '.png')):  # Add more file extensions if needed
            image_path = os.path.join(folder_path, filename)
            img = cv2.imread(image_path)
            if img is not None:
                images.append(img)
                print(f"Read image: {filename}")
            else:
                print(f"Failed to read image: {filename}")

    return images


if __name__ == "__main__":
    os.chdir("..")
    HOME = os.getcwd()
    img_folder = os.path.join(HOME, "detection","Images")

    queries = ["hawk", "tiger", "grass hopper"]
    crawler = Crawler()
    images_data = crawler.multi_crawl(queries, 10)

    create_download_folder(download_folder=img_folder)

    for img in images_data:
        img_data = img.to_dict()
        url = img_data['url']
        image_id = f"{img_data['query']}_{img_data['image_id']}"
        filename =  f"{image_id}.jpg"  # You can change the file extension based on the actual image format

        download_image(url, filename, download_folder=img_folder)

    images = read_images_from_folder(img_folder)
    for i, img in enumerate(images):
        print(f"Image {i + 1} shape: {img.shape}")
