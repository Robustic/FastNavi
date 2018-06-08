# FastNavi
FastNavi is a navigator tool. With FastNavi it is possible to draw network of the roads to the screen and find the fastest route from the any location to the any other location.

There is two part of the functionality in the FastNavi application:

    Navigator
Radio button is switched to "Navigator". First it is asked to snapp start location. When start location is snapped, it will change its color to the green. After that it is asked to snapp end location. End location will change its color to the red. If route between locations is founded, route is marked as blue path. The path is the shortest route between start and end locations.

    Draw
Radio button is switched to "Draw". New roads can be drawn with GUI. First it is asked to snapp start location. Start location for the new road can be existing location point or it can be freely chosen new location. After that that it is asked to snapp end location. End location for the new road can be existing location point or it can be freely chosen new location. When start and end locations are given, the new road is added. Added roads are utilized when "Navigator" part of the application is used.

Network of the roads can be writen to the file with .txt -format when "Write file" button is pushed. File is named as "Network.txt". 
When FastNavi application is launched, "Network.txt" file is read and network of the roads is drawn to the screen. If "Network.txt" file cannot be found, blank network is used.

There is used background image. There is no any effect with background image to the functionality of the application.

FastNavi is programmed with Java and GUI is programmed with JavaFX.
