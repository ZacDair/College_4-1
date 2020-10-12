# Lab-3 Task 1
# R00159222 Zac Dair

import matplotlib.pyplot as plt
import numpy as np


# Split our data generation into a function so we can import and call it in task 2
def generateData(clutserCount, datapointCount, variance, outlierCount):
    # Part A:
    # Set a cluster amount, and define the cluster shape (x, y) == 2
    clusters = np.random.rand(clutserCount, 2)

    # Part B & C:
    # For each cluster add normally distributed noise, and an ID to reference which cluster its from
    data = np.array([[]])
    target = np.array([[]], dtype='int')

    # Loop through our clusters, adding our centroid to our random vector multiplied by our variance
    index = 0
    while index < clutserCount:
        datapoints = clusters[index, :] + (variance * np.random.randn(datapointCount, 2))
        data = np.append(data, datapoints).reshape((index + 1)*datapointCount, 2)
        target = np.append(target, [index]*datapointCount)
        index = index + 1

    # Part D:
    # Add m outliers uniformly distributed in the unit box with random labels
    outliers = np.random.rand(outlierCount, 2)
    outliersLabels = np.random.randint(0, clutserCount, outlierCount)

    data = np.append(data, outliers).reshape(clutserCount*datapointCount+outlierCount, 2)
    target = np.append(target, outliersLabels)
    return data, target


# Plots and displays our data when needed
def showScatterGraph(dataset, labels):
    plt.figure()
    plt.scatter(dataset[:, 0], dataset[:, 1], c=labels)
    plt.show()


# Run our functions, generates the dataset and plots our data (un-comment to see working)
# data, target = generateData(2, 100, 0.03, 25)
# showScatterGraph(data, target)
