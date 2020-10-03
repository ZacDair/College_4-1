# Lab-2 Task 2
# R00159222 Zac Dair
# Analysis of the Titanic passenger dataset (titanic.csv)

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D


def partA():
    # Load our dataset
    data = pd.read_csv("titanic.csv", usecols=["Survived"])

    # Count where survived == 1 (the passenger survived)
    survived = int(data[data['Survived'] == 1].count())

    # Length of the data array is the amount of passengers
    totalPassengers = len(data)

    # Survival percentage calculation and displaying the result
    percentage = 100 * survived/totalPassengers
    print("There were", totalPassengers, "passengers on the Titanic.", survived, "passengers survived, which is",
          percentage, '%')


def partB():
    # Load our dataset
    data = pd.read_csv("titanic.csv", usecols=["Survived", "Sex"])

    # Store the total passengers needed to calculate survival rate
    totalPassengers = len(data)

    # Convert out dataset to only store the survived passengers
    data = data[data['Survived'] == 1]

    # Delete the survived column as we don't need it anymore
    del data['Survived']

    # From our remaining dataset entries count each female
    femaleCount = int(data[data['Sex'] == 'female'].count())

    # Minus the female survival count from the total giving us the males
    maleCount = len(data) - femaleCount

    # Survival percentage calculation and displaying the result
    percentageF = 100 * femaleCount / totalPassengers
    percentageM = 100 * maleCount / totalPassengers

    print("There were", totalPassengers, "passengers on the Titanic.")
    print(maleCount, "males and", femaleCount, "females survived.")
    print("Male survival rate: "+str(percentageM)+"%")
    print("Female survival rate: "+str(percentageF)+"%")

    # From the above function just over twice the amount of females survived than males


def partC():
    # Load our dataset
    data = pd.read_csv("titanic.csv", usecols=["Survived", "Fare"])

    # Get the mean of the Fare column where survived is true (1) and then again for survived is false (0)
    avgSurvivedFare = data.loc[data['Survived'] == 1, 'Fare'].mean()
    avgNonSurvivedFare = data.loc[data['Survived'] == 0, 'Fare'].mean()

    # Print out our results
    print("The average fare for the survivors is: ", avgSurvivedFare)
    print("The average fare for the non-survivors is: ", avgNonSurvivedFare)


def partD():
    # Load our dataset
    data = pd.read_csv("titanic.csv", usecols=["Survived", "Name", "Age", "Embarked"])

    # Set our conditions (Survived == 1, Embarked == 'Q')
    survived = data['Survived'] == 1
    fromQueenstown = data['Embarked'] == 'Q'

    # Drop all entries that have no name or age
    data = data.dropna(subset=['Age'])

    # Convert our dataset to only store the entries matching the above conditions
    # UserWarning: Boolean Series key will be reindexed to match DataFrame index. data = data[fromQueenstown] ???
    data = data[survived & fromQueenstown]

    # Store the name and age of these survivors
    shortData = data[['Name', 'Age']]
    print(shortData)
    print("\nThe above data will be now written to the titanic_short.csv file...")
    shortData.to_csv('titanic_short.csv', index=False)


# All Questions run one after another using these function calls
partA()
partB()
partC()
partD()
