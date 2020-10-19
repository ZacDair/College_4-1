import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.model_selection import StratifiedKFold
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score, confusion_matrix
import matplotlib.pyplot as plt

file = "titanic.csv"
columns = ["Sex", "Pclass", "Survived"]
data = pd.read_csv(file, usecols=columns)

# Convert our sex feature to numerical (female = 0, male = 1)
data['Sex'].replace({'female': 0, 'male': 1}, inplace=True)

# Split data into features(x) and target(y)
x = data[["Sex", "Pclass"]]
y = data["Survived"]


totalPassengerCount = len(y)
survivorCount = len(data[y == 1])
casualtyCount = totalPassengerCount - survivorCount
print("There were %d total passengers" % totalPassengerCount)
print("There were %d survivors" % survivorCount)
print("There were %d casualties" % casualtyCount)

skf = StratifiedKFold(n_splits=min(survivorCount, casualtyCount), shuffle=True)  # Leave one out splits
ROC_X = []
ROC_Y = []

for k in range(1, 10):
    trueCas = []
    trueSurv = []
    falseCas = []
    falseSurv = []
    for trainIndex, testIndex in skf.split(x, y):
        model = KNeighborsClassifier()
        model.fit(x.iloc[trainIndex], y.iloc[trainIndex])
        predictedLabels = model.predict(x.iloc[testIndex])

        C = confusion_matrix(y[testIndex].to_numpy(), predictedLabels)

        trueCas.append(C[0, 0])
        trueSurv.append(C[1, 1])
        falseCas.append(C[1, 0])
        falseSurv.append(C[0, 1])

    print("K = ", k)
    print("True Casualties: ", np.sum(trueCas))
    print("True Survivors: ", np.sum(trueSurv))
    print("False Casualties: ", np.sum(falseCas))
    print("False Survivors: ", np.sum(falseSurv))

    ROC_X.append(np.sum(falseSurv))
    ROC_Y.append(np.sum(trueSurv))

print(ROC_X)
print(ROC_Y)

plt.figure()
plt.scatter(ROC_X, ROC_Y)
plt.axis([0, np.max(ROC_X), 0, np.max(ROC_Y)])
plt.show()




