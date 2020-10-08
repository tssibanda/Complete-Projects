"""
Program Description:
Author: Thamsanqa Sibanda
Date: 14 March 2020
Warning: excessive use of this bot on 1 account may cause the account to be blocked temporarily
"""

from selenium import webdriver
from time import sleep

# load password from a text file named mine.txt
mine = open("mine.txt", "r")
for line in mine:
    pw = line


class InstaBot:

    # constructor
    def __init__(self, usrn, pw):
        self.usrn = usrn
        self.driver = webdriver.Chrome()
        self.driver.get("https://instagram.com")
        sleep(3)
        self.driver.find_element_by_xpath("//input[@name=\"username\"]").send_keys(usrn)
        self.driver.find_element_by_xpath("//input[@name=\"password\"]").send_keys(pw)
        self.driver.find_element_by_xpath('//button[@type=\"submit\"]').click()
        sleep(3)
        self.driver.find_element_by_xpath("//button[contains(text(), 'Not Now')]").click()
        sleep(2)
        self.driver.find_element_by_xpath("//a[@href='/{}/']".format(self.usrn)).click()
        sleep(2)
        following = self.get_names("following")
        followers = self.get_names("followers")

        # display users who are not following back
        for i in following:
            if i not in followers:
                print(i)

    # method to get user names
    def get_names(self, t):
        sleep(2)
        self.driver.find_element_by_xpath("//a[@href=\"/{}/{}/\"]".format(self.usrn, t)).click()
        sleep(1)

        # code to scroll to the bottom of the scroll box
        scroll_box = self.driver.find_element_by_xpath("/html/body/div[4]/div/div[2]")
        last_ht = 0
        ht = 1
        while last_ht != ht:
            last_ht = ht
            sleep(1)
            ht = self.driver.execute_script("""arguments[0].scrollTo(0,arguments[0].scrollHeight);
            return arguments[0].scrollHeight;""", scroll_box)

        # code to find all names in scroll box
        links = scroll_box.find_elements_by_tag_name('a')
        names = [name.text for name in links if name.text != '']

        # click close button
        self.driver.find_element_by_xpath("/html/body/div[4]/div/div[1]/div/div[2]/button").click()
        return names


followBot = InstaBot('stevenbeatsmith', pw)
mine.close()
