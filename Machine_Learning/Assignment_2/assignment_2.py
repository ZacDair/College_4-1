import pandas as pd
import matplotlib.pyplot as plt
import time
from sklearn import model_selection, linear_model, metrics, svm


# Prints a count for each class, and displays some examples of each class
def displayDataInfo(features, labels):
    # Count our labels (0 for sneaker, 1 for ankle boot)
    sneakerCount = len(labels[labels == 0])
    bootCount = len(labels[labels == 1])
    print("The dataset consists of", sneakerCount, "sneakers and", bootCount, "ankle boots.")

    # Display 2 of each class (currently first two, might be nice to go random)
    plt.figure()
    for label in range(2):
        for figIndex in range(2):
            plt.subplot(2, 2, label * 2 + figIndex + 1)
            plt.imshow(features[labels == label][figIndex].reshape(28, 28))
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
def trainAndRunModel(model, featureData, labelData, kFold, verbosity, mName):
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
            print("\nModel Accuracy:", score)
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

    print("Training Time:")
    print("Min:", minTrainTime, "\nMax:", maxTrainTime, "\nAvg:", avgTrainTime)
    print("\nPrediction Time:")
    print("Min:", minPredictTime, "\nMax:", maxPredictTime, "\nAvg:", avgPredictTime)
    print("\nAccuracy:")
    print("Min:", minAccuracy, "\nMax:", maxAccuracy, "\nAvg:", avgAccuracy)

    return {"Dataset Length": len(featureData), "Model": mName, "Train Time": avgTrainTime, "Predict Time": avgPredictTime, "Score": avgAccuracy}


# Retrieve the full dataset
datasetFeatures, datasetLabels = retrieveData(0)

# Display info about the full dataset
displayDataInfo(datasetFeatures, datasetLabels)

# K-fold init
kf = model_selection.KFold(n_splits=3, shuffle=True)

# Define our perceptron model
perceptron = linear_model.Perceptron()

# Define various support vector machine models (note rbf has no gamma value, this is defined during the loop below)
svmLinear = svm.SVC(kernel='linear')
svmRBF = svm.SVC(kernel='rbf')


models = [perceptron, svmLinear, svmRBF]
modelResults = []

# Dict to store the gamma and it's accuracy (we keep a running total of the accuracy of each gamma value)
gammaAccuracies = {}

# Define a list of sample sizes (these represent the amount of entries we take from the dataset)
# sampleSizes = [10, 50, 100, 500, 1000, 5000, 10000, 0]
sampleSizes = [10, 50, 100, 500, 1000, 2000]
# Loop through each different size
for size in sampleSizes:

    # Get our new dataset (changes in size)
    dFeatures, dLabels = retrieveData(size)

    # Loop through each different model
    for m in models:

        # If our model is the Perceptron we only run once
        if type(m).__name__ == "Perceptron":
            modelName = "Perceptron"

            # Fit and run predictions on our model
            print("\nModel:", modelName, "Running with a Dataset of", len(dLabels), "entries.")
            modelResults.append(trainAndRunModel(m, dFeatures, dLabels, kf, False, modelName))

        # Else our model will be an SVM (check if it's using rbf as kernel)
        elif m.kernel == "rbf":

            # Store a list of possible gamma values
            gammaValues = [1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10]

            # Loop through our possible gamma values, training and testing each RBF SVM with the new gamma
            for gamma in gammaValues:
                modelName = "SVM[RBF, Gamma:" + str(gamma) + "]"
                m.gamma = gamma

                # Fit and run predictions on our model
                print("\nModel:", modelName, "Running with a Dataset of", len(dLabels), "entries.")
                resultDict = trainAndRunModel(m, dFeatures, dLabels, kf, False, modelName)

                # Add the current gamma score to the sum of the previous scores (running total)
                if gamma in gammaAccuracies.keys():
                    gammaAccuracies[gamma] += resultDict["Score"]
                else:
                    gammaAccuracies[gamma] = resultDict["Score"]

                modelResults.append(resultDict)

        # Else our model should be an SVM with a linear kernel
        else:
            modelName = "SVM [Linear]"

            # Fit and run predictions on our model
            print("\nModel:", modelName, "Running with a Dataset of", len(dLabels), "entries.")
            modelResults.append(trainAndRunModel(m, dFeatures, dLabels, kf, False, modelName))


# Print our overall results, grouped by dataset size
print("\n\nOverall Results:")
i = 1
count = len(models) + len(gammaAccuracies)-1
for res in modelResults:
    print(res)
    if i == count:
        print("")
        i = 1
    else:
        i += 1

# Store an initial very low best accuracy for comparison against the actual RBF accuracies
bestGammaAccuracy = 1e-100000
bestGamma = 0

# Calculate the mean gamma value accuracies, and compare to find the best gamma parameter
for gammaKey in gammaAccuracies:
    gammaMean = gammaAccuracies[gammaKey] / len(sampleSizes)
    if gammaMean > bestGammaAccuracy:
        bestGammaAccuracy = gammaMean
        bestGamma = gammaKey

# Print our best gamma parameter and it's corresponding accuracy
print("Best Gamma Parameter:", bestGamma)
print("Highest Gamma Accuracy:", bestGammaAccuracy)
