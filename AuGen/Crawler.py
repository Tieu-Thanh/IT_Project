import time
import requests
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup

# from api.models.Image import Image
from Image import Image

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

    def scroll_down_page(self, times=2):
        for _ in range(times):
            self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(2)

    def crawl_images(self, query):
        images_list = []
        try:
            search_url = f'https://www.google.com/search?q={query}&tbm=isch'
            self.driver.get(search_url)
            time.sleep(5)

            self.scroll_down_page()

            soup = BeautifulSoup(self.driver.page_source, 'html.parser')

            image_container = soup.find(id='islrg')

            for idx, raw_img in enumerate(image_container.find_all('img'), 1):
                link = raw_img.get('data-src')
                if link and link.startswith("https://") and "images" in link:
                    img_id = f"image_{idx}"
                    roi_values = []  # You may want to extract ROI values from the page

                    image = Image(img_id, link, roi_values)
                    images_list.append(image)

        except Exception as e:
            print(f"An error occurred during crawling: {e}")

        return images_list

    def crawl(self, query):
        with self.driver:
            return self.crawl_images(query)


if __name__ == "__main__":
    crawler = Crawler()
    images_data = crawler.crawl("apple+banana+orange")
    for img in images_data:
        print(img.to_dict())
