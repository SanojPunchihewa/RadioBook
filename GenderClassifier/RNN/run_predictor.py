from data_util import split_dataset, PROJECT_DIR
from test import test, predict


def list_to_string(s):
    # initialize an empty string
    str1 = "\n"

    # return string
    return str1.join(s)


DATASET = PROJECT_DIR + "data/AllNameList.csv" # this is the preshuffled dataset
WEIGHTS = PROJECT_DIR + "weights/nb/naive_bayes_weights_jup"

# split the dataset into 70% train, 0%  val, 30% test
TRAINSET, VALSET, TESTSET = split_dataset(0.80, 0.001)

speaker_list = open("../speaker_list.txt", "r").read().splitlines()
predicted_genders = predict(names=speaker_list)
print(predicted_genders)

# split speakers into files by gender
male_speaker_list = []
female_speaker_list = []
for i in range(len(predicted_genders)):
    if predicted_genders[i] == 'm':
        male_speaker_list.append(speaker_list[i])
    else:
        female_speaker_list.append(speaker_list[i])

female_speakers_file = open("../female_speakers.txt", "w").write(list_to_string(female_speaker_list))
male_speakers_file = open("../male_speakers.txt", "w").write(list_to_string(male_speaker_list))

