import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.model_selection import StratifiedKFold
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score

# Part A
# file = "D:/College_4-1/Machine_Learning/Lab_4/titanic.csv"
file = "titanic.csv"
columns = ["Sex", "Pclass", "Survived"]
data = pd.read_csv(file, usecols=columns)

# Convert our sex feature to numerical (female = 0, male = 1)
data['Sex'].replace({'female': 0, 'male': 1}, inplace=True)
# .map could also be used

# Split data into features(x) and target(y)
x = data[["Sex", "Pclass"]]
y = data["Survived"]

# Part B
totalPassengerCount = len(y)
# counts can also be done using len(data[target==1])

survivorCount = len(data[y == 1])
casualtyCount = totalPassengerCount - survivorCount
print("There were %d total passengers" % totalPassengerCount)
print("There were %d survivors" % survivorCount)
print("There were %d casualties" % casualtyCount)

# Part C
skf = StratifiedKFold(n_splits=min(survivorCount, casualtyCount), shuffle=True)  # Leave one out splits
# skf = StratifiedKFold(n_splits=2) specific n splits

# For loop, for train_index, test_index in kf.split(data, target)
# Part D
i = 1
for trainIndex, testIndex in skf.split(x, y):
    print("Iteration %d" %i)
    print(trainIndex)
    print()
    print(testIndex)
    print()

    model = KNeighborsClassifier()
    model.fit(x.iloc[trainIndex], y.iloc[trainIndex])
    predictedLabels = model.predict(x.iloc[testIndex])

    print(y[testIndex].to_numpy())
    print()
    print(predictedLabels)
    print()
    print()
    i = i + 1
'''
Below method works, but lacks flexibility and doesn't really fit what we need for task 2
X_train, X_test, y_train, y_test = train_test_split(x, y.iloc[:, 0], test_size=0.2, random_state=1, stratify=y)
knn = KNeighborsClassifier(n_neighbors=2)
knn.fit(X_train, y_train)
predictions = knn.predict(X_test)
print(accuracy_score(y_test, predictions))
print(cross_val_score(knn, X_train, y_train, cv=skf, scoring='accuracy'))'''
