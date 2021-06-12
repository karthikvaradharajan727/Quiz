# Quiz
# Design pattern - MVVM
# Programming Language - Kotlin
# Library - Flexbox layout
# Classes:
QuizActivity class has all the view layers, used view binding
QuizAdapter class has adapter common for both answer and option as per the design
QuizRepository class holds the data IO operations. In future, we can add webservice integration in the class this will leed to no change in Viewmodel class
QuizViewModel class holds the resposibility of business logic of the application and convert the data to show in UI and this class is the deciding factor of the complete module
