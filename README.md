# RadioBook

## How to build and Run
```
git clone https://github.com/SanojPunchihewa/RadioBook.git
cd RadioBook/NLP
mvn clean compile
export MAVEN_OPTS="-Xmx14000m"
mvn exec:java -Dexec.mainClass="com.example.radiobook.BasicPipelineExample"
```

------------


## Gender Classifier
The gender classifier in this repository is a replacement for the approach exists in Standford NLP. The exisiting 
approach is to map from a gender-name list. Considering vast possibilities for human names considering the 
geography, culture and etc, the former approach is somewhat inefficient and impractical. Instead, using an 
inferred model of a well trained RNN classifier performs comperatively far better accuracy and coverage.

The classsifier algorithm consists of 2 main parts.
1.  **Classifier Preproccesor**(Java with Maven)
The output from the NLP pipeline comprises of quotes related with corresponding speaker for them and additional attributes, in JSON format. A speaker can be repeated more than once. Thus, these need to preprocessed before input to the classifier.
       - Input - nlp_output.json
	   - output - speaker_list.txt
	   - dependancies -Gson librabry
	   
2. **RNN Classifier**(Python with Pytorch)
RNN classifier is an opensource Python code from internet modified for our use case. It takes a list of names and outputs the predicted gender set for each name with a certain probabilities. In our case, we have filtered out the predicted gender set to take the most prominent gender with highest probability from the model. RNN classifier has 2 use cases.
    - run_trainer.py - The python script train the model
    - run_predictor.py - Once model is used, this script can be used to predit with the inferred model or an user can directly use the existing inferred model in the repo with this.
    - dependencies - pytorch


## How to test your own story

Update the string in [line 17](https://github.com/SanojPunchihewa/RadioBook/blob/fc78a53223075fe7e1b69ec806da80209d466d00/NLP/src/main/java/com/example/radiobook/BasicPipelineExample.java#L17)
and use above method to build and run
