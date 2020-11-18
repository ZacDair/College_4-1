import pandas as pd
import matplotlib.pyplot as plt
import time

from sklearn import model_selection, linear_model, metrics


def preProcessingAndVisualization(sampleSize):
    # File reading with error checking
    foundFile = False
    filename = 'product_images.csv'
    data = pd.DataFrame
    try:
        # Try to load the csv data into a dataframe (use num rows to limit the size)
        if sampleSize <= 0:
            data = pd.read_csv(filename)
        else:
            data = pd.read_csv(filename, nrows=sampleSize)
        foundFile = True
    except FileNotFoundError:
        print("ERROR - Sorry the file:", filename, "could not be found!")
        exit(0)

    # If we have found and extracted the data, split, analyze, display and return
    if foundFile:

        # Isolate the labels column from the original data
        dataLabels = data['label']

        # Copy the original dataframe, minus the label column (leaves us with feature vectors)
        dataFeatures = data.drop(columns='label').to_numpy()

        # Count our labels (0 for sneaker, 1 for ankle boot)
        sneakerCount = len(dataLabels[dataLabels == 0])
        bootCount = len(dataLabels[dataLabels == 1])
        print("The dataset consists of", sneakerCount, "sneakers and", bootCount, "ankle boots.")

        # Display 2 of each class (currently first two, might be nice to go random)
        plt.figure()
        for label in range(2):
            for figIndex in range(2):
                plt.subplot(2, 2, label * 2 + figIndex + 1)
                plt.imshow(dataFeatures[dataLabels == label][figIndex].reshape(28, 28))
        plt.show()

        return dataFeatures, dataLabels


features, labels = preProcessingAndVisualization(0)

# K-fold init
kf = model_selection.KFold(n_splits=10, shuffle=True)

# Lists to store timings and accuracy values
predictTimes = []
trainTimes = []
accuracies = []

# Train Test Splits for Cross Validation
for trainIndex, testIndex in kf.split(features):
    # Initialise perceptron model
    perceptron = linear_model.Perceptron()

    # Fit model to the data
    trainTimeStart = time.time()
    perceptron.fit(features[trainIndex], labels[trainIndex])
    trainTimeEnd = time.time()

    # Get predictions
    predictionTimeStart = time.time()
    predictions = perceptron.predict(features[testIndex])
    predictionTimeEnd = time.time()

    # Get accuracy score and confusion matrix
    score = metrics.accuracy_score(labels[testIndex], predictions)
    confusionMatrix = metrics.confusion_matrix(labels[testIndex], predictions)
    print("\nPerceptron Accuracy:", score)
    print("Training Time:", trainTimeEnd - trainTimeStart)
    print("Prediction Time:", predictionTimeEnd - predictionTimeStart)
    print("Confusion Matrix:")
    print(confusionMatrix)

    trainTimes.append(trainTimeEnd - trainTimeStart)
    predictTimes.append(predictionTimeEnd - predictionTimeStart)
    accuracies.append(score)

# Calculate min, max, average train times, predict times, and accuracy
minTrainTime = min(trainTimes)
maxTrainTime = max(trainTimes)
avgTrainTime = sum(trainTimes) / len(trainTimes)

minPredictTime = min(predictTimes)
maxPredictTime = max(predictTimes)
avgPredictTime = sum(predictTimes) / len(predictTimes)

minAccuracy = min(accuracies)
maxAccuracy = max(accuracies)
avgAccuracy = sum(accuracies) / len(accuracies)

print("\n\nTraining Time:")
print("Min:", minTrainTime, "\nMax:", maxTrainTime, "\nAvg:", avgTrainTime)
print("\nPrediction Time:")
print("Min:", minPredictTime, "\nMax:", maxPredictTime, "\nAvg:", avgPredictTime)
print("\nAccuracy:")
print("Min:", minAccuracy, "\nMax:", maxAccuracy, "\nAvg:", avgAccuracy)