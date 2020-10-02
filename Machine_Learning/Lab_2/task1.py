# Lab-2 Task 1
# R00159222 Zac Dair
# Analysis of the bike sharing dataset (day.csv)

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D


def partA():
    # List of attributes we care about
    attributeNames = ["casual", "registered", "holiday"]

    # Load our dataset
    data = pd.read_csv("day.csv", usecols=attributeNames)

    # Get the mean of the casual or registered column where holiday is 0 and 1
    casualHol = data.loc[data['holiday'] == 1, 'casual'].mean()
    casualNonHol = data.loc[data['holiday'] == 0, 'casual'].mean()
    regHol = data.loc[data['holiday'] == 1, 'registered'].mean()
    regNonHol = data.loc[data['holiday'] == 0, 'registered'].mean()

    # Print our averages
    print("Average casual rentals on a holiday: ", casualHol)
    print("Average casual rentals on a normal day: ", casualNonHol)
    print("Average registered rentals on a holiday: ", regHol)
    print("Average registered rentals on a normal day: ", regNonHol)

    '''From running the above function we can see that there are more registered rentals on a regular day,
       and on average the lowest amount of casual rentals on a normal day'''


def partB():
    # List of attributes we care about
    attributeNames = ["temp", "atemp"]

    # Load our dataset
    data = pd.read_csv("day.csv", usecols=attributeNames)

    # Get the min and max for each column
    tempMin = np.min(data['temp'])
    tempMax = np.max(data['temp'])
    atempMin = np.min(data['atemp'])
    atempMax = np.max(data['atemp'])

    # Print our min/max values
    print("Real Temperatures in Celsius: \nMin:", tempMin, "\nMax:", tempMax, "\n")
    print("Feeling Temperatures in Celsius: \nMin:", atempMin, "\nMax:", atempMax)


def partC():
    # List of attributes we care about
    attributeNames = ["dteday", "casual", "registered"]

    # Load our dataset
    data = pd.read_csv("day.csv", usecols=attributeNames)

    # Store the days where there are more casual than registered renters
    data = data[data["casual"] > data["registered"]]

    # Rename date column from dteday to date just cos :D
    data.rename(columns={'dteday':'Date', 'casual':'Casual', 'registered': 'Registered'}, inplace=True)

    # Print our results without the index for style :D
    print("Table of days where there are more casual than registered rentals:\n")
    print(data.to_string(index=False))


def partD():
    # List of attributes we care about
    attributeNames = ["temp", "atemp", "casual", "registered"]

    # Load our dataset
    data = pd.read_csv("day.csv", usecols=attributeNames)

    # Sort by temperature
    data = data.sort_values(by=["temp"])

    # Create the graphs one for actual temp, one for temp feel
    plt.subplot(211)
    plt.title('Temperature / Causal and Registered Rentals')
    plt.scatter(data['casual'], data['temp'], label="Casual")
    plt.scatter(data['registered'], data['temp'], label="Registered")
    plt.legend(loc=0)

    plt.subplot(212)
    plt.title('Temperature Feel / Causal and Registered Rentals')
    plt.scatter(data['casual'], data['atemp'], label="Casual")
    plt.scatter(data['registered'], data['atemp'], label="Registered")
    plt.legend(loc=0)
    plt.show()

    '''From running the above function we can see in the scatter graph wider distribution of registered rentals, with
    an increasing amount of rentals as the temperature increases'''


def partE():
    # List of attributes we care about
    attributeNames = ["temp", "hum", "windspeed", "casual"]

    # Load our dataset
    data = pd.read_csv("day.csv", usecols=attributeNames)

    # Split our dataset based on the median of the casual column
    casualMedian = np.median(data['casual'])
    busyDays = data[data["casual"] > casualMedian]
    quietDays = data[data["casual"] < casualMedian]

    # 3D plot
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(quietDays['temp'], quietDays['hum'], quietDays['windspeed'], color='g', label="Quiet")
    ax.scatter(busyDays['temp'], busyDays['hum'], busyDays['windspeed'], color='r', label="Busy")
    plt.legend(loc=0)
    plt.title("Temp, Hum, Windspeed / Casual Rentals split by busyness (Median: " + str(casualMedian) + ")")
    plt.show()


# All Questions run one after another using these function calls
partA()
partB()
partC()
partD()
partE()