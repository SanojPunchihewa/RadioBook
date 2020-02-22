# RadioBook

## How to build and Run
```
git clone https://github.com/SanojPunchihewa/RadioBook.git
```
### NLP part
```
cd RadioBook/NLP
mvn clean compile
export MAVEN_OPTS="-Xmx14000m"
mvn exec:java -Dexec.mainClass="com.example.radiobook.BasicPipelineExample"
```
### TTS part
```
cd RadioBook/TTS
mvn clean compile
export MAVEN_OPTS="-Xmx14000m"
mvn clean compile  exec:java -Dexec.mainClass="TTS"
```

------------


## Gender Classifier
The gender classifier in this repository is a replacement for the approach exists in Standford NLP. The exisiting 
approach is to map from a gender-name list. Considering vast possibilities for human names considering the 
geography, culture and etc, the former approach is somewhat inefficient and impractical. Instead, using an 
inferred model of a well trained RNN classifier performs comperatively far better accuracy and coverage.

The classsifier algorithm consists of 2 main parts.

**1. Classifier Preproccesor**(Java with Maven)

The output from the NLP pipeline comprises of quotes related with corresponding speaker for them and additional attributes, in JSON format. A speaker can be repeated more than once. Thus, these need to preprocessed before input to the classifier. 
- **Input** - nlp_output.json
- **output** - speaker_list.txt
- **dependancies** - Gson librabry
	   
- **Usage**

  Compile with maven and run *ClassifierInputPreprocessor.java* with the *nlp_output.json* file in the root directory(GenderClassifier).   Once completed it will generate a *speaker_list.txt* to the root directory.

**2. RNN Classifier**(Python with Pytorch)([from Name2Gender](https://github.com/ellisbrown/name2gender/tree/master/rnn "RNN classifier"))

This [RNN classifier](https://github.com/ellisbrown/name2gender/tree/master/rnn "RNN classifier") is an opensource Python code from internet, modified for our use case. It takes a list of names and outputs the predicted gender set for each name with a certain probabilities. In our case, we have filtered out the predicted gender set to take the most prominent gender with highest probability from the model. RNN classifier has 2 use cases.
- **run_trainer.py** - The python script to train the model
- **run_predictor.py**
Once model is trained, this script can be used to predit with the inferred model or an user can directly use the existing inferred model in the repo with this.
- **input** - speaker_list.txt
- **output** - female_speakers.txt, male_speakers.txt
- **dependencies** - pytorch, nltk

- **Usage**

  In order to run the classifier there are few dependecies to be installed. For the easiness, we have exported an enviroment so that one   can easily clone it to their machine
  
     - Environment import with conda
     
	   `conda env create -f genderclassifierenv.yml`
     - Activate the new environment
     
	   `conda activate GenderClassifierRNN`
     - Train RNN model
     
	   `python run_trainer.py`
     - To predict using inferred model
     
	   `python run_predictor.py`
	   

## How to test your own story

Update the string in [line 17](https://github.com/SanojPunchihewa/RadioBook/blob/fc78a53223075fe7e1b69ec806da80209d466d00/NLP/src/main/java/com/example/radiobook/BasicPipelineExample.java#L17)
and use above method to build and run
