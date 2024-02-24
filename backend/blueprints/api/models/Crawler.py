import time
import os
import requests
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor
# from api.models.Image import Image
from .Image import Image


class Crawler:
    def __init__(self):
        self.driver = self.setup_driver()

    def setup_driver(self):
        options = Options()
        options.add_argument('--headless')
        options.add_argument('--no-sandbox')
        options.add_argument('--disable-gpu')
        options.add_argument('--disable-web-security')
        options.add_argument('--allow-running-insecure-content')
        options.add_argument('--allow-cross-origin-auth-prompt')
        options.add_experimental_option("excludeSwitches", ["enable_logging"])

        return webdriver.Chrome(options=options)

    def __scroll_down_page(self, times=2):
        for _ in range(times):
            self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(2)

    def __crawl_images(self, query, img_num=50, scroll_times=1):
        images_list = []
        try:
            search_url = f'https://www.google.com/search?q={query.replace(" ", "+")}&tbm=isch'
            self.driver.get(search_url)
            time.sleep(5)

            self.__scroll_down_page(times=scroll_times)

            soup = BeautifulSoup(self.driver.page_source, 'html.parser')

            image_container = soup.find(id='islrg')

            idx = 1
            for raw_img in image_container.find_all('img'):
                if idx > img_num:
                    break

                link = raw_img.get('data-src')
                if link and link.startswith("https://") and "images" in link:
                    img_id = f"{idx}"

                    image = Image(image_id=img_id, query=query.replace(" ", "_"), url=link)
                    images_list.append(image)

                    idx += 1

        except Exception as e:
            print(f"An error occurred during crawling: {e}")

        return images_list

    def crawl(self, queries, img_num=50):
        img_list = []
        with ThreadPoolExecutor(max_workers=3) as executor:
            for query in queries:
                result = list(executor.map(self.__crawl_images, [query], [img_num]))
                img_list.extend(result[0])
        return img_list

    def create_download_folder(self, folder_name="Images"):
        if not os.path.exists(folder_name):
            os.makedirs(folder_name)

    def download_images(self, images, download_folder="Images"):
        self.create_download_folder(download_folder) # create folder

        for img in images:
            filename = f"{img.query}_{img.image_id}.jpg"
            filepath = os.path.join(download_folder, filename)

            response = requests.get(img.url)
            if response.status_code == 200:
                with open(filepath, 'wb') as file:
                    file.write(response.content)
                print(f"Downloaded: {filepath}")
            else:
                print(f"Failed to download: {filename}")


if __name__ == "__main__":
    # # Crawl images
    # queries = ["hawk", "tiger", "grass hopper"]
    # crawler = Crawler()
    # images_data = crawler.crawl(queries, img_num=5)
    # print(images_data)

    # Download images
    os.chdir("../../")
    HOME = os.getcwd()
    print(HOME)
    img_folder = os.path.join(HOME, "detection", "Images")
    print(img_folder)
    # crawler.download_images(images_data, download_folder=img_folder)
