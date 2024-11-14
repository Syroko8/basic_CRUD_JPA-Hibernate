# basic_CRUD_JPA-Hibernate
This is a basic example of using JPA (Java Persistence API) and Hibernate in a Java program that allows us to perform CRUD operations on instances of authors and books, managing their data in an SQL database. The user interface is built using Java Swing libraries.

# Instructions
To run the project, the first thing you need to do is modify the "persistence.xml" file. This file can be found in the "META-INF" folder, within the "resources" directory.
In this file, you must modify the username that will be used by the system to access and modify the databases (root user, for example), as well as the password. This user must have the necessary permissions to allow the program to create and make changes to the database.

To run the program, execute the "Main" class, which is located directly in the "example" directory, under "com" and "java".
This class will instantiate the necessary classes and display the main view of the program, from where you can navigate through the interface freely.

# General Structure
In this project, the classes are organized into three packages, except for the "Main" class, which is directly in "example".

The "**controller**" package contains the two controllers we will use as communication bridges between the "AutorDAO", "LibroDAO" files and the rest of the files.
In the "**model**" package, we find the previously mentioned "LibroDAO" and "AutorDAO" classes, along with the "Autor" and "Libro" classes, which represent the database instances. These classes are used to create objects that allow us to retrieve and manipulate data from the database.

- Finally, the "**view**" package contains the views, along with two interfaces and a class used for switching between views.
