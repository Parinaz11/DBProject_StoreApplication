# StoreApplication-in-java-using-mongodb-and-javafx

For this project we are going to make use of three tools;

Mongodb server

mongodb-java-driver 3.12.14

Scene builder

If you already have these tools installed on your system, you can go straight to clone and open this project on your system.

First, we are going to set up our mongodb server. Go to the link below and download mongodb 

https://www.mongodb.com/download-center#community

Now, go to the link below and follow the steps to configure and start your mongodb service

For windows,

https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/

For linux,

https://docs.mongodb.com/manual/administration/install-on-linux/

For mac,

https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/

Now that we have mongodb up and running, lets create our project. 

I will be making use of javafx, so we can create a javafx project and set it up.

Less i forget we will need scene builder to create our awesome GUI for the project. So if u don't have scene builder on your system, go to the link below and download it, 

http://gluonhq.com/products/scene-builder/

After you download it, integrate it to your IDE...

Go to http://code.makery.ch/library/javafx-8-tutorial/part1/ if you are using eclipse, 

Go to https://www.jetbrains.com/help/idea/opening-fxml-files-in-javafx-scene-builder.html if you are using intellij,

Go to https://docs.oracle.com/javase/8/scene-builder-2/work-with-java-ides/sb-with-nb.htm for netbeans 

We need a driver in our java application that will be able to communicate with the mongodb server right from our java application

There are different parkages out there but i will be using mongo-java-driver 3.5.0

Go to the link below to download it,

https://jar-download.com/?search_box=mongo-java-driver

What you need to do now is to add the mongo-java-driver 3.12.14 to your java library.
How? In IntelliJ IDEA: go to File -> Project Structure -> Libraries. Then click on the plus sign above and select mongo-java-driver-3.5.0 JAR file that you downloaded. You're ready to go.
