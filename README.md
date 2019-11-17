# JHaikuGenerator
A Java-based program which generates Haiku-style poems using words from an uploaded file.

## SYSTEM REQUIREMENTS
To use this software, your computer will need the latest version of Java and internet connectivity.

## DOWNLOADING THE SOFTWARE
To download and use this software, select the "dist" folder of the repository and select the distribution that you wish to use.
Current versions:
  .exe - 1.0.5
  .jar - 1.0.5

## HOW TO USE
To use this software to generate Haiku-style poems, the user will need to select a text file for the program to use. From there, select the language the text file is written in, a style of generation, and whether or not each line should be generated from where the last one ended. 

## VERSION HISTORY
### Version 1.0.5
Implemented an error logger, fixed issues with executables throwing JNI errors upon starting. The program should be compatible with publically available JREs (1.8.0--).

### Version 1.0.0
The code base is fully functional when compiled. The progress bar now sufficiently indicates the progress of generation (reading and generation now measured, where only reading was measured in the past). An icon has been implemented into the code, allowing the program to display an icon for its instance.
Future changes include implementing more language sources, which will allow for the use of more diverse texts in generating haikus.

### Version:  0.9.5
The code base is now fully functional. When compiled, it will read standard text files and return generated haikus reliably. At this point, only minor changes remain to be done. An icon needs to be designed, other languages need to be implemented, and more indication of the program's progress in generation is needed. 

### Version:  0.8.0
As of Nov. 9, 2019, the program's code has been implemented in such a way that it improves performace. However, there is a known issue where words are truncated by the code when attempting to generate poems. The progress bar component still needs to be implemented as a representation of the program's progress in generating peoms. Further performance improvements are needed.

### Version:  0.6.5
As of Nov. 6, 2019, the program has most of the objects and code which it needs to function. However, that code has not been implemented such that it would function.
