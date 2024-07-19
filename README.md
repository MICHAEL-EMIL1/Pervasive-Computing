# Pervasive-Computing Project 
The pervasive computing project aims to develop a solution for remote smart control. The purpose is to control embedded electronic circuits using a mobile application. The main objectives are Seamless Connectivity, Remote Control, Real-time Monitoring.

## System description
The remote smart control project consists of a digitally controlled interface between an embedded system comprising a microcontroller, wireless integrated module, environmental sensors. This system should make a connection over Wifi and send and receive data to a monitoring system on a mobile application. Also, it has a feature to control some parts of the system remotely from the mobile application. The data is stored in cloud (firebase) and cached by both the microcontroller and mobile application through SQL lite. 

## Smart Home Project
### Enables users to remotely control and manage various devices and systems within their homes using smartphone:
1.	Feature1: Temperature: Measure the room temperature through temperature sensor, uploads the value on the cloud (firebase database), then view it on the mobile application through an activity (e.g. Temperature Activity).
2.	Feature2: Password: Create a password for your home through the mobile application, store it on the cloud. It is used by the microcontroller’s connected keypad to open the home lock (door) if it is entered correctly.
3.	Feature 3: Light: A Boolean value is sent through the mobile application to the microcontroller to control and turn on/off a LED. The Boolean value is captured through a button on the mobile application’s interface and stored on the cloud.
4.	Feature 4: Message: A message is entered by the mobile application (e.g. Message Activity), stored on the cloud, and cashed by the microcontroller from the cloud to be displayed on LCD, for example “Welcome Mohamed!”.

## Software Requirements
Use android studio IDE for developing the mobile application. All the following features should be implemented:
1)	Registration Activity: name, username, password, confirm password, profile, picture, email, birthdate (calendar) [stored on firebase, then cached SQLite].
2)	Login Activity: 
1- Login using normal method or firebase authentication.(Implement both).
2- Implement remember me, using checkbox.
3- Implement forget password.
3)	Main Activity (Home Activity): which contains a listview of all actions. 
4)	Listview or Recycler view: which contains an item for each action, each item contains (image/title), if item clicked it goes to the specified action activity.
Ex: Temperature action, the list item will contain: a title is called Temperature, and an image of a “thermometer.png”, if clicked it will go to Temperature Action activity.
5)	Search facility in the application bar, where you filter by action[title].
Ex: you want to search for temperature action, you write “Temperature” in search bar, so it appears without the rest of actions.
6)	Options menu:
1-	Activity Log (item1): click, then go to Activity Log activity.
2-	Profile (item2): go to Profile Activity.
3-	Logout (item3): where you return back to login form.
7)	Activity Log: for every action is made from your application, it stores the action with time stamp into the cloud using Firebase Realtime database, in case of disconnection, it caches the last version of the cloud using SQLite.
8)	Profile Activity: Contains User Profile Picture, and Username and button logout.
9)	Action_Activity: for each action, implement its activity, for example, Temperature_Activity, which displays the current temperature from the electronic circuit via cloud (firebase).

## Hardware Requirements:
Your system must perform at least (4) actions / features of the embedded system.
Note: If the network is disconnected, we want to disconnect the circuit due to power consumption, for example: turn of led, fan, sensor reading.
Hardware Components:
1)	Breadboard.
2)	Jump wires.
3)	LED.
4)	ESP8266NodeMCU (recommended) or (ArduinoUno+ ESP8266 WiFiModule). Note: in case of ArduinoUno, (you might need a different library than described in laboratory).
5)	LCD.
6)	Keypad.
7)	Temperature Sensor.
