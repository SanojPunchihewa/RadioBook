# RadioBook

## How to build and Run
```
git clone https://github.com/SanojPunchihewa/RadioBook.git
cd RadioBook/NLP
mvn clean compile
export MAVEN_OPTS="-Xmx14000m"
mvn exec:java -Dexec.mainClass="com.example.radiobook.BasicPipelineExample"
```

## How to test your own story

Update the string in [line 17](https://github.com/SanojPunchihewa/RadioBook/blob/fc78a53223075fe7e1b69ec806da80209d466d00/NLP/src/main/java/com/example/radiobook/BasicPipelineExample.java#L17)
and use above method to build and run
