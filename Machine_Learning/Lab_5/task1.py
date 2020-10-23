import numpy as np
import pandas as pd

# Part A
# Read in our data
file = "titanic.csv"
columns = ["Sex", "Pclass", "Survived"]
data = pd.read_csv(file, usecols=columns)

# Convert our sex feature to numerical (female = 0, male = 1)
data['Sex'].replace({'female': 0, 'male': 1}, inplace=True)

# Split data into features(x) and target(y)
x = data[["Sex", "Pclass"]]
y = data["Survived"]

# Part B
# Split data into training and test data
train, test = train_test_split(data, test_size=0.2)

# Part C
# Count the num of passenger, the num of survivors, and the num of casualties in the training data and calculate priors
trainingPassengerCount = len(train)
trainingSurvivorCount = len(train[train["Survived"] == 1])
trainingCasualtyCount = trainingPassengerCount - trainingSurvivorCount

# Priors
pSurvived = trainingSurvivorCount / trainingPassengerCount
pCasualty = trainingCasualtyCount / trainingPassengerCount

print("Total Passengers: ", trainingPassengerCount)
print("Total Survivors: ", trainingSurvivorCount)
print("Total Casualties: ", trainingCasualtyCount)
print("Prior Survived:", pSurvived)
print("Prior Casualty:", pCasualty)

# Part D
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

print("Male Survivors:", maleSurvivorCount, "\nMale Survivor likelihood:", pMaleSurvived)
print("Female Survivors:", femaleSurvivorCount, "\nFemale Survivor likelihood:", pFemaleSurvived)
print("Male Casualties:", maleCasualtyCount, "\nMale Casualties likelihood:", pMaleCasualty)
print("Female Casualties:", femaleCasualtyCount, "\nFemale Casualties likelihood:", pFemaleCasualty)

# Part E
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

print("Class 1 Survivors:", class1SurvivorsCount, "\nClass 1 Survivor likelihood:", pClass1Survived)
print("Class 2 Survivors:", class2SurvivorsCount, "\nClass 2 Survivor likelihood:", pClass2Survived)
print("Class 3 Survivors:", class3SurvivorsCount, "\nClass 3 Survivor likelihood:", pClass3Survived)
print("Class 1 Casualties:", class1CasualtiesCount, "\nClass 1 Casualty likelihood:", pClass1Casualty)
print("Class 2 Casualties:", class2CasualtiesCount, "\nClass 2 Casualty likelihood:", pClass2Casualty)
print("Class 3 Casualties:", class3CasualtiesCount, "\nClass 3 Casualty likelihood:", pClass3Casualty)
