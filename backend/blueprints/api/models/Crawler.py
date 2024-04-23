import time
import os
import requests
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor
from .Image import Image


class Crawler:
    _driver_instance = None

    def __init__(self):
        self.driver = self.setup_driver()

    @classmethod
    def setup_driver(cls):
        if cls._driver_instance is None:
            options = Options()
            options.add_argument('--headless')
            options.add_argument('--no-sandbox')
            options.add_argument('--disable-gpu')
            options.add_argument('--disable-web-security')
            options.add_argument('--allow-running-insecure-content')
            options.add_argument('--allow-cross-origin-auth-prompt')
            options.add_experimental_option("excludeSwitches", ["enable_logging"])
            cls._driver_instance = webdriver.Chrome(options=options)
        return cls._driver_instance

    def __scroll_down_page(self, times=1):
        for _ in range(times):
            self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(2)

    def __crawl_images(self, query, img_num=50, scroll_times=1):
        images_list = []
        try:
            search_url = f'https://www.google.com/search?q={query.replace(" ", "+")}&tbm=isch'
            print(search_url)
            time.sleep(5)
            self.driver.get(search_url)
            self.__scroll_down_page(times=scroll_times)

            soup = BeautifulSoup(self.driver.page_source, 'html.parser')
            def has_exact_class(tag, class_name):
                # Check if the element's 'class' attribute exactly matches the provided class_name
                return tag.has_attr('class') and tag['class'] == class_name.split()

            # Find elements that have exactly "text" as their class
            image_tags = soup.find_all(lambda tag: has_exact_class(tag, "YQ4gaf"))
            idx = 1
            for img_tag in image_tags:
                if idx > img_num:
                    break

                src_link = img_tag.get('src')
                if src_link and src_link.startswith("https://"):
                    print(src_link, "\n")
                    image = Image(image_id=idx, query=query.replace(" ", "_"), url=src_link)
                    images_list.append(image)

                    idx += 1

                elif src_link and src_link.startswith("data:image"):
                    continue  # Skip base64 images

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

    @classmethod
    def quit_driver(cls):
        if cls._driver_instance:
            cls._driver_instance.quit()
            cls._driver_instance = None

    def create_download_folder(self, folder_name="Images"):
        if not os.path.exists(folder_name):
            os.makedirs(folder_name)

    def download_image(self, img, download_folder):
        filename = f"{img.query}_{img.image_id}.jpg"
        filepath = os.path.join(download_folder, filename)

        response = requests.get(img.url)
        if response.status_code == 200:
            with open(filepath, 'wb') as file:
                file.write(response.content)
            print(f"Downloaded: {filepath}")
        else:
            print(f"Failed to download: {filename}")

    def download_images(self, images, download_folder="Images"):
        print("Downloading images")
        """Modified download_images method to use ThreadPoolExecutor."""
        self.create_download_folder(download_folder)  # Ensure the download folder exists

        # Using ThreadPoolExecutor to download images concurrently
        futures = []
        with ThreadPoolExecutor(max_workers=5) as executor:
            for img in images:
                future = executor.submit(self.download_image, img, download_folder)
                futures.append(future)

        # Wait for all futures to complete
        for future in futures:
            future.result()  # This will block until the future is done

    # def download_images(self, images, download_folder="Images"):
    #     self.create_download_folder(download_folder)  # create folder
    #
    #     for img in images:
    #         filename = f"{img.query}_{img.image_id}.jpg"
    #         filepath = os.path.join(download_folder, filename)
    #
    #         response = requests.get(img.url)
    #         if response.status_code == 200:
    #             with open(filepath, 'wb') as file:
    #                 file.write(response.content)
    #             print(f"Downloaded: {filepath}")
    #         else:
    #             print(f"Failed to download: {filename}")


if __name__ == "__main__":
    # # Crawl images
    queries = ["hawk"]
    crawler = Crawler()
    images_data = crawler.crawl(queries, img_num=5)
    # # print(images_data)
    Crawler.quit_driver()

    # images_data = [
    #     {'image_id': '1',
    #      'url': 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYgZAMNcyMbbksPezrpkR-Qoagg9n_I5pp8A&usqp=CAU',
    #      'query': 'hawk'},
    #     {'image_id': '2',
    #      'url': 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5dHeOm21gvacSNtlSEmLHvbTUdVrN4LuYMA&usqp=CAU',
    #      'query': 'hawk'},
    #     {'image_id': '3',
    #      'url': 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJmkK9BzVTiYTtPx-3ptKruX5ZqEUHjv4Npg&usqp=CAU',
    #      'query': 'hawk'},
    #     {'image_id': '4',
    #      'url': 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDxy7SYfGabn85_pSQKsLSSlZUvTiEaS-PxQ&usqp=CAU',
    #      'query': 'hawk'},
    #     {
    #         'image_id': '5',
    #         'url': 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqzUQasrAi67CY86GeJ6wwTFp0OrRmWd38O8BmRVkAqB6FSceBSdql8xQ&usqp=CAU',
    #         'query': 'hawk'
    #     }
    # ]
    # Download images
    os.chdir("../../")
    HOME = os.getcwd()
    # print(HOME)
    img_folder = os.path.join(HOME, "detection", "Images")
    print(img_folder)
    # print(img_folder)
    crawler.download_images(images_data, download_folder=img_folder)
