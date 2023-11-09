from AuGen.Crawler import Crawler as cl
import os
import time
from typing import Self
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup 
import codecs
import re
from selenium.webdriver.common.by import By
import requests
from webdriver_manager.chrome import ChromeDriverManager
from django.core.files.base import ContentFile
class TempCrawler(cl):

    def __setupDriver(self):
        options = webdriver.ChromeOptions()
        # options.add_argument('--headless')
        options.add_argument('--no-sandbox')
        options.add_argument('--disable-gpu')
        options.add_argument('--disable-web-security')
        options.add_argument('--allow-running-insecure-content')
        options.add_argument('--allow-cross-origin-auth-prompt')
        options.add_experimental_option("excludeSwitches", ["enable_logging"])

        driver = webdriver.Chrome(options=options)
        return driver


    def run(self):
        url = 'https://images.google.com/'
        self.driver.get(url)
        time.sleep(10)

        self.driver.quit()




if __name__ == "__main__":
    tcl = TempCrawler()
    tcl.run()