# *Birds of a Feather*

## By: Rishmal Rohin Kumar

---

### Description

*Birds of a Feather* is an app that I created with for a group project in my Software Engineering course. The idea for the app is that students are returning to in-person education after about one and a half years of remote learning, and students are trying to pick teammates for their group homework assignments. Students can create a profile that includes their first name, which is either retrieved using Google login (on Android) or entered by the student, an optional profile picture, and any past or current courses that they would like to display. After creating a profile, they can start a Bluetooth search that will find other students within a radius who have taken a course with them and display a summary of their first name, profile picture, and number of shared classes. Students can click on these summary entries to view the other student's profile page, which includes their name, profile picture, and complete list of shared courses and the details associated with them. While on this page, students can send a "wave" to the other student, which will notify the other student that they are interested in working together, as their name will at the top of the list of students on the previous search page. Students also have the option to "Favorite" a student to keep track of them on their own app and view a list of favorites later. Additionally, on the page with the list of summaries, students can sort by recency, class size, or none, to prioritize shared classes that occurred recently, sharing a small class with another student, or simply just prioritizing the raw number of shared classes, respectively. Furthermore, they can filter by current quarter only to limit the shared classes to only consider classes from the current quarter. Lastly, when a student stops the search, they are given the ability to save the session and give it a name. The next time they decide to start a search, students can view previous sessions and choose to resume them.  

---

### Usage

To use this app, you must download [Android Studio](https://developer.android.com/studio) and run the app on an Android device linked to the computer or one of Android Studio's emulators.  
First, import this repository to your personal GitHub. Then, open this repository on Android Studio by clicking File > New > Project from Version Control. Once the Gradle build has finished, select the correct device(s) you wish to run the app on at the top and click run. (Note: If you want to use an emulator, you must choose one that has Google Play Services enabled and an appropriate API. The Google Pixel 4 with API 30 works well.) Once the app launches, you must enter a first name or use Google login. Next, you may enter the URL of a picture you wish to use as a profile picture. Then, you may enter any classes that you wish to be considered when calculating shared classes with other students. When you finish and click "Submit", you will be taken to the Home page with the Search button. 
- If you are on an emulator, you must use the mock Bluetooth feature to simulate the app receiving Bluetooth messages. Click Start and then click the Mock Bluetooth button. Here, you must enter CSV input in the form below. Once you are finished entering Bluetooth messages, you can navigate back to the Home screen and you will see the messages reflected on the screen (assuming that your profile does share classes with the students you entered).
  - a4ca50b6-941b-11ec-b909-0242ac120002,,,,  
    Bill,,,,  
    https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,  
    2021,FA,CSE,210,Small  
    2022,WI,CSE,110,Large
  - The first line is the other student's UUID, the second line is the student's name, the third line is the student's profile picture, the next lines are the student's courses, formatted Year, Quarter Code, Subject, Course Number, and Class Size
  - In order to enter multiple students, enter the students' CSVs one at a time and make sure to change their UUID's (you can change any character).
  - If you want to simulate a wave, enter a line of this format at the bottom of the CSV, where the first entry is the UUID that will receive the wave (the current user's UUID is displayed at the top of this screen):   
    - 4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,
- If you are running the app on multiple Android devices, you can run click Start and run the app normally, provided that Bluetooth is on. 

--- 

### Credits

This project was assigned by Professor William Griswold at University of California, San Diego. It was completed in collaboration with Tyler Lentz, Zachariah Jasser, Alan Hang, Zhuoran Li, and Jinuk Lee. 
