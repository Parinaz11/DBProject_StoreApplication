# Store Application
Using Java, JavaFX (a Java graphics library for our GUI) and MongoDB (for our database) we created a simple store application.

The database I created in mongodb, was named Store_Application and it had two collections named LoginInfo (each document containing Username, Password and an array called boughtProducts for a user) and Products (each document contianing Name, Price, Brand, Category, ImageID, itemsLeft and an array called reviews). 

After running the program, you are faced with a login/signUp page in which you are required to enter your username and password. Usernames and passwords are stored in our local database. If you enter the wrong password for a username or your username does not exist, you are not authorized to enter and therefore should click on the little signUp button to enter the signUp page. In this page the username and password that you enter is stored as a new document in LoginInfo collection and you enter the main page showing our products.

There are different categories such as All, makeup, cars, clothes, laptops, cellphones, etc. Also there is a search bar at the top of the page where you can search a part of the name for a product you want and it will show it to you. 

For each product you click on, you're able to see the information that is collected from our database for that product and can add the product to your cart (boughtProducts array) or write reviews for it which will also store in our database.

If you want to exit the program, simply click on the SignOut button to enter the login page and then click on the EXIT button.

# How to Run the Program
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
How? Open IntelliJ IDEA follow these steps:

File -> Project Structure -> Libraries

Then click on the plus sign above and select mongo-java-driver-3.5.0 JAR file that you downloaded. You're ready to go.
