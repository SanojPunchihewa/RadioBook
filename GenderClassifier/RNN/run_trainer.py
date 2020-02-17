from data_util import split_dataset, PROJECT_DIR
from train import train

DATASET = PROJECT_DIR + "data/AllNameList.csv" # this is the preshuffled dataset
WEIGHTS = PROJECT_DIR + "weights/nb/naive_bayes_weights_jup"

# split the dataset into 70% train, 0%  val, 30% test
TRAINSET, VALSET, TESTSET = split_dataset(0.75, 0.001)

train(TRAINSET)
