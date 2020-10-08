from selenium import webdriver
from time import sleep

#path to custom download directory
preferences = {"download.default_directory" : "D:\College\DT211C_Year_2\Semester_2\Past_Exams\CMPU2007_Databases_1"}
boptions = webdriver.ChromeOptions()
boptions.add_experimental_option("prefs", preferences)

class examPapers:
    def __init__(self,tusr,spw):
        self.tusr = tusr
        self.spw = spw
        #self.driver.get("chrome://settings/?search=pdf")
        self.driver.findElementByID("pdf-section").click()
        self.tmodCod = "CMPU2007"
        self.driver.get("http://{}:{}@student.dit.ie/exampapers/KT/Academic%20Year%202013-2014%20Sci".format(tusr,spw))
        sleep(3)
        self.driver.find_element_by_xpath("//a[contains(text(), 'Summer/')]").click()
        sleep(3)
        self.driver.find_element_by_xpath("//a[contains(text(), '{}')]".format(self.tmodCod)).click()
        sleep(6)

examPapers('Student','ThunderRoad')