# Lab-3 Task 2
# R00159222 Zac Dair

import matplotlib.pyplot as plt
import numpy as np
from sklearn import svm
import task1

# Use task 1 to generate our dataset and labels
dataset, labels = task1.generateData(4, 100, 0.03, 10)

# Part A:
# Create our Support Vector Machine
model = svm.SVC()
# Fit our model to our data
model.fit(dataset, labels)

# Part B:
# Create the matrix - First split our datapoints into a min/max
xMin = min(dataset[:, 0])
yMin = min(dataset[:, 1])
xMax = max(dataset[:, 0])
yMax = max(dataset[:, 1])

# Defines how many data points are on our matrix (100 == 0.01)
matrixCellSize = 0.01
x, y = np.meshgrid(np.arange(xMin, xMax, matrixCellSize), np.arange(yMin, yMax, matrixCellSize))
xy = np.array([x.flatten(), y.flatten()]).transpose()

# Part C:
# Test prediction on 0.48349683 0.57498938 should be 1
prediction = model.predict(xy)
prediction = prediction.reshape(x.shape)
plt.figure()
plt.imshow(prediction, extent=(xMin, xMax, yMin, yMax), alpha=0.4, origin="lower")
plt.scatter(dataset[:, 0], dataset[:, 1], c=labels)
plt.show()

# Part D:
# Reshape predictions into a 10x10 matrix and visualise the labels for the areas in the unit box as an image.
# (Hint: use matplotlib.pyplot.imshow)
