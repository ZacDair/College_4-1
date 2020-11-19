import pandas as pd
import matplotlib.pyplot as plt
import time

from sklearn import model_selection, linear_model, metrics, svm


# Prints a count for each class, and displays some examples of each class
def displayDataInfo(features, lables):
    # Count our labels (0 for sneaker, 1 for ankle boot)
    sneakerCount = len(lables[lables == 0])
    bootCount = len(lables[lables == 1])
    print("The dataset consists of", sneakerCount, "sneakers and", bootCount, "ankle boots.")

    # Display 2 of each class (currently first two, might be nice to go random)
    plt.figure()
    for label in range(2):
        for figIndex in range(2):
            plt.subplot(2, 2, label * 2 + figIndex + 1)
            plt.imshow(features[lables == label][figIndex].reshape(28, 28))
    plt.show()


# Reads in our csv dataset using a size value to determine how much is retrieved (returns features and labels)
def retrieveData(sampleSize):
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

        return dataFeatures, dataLabels


# Calculates the min, max and average of a list of values
def calculateMinMaxAvg(values):
    return min(values), max(values), sum(values) / len(values)


# Train and Test Model using K-folds
def trainAndRunModel(model, featureData, labelData, kFold, verbosity):
    # Lists to store timings and accuracy values
    predictTimes = []
    trainTimes = []
    accuracies = []

    # Train Test Splits for Cross Validation
    for trainIndex, testIndex in kFold.split(featureData):

        # Fit model to the data
        trainTimeStart = time.time()
        model.fit(featureData[trainIndex], labelData[trainIndex])
        trainTimeEnd = time.time()

        # Get predictions
        predictionTimeStart = time.time()
        predictions = model.predict(featureData[testIndex])
        predictionTimeEnd = time.time()

        # Get accuracy score and confusion matrix
        score = metrics.accuracy_score(labelData[testIndex], predictions)
        confusionMatrix = metrics.confusion_matrix(labelData[testIndex], predictions)
        if verbosity:
            print("\nPerceptron Accuracy:", score)
            print("Training Time:", trainTimeEnd - trainTimeStart)
            print("Prediction Time:", predictionTimeEnd - predictionTimeStart)
            print("Confusion Matrix:")
            print(confusionMatrix)

        trainTimes.append(trainTimeEnd - trainTimeStart)
        predictTimes.append(predictionTimeEnd - predictionTimeStart)
        accuracies.append(score)

    minTrainTime, maxTrainTime, avgTrainTime = calculateMinMaxAvg(trainTimes)
    minPredictTime, maxPredictTime, avgPredictTime = calculateMinMaxAvg(predictTimes)
    minAccuracy, maxAccuracy, avgAccuracy = calculateMinMaxAvg(accuracies)

    print("\nTraining Time:")
    print("Min:", minTrainTime, "\nMax:", maxTrainTime, "\nAvg:", avgTrainTime)
    print("\nPrediction Time:")
    print("Min:", minPredictTime, "\nMax:", maxPredictTime, "\nAvg:", avgPredictTime)
    print("\nAccuracy:")
    print("Min:", minAccuracy, "\nMax:", maxAccuracy, "\nAvg:", avgAccuracy)


# Retrieve the full dataset
datasetFeatures, datasetLabels = retrieveData(0)

# Display info about the full dataset
displayDataInfo(datasetFeatures, datasetLabels)

# K-fold init
kf = model_selection.KFold(n_splits=10, shuffle=True)

# Define our perceptron model
perceptron = linear_model.Perceptron()

# Define various support vector machine models
svm1 = svm.SVC(kernel='linear')
svm2 = svm.SVC(kernel='rbf', gamma=1e-1)
svm3 = svm.SVC(kernel='rbf', gamma=1e-2)
svm4 = svm.SVC(kernel='rbf', gamma=1e-3)
svm5 = svm.SVC(kernel='rbf', gamma=1e-4)

models = [perceptron, svm1, svm2, svm3, svm4, svm5]

# Loop through each different model
for m in models:
    # Loop through 8 different amounts of the dataset (0 means all)
    sizeList = [10, 50, 100, 500, 1000, 5000, 10000, 0]
    for size in sizeList:

        # Get our new dataset (changes in size)
        dFeatures, dLabels = retrieveData(size)
        if type(m).__name__ == "Perceptron":
            modelName = "Perceptron"
        else:
            modelName = "SVM(" + m.kernel + " Gamma: " + str(m.gamma) + ")"
        print("\nModel:", modelName, "Running with a Dataset of", len(dLabels), "entries.")

        # Fit and run predictions on our model
        trainAndRunModel(m, dFeatures, dLabels, kf, False)

# Get mean of gamma value accuracies for svm's to disern the best svm gamme value