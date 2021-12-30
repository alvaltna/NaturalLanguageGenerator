# NaturalLanguageGenerator 

This software is an extension to Enterprise Architect modeling tool which generates natural language (human language) sentences from UML (Unified Modeling Language) class diagrams. 
By default software is capable of generating sentences in English and Estonian languages. 
In addition to that there is support for adding new languages through the usage of template. 
Template is used for creating a language file for a new language. 
Then the language file is used by the software to generate natural language sentences in that language.
#Instructions to install and use

Installation

1.	Download JDK (Java Development Kit) 11 or a newer version of JDK.
2.	Run file named SetJdkPath with a option 'Run as administrator' in software package folder (Software To Generate Natural Language Sentences folder).
3.	Copy in to the opened command line window JDK's bin folder path.
4.	Then open Enterprise Architect.   
Enterprise Architect 12 <br />
In Enterprise Architect 12 in user interface choose from the upper toolbar Project -> Data Management -> Import Reference Data. <br /> 
Choose file named LanguageGenerationScript.xml from software package folder. <br /> 
Then in Enterprise Architect choose Select Datasets to Import -> Datasets to Import ja click on button Import. <br /> 
Enterprise Architect 15 <br />
In Enterprise Architect 15 in user interface choose from upper toolbar Configure -> Transfer -> Import Reference Data. <br /> 
Choose from software package folder file named LanguageGenerationScript.xml. <br /> 
Then in Enterprise Architect choose Select Datasets to Import -> Datasets to Import and click on button Import. <br /> 

5. To run the software double click on RunProgram file inside software package folder. <br /> 

How to use

Enterprise Architect 12 <br /> 

Sentences can be generated only from packages and diagrams. <br /> 
By default software script writes it's output into user's 'C:\Users\user\AppData\Local\Temp' folder into file named file.json where 'user' is the machine' user. <br /> 
In order to change the output file in user has to open Enterprise Architect 12 user interface and choose from the upper toolbar 'View' -> 'Scripting'. <br /> 
In the opened window select 'Lausete_Genereerimise_skriptid' and from inside that NaturalLanguageGeneration script. <br /> 
Double clicking on that opens the contents of the script in user interface. <br /> 
 
Enterprise Architect 15 <br /> 

In Enterprise Architect 15 in user interface choose from upper toolbar 'Specialize' -> 'Tools' -> 'Script Library'. <br />  
Inside 'Scripting tab' window choose 'Lausete_Genereerimise_skriptid' and inside that NaturalLanguageGeneration script. <br /> 
Double clicking on that opens the contents of the script in user interface. <br /> 

Enterprise Architect 12 and 15 <br />

Inside a script there is a line
'Set objOutFile = fso.CreateTextFile(tempFolderPath & "\file.json", True)'. <br /> 
On this line 'CreateTextFile()' method's first input must be replaced with the path of the file where user intends to write the script's JSON output. <br /> 
In order to run the back-end application file named RunProgram must be run inside software package folder. <br /> 
For the software to work properly in the back-end application the path to read input data must be the same as the path chosen inside the script. <br /> 
By default when running the software for the first time script's output path and back-end application's path to read script's output always match and are set as 'C:\Users\user\AppData\Local\Temp' where 'user' is the user of the machine. <br /> 
When the user changes script's output file path same path must be chosen in back-end application by clicking on the button 'Choose file input' and selecting it graphically. <br /> 
Now back-end application is reading the input data from the right file. <br /> 

In order to generate sentences from package or diagram. In Enterprise Architect 'Project Browser' make a right mouse click on the chosen package or diagram. <br /> 

Enterprise Architect 12 <br />

From the opened menu choose 'Scripts' and 'NaturalLanguageGeneration'. <br /> 

Enterprise Architect 15 <br />

From the opened menu choose 'Specialize' -> 'Scripts' and 'NaturalLanguageGeneration'. <br /> 

Enterprise Architect 12 and 15 <br />

By default sentences are generated in English language. <br/> 
To add a suitable language in back-end application user interface click on button 'Add a new language'. <br/> 
Inside the opened view all the words in English must be translated to the chosen language. <br/> 
One must try to translate every word and phrase as accurately as possible. <br/> 
Do not remove any '%s' symbols these are needed for data input later. <br/> 
Translated fields can be saved by clicking on button 'Save a new language'. <br/>  
Translated fields are saved into a text file with the name user chose in a pop-up window. <br/> 
For example if user chose name 'Estonian' translated fields will be saved inside a text file called 'Estonian.txt'. <br/> 
Later user can continue translating the fields inside the text file or change/modify them if necessary. <br/> 
If user saves a new language with name that already exists then that text file will be overwritten. <br/> 
After adding a new language there will be new language choice at home view. <br/> 
By clicking on a language choice the sentences will be translated to that language. <br/> 
Completely correct grammar is not guaranteed. <br/> 
For example in Estonian language the words that are inserted into sentences from the script's output file do not have the correct word endings. <br/> 
By clicking on a button 'Delete language' the user is taken to a new view. <br/> 
There is a choice for deleting added languages. <br/> 
By clicking on a button 'Remove' that text file will be deleted. <br/> 
Generating a large amount of sentences from a lot of packages the program may take a bit time. <br/> 
 

#License
Copyright 2021 Alvar Valtna

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

