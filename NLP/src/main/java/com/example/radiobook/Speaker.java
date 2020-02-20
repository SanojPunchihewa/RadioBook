package com.example.radiobook;

public class Speaker {

    enum Gender {
        UNKNOWN,
        FEMALE,
        MALE
    }

    private int speakerCount = 0;
    private int malePronounCount = 0;
    private int femalePronounCount = 0;
    private int unknownGenderCount = 0;
    private int femaleGenderCount = 0;
    private int maleGenderCount = 0;

    public void incrementSpeakerCount() {
        this.speakerCount++;
    }

    public void incrementFemalePronounCount() {
        this.femalePronounCount++;
    }

    public void incrementMalePronounCount() {
        this.malePronounCount++;
    }

    public void incrementUnknownGenderCount() {
        this.unknownGenderCount++;
    }

    public void incrementFemaleGenderCount() {
        this.femaleGenderCount++;
    }

    public void incrementMaleGenderCount() {
        this.maleGenderCount++;
    }

    public int getSpeakerCount() {
        return speakerCount;
    }

    public int getMalePronounCount() {
        return malePronounCount;
    }

    public int getFemalePronounCount() {
        return femalePronounCount;
    }

    public int getUnknownGenderCount() {
        return unknownGenderCount;
    }

    public int getFemaleGenderCount() {
        return femaleGenderCount;
    }

    public int getMaleGenderCount() {
        return maleGenderCount;
    }
}
