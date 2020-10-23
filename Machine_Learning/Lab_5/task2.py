import numpy as np
import pandas as pd

# Read in our data
file = "titanic.csv"
columns = ["Sex", "Pclass", "Survived"]
data = pd.read_csv(file, usecols=columns)

# Convert our sex feature to numerical (female = 0, male = 1)
data['Sex'].replace({'female': 0, 'male': 1}, inplace=True)

# Split data into features(x) and target(y)
x = data[["Sex", "Pclass"]]
y = data["Survived"]

# Split data into training and test data
train, test = train_test_split(data, test_size=0.2)

# Count the num of passenger, the num of survivors, and the num of casualties in the training data and calculate priors
trainingPassengerCount = len(train)
trainingSurvivorCount = len(train[train["Survived"] == 1])
trainingCasualtyCount = trainingPassengerCount - trainingSurvivorCount

# Priors
pSurvived = trainingSurvivorCount / trainingPassengerCount
pCasualty = trainingCasualtyCount / trainingPassengerCount

maleSurvivorCount = len(train[(train["Survived"] == 1) & (train["Sex"] == 1)])
maleCasualtyCount = len(train[(train["Survived"] == 0) & (train["Sex"] == 1)])
femaleSurvivorCount = len(train[(train["Survived"] == 1) & (train["Sex"] == 0)])
femaleCasualtyCount = len(train[(train["Survived"] == 0) & (train["Sex"] == 0)])

# Likelihoods using Laplace smoothing (which is not needed in this case but used anyway)
laplace = 10
pMaleSurvived = (maleSurvivorCount + laplace) / (trainingSurvivorCount + (laplace * 2))
pMaleCasualty = (maleCasualtyCount + laplace) / (trainingCasualtyCount + (laplace * 2))
pFemaleSurvived = (femaleSurvivorCount + laplace) / (trainingSurvivorCount + (laplace * 2))
pFemaleCasualty = (femaleCasualtyCount + laplace) / (trainingCasualtyCount + (laplace * 2))

class1SurvivorsCount = len(train[(train["Survived"] == 1) & (train["Pclass"] == 1)])
class2SurvivorsCount = len(train[(train["Survived"] == 1) & (train["Pclass"] == 2)])
class3SurvivorsCount = len(train[(train["Survived"] == 1) & (train["Pclass"] == 3)])
class1CasualtiesCount = len(train[(train["Survived"] == 0) & (train["Pclass"] == 1)])
class2CasualtiesCount = len(train[(train["Survived"] == 0) & (train["Pclass"] == 2)])
class3CasualtiesCount = len(train[(train["Survived"] == 0) & (train["Pclass"] == 3)])

pClass1Survived = (class1SurvivorsCount + laplace) / (trainingSurvivorCount + (laplace * 3))
pClass2Survived = (class2SurvivorsCount + laplace) / (trainingSurvivorCount + (laplace * 3))
pClass3Survived = (class3SurvivorsCount + laplace) / (trainingSurvivorCount + (laplace * 3))
pClass1Casualty = (class1CasualtiesCount + laplace) / (trainingCasualtyCount + (laplace * 3))
pClass2Casualty = (class2CasualtiesCount + laplace) / (trainingCasualtyCount + (laplace * 3))
pClass3Casualty = (class3CasualtiesCount + laplace) / (trainingCasualtyCount + (laplace * 3))

# Part A
# For each entry in the test data calculate posteriors
'''
maleSurvivor * class 1 survivor     pCasulaty
-------------------------------- > ----------
maleCasualty * class 1 clasuaty     pSurvivor   
'''