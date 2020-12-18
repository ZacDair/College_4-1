import pandas as pd
import numpy as np
from sklearn import model_selection
import matplotlib.pyplot as plt


# Task 1 Load Data
def loadData():
    # File reading with error checking
    foundFile = False
    filename = 'diamonds.csv'
    data = pd.DataFrame
    try:
        data = pd.read_csv(filename)

        foundFile = True
    except FileNotFoundError:
        print("ERROR - Sorry the file:", filename, "could not be found!")
        exit(0)

    if foundFile:

        # Task 1 Extract cut qualities
        cutQualities = data.cut.unique()

        # Task 1 Extract color grades
        colorGrades = data.color.unique()

        # Task 1 Extract clarity grades
        clarityGrades = data.clarity.unique()

        return data, cutQualities, colorGrades, clarityGrades


# Task 2 Tri-variate polynomial model function
def calculate_model_function(degree, featureVectors, coefficients):
    res = np.zeros(featureVectors.shape[0])
    k = 0
    for n in range(degree+1):
        for i in range(n+1):
            for j in range(n+1):
                res += coefficients[k] * (featureVectors[:, 0] ** i) * (featureVectors[:, 1] ** (n - i)) * (featureVectors[:, 2] ** (n - j))
            k += 1
    return res


# Task 2 Calculates the correct size for the coefficient vector
def numberOfCoefficients3(degree):
    t = 0
    for n in range(degree+1):
        for i in range(n+1):
            for j in range(n+1):
                for k in range(n+1):
                    if i+j+k == n:
                        t = t+1
    return t


# Task 3 Linearization
def linearize(degree, data, p0):
    # Task 3 calculate the value of the model
    f0 = calculate_model_function(degree, data, p0)
    J = np.zeros((len(f0), len(p0)))
    epsilon = 1e-6

    # Task 3 calculate the Jacobian matrix
    for i in range(len(p0)):
        p0[i] += epsilon
        fi = calculate_model_function(degree, data, p0)
        p0[i] -= epsilon
        di = (fi - f0)/epsilon
        J[:, i] = di
    return f0, J


# Task 4 Parameter Update
def calculate_update(y, f0, J):
    l = 1e-2
    # Task 4 Normal Matrix
    N = np.matmul(J.T, J) + l*np.eye(J.shape[1])

    # Task 4 Regularisation and residual calculation
    r = y-f0

    # Task 4 Construct and solve the equation
    n = np.matmul(J.T, r)
    dp = np.linalg.solve(N, n)
    return dp


# Task 5 Calculate the best fitting coefficient vector
def calculateBestCoefVector(degree, maxIter, featureVector, targetVector):
    # Task 5 Create the coefficient vector of zeroes based on the degree value
    p0 = np.zeros(numberOfCoefficients3(degree))

    # Iterate through the procedure (alternating linearize and parameter update)
    for i in range(maxIter):

        # Calculate Jacobian and estimated target vector
        f0, J = linearize(deg, featureVector, p0)

        # Calculate the optimal parameter update
        dp = calculate_update(targetVector, f0, J)
        p0 += dp
    return p0


# Use our loadData function, to retrieve the full dataset, unique values for cut, color and clarity
dataset, cuts, colors, clarities = loadData()

# Subset Feature List
subsetFeatures = []

# Subset Target List
subsetTargets = []

# Dataset Names
datasetNames = []

# Task 1 Loop through our possible combinations of cut, color, clarity and count the occurrences for each
combinationCounts = {}
for cut in cuts:
    for color in colors:
        for clarity in clarities:
            subset = dataset[(dataset["cut"] == cut) & (dataset["color"] == color) & (dataset["clarity"] == clarity)]
            count = len(subset)

            # Task 1 only extract if it's more than 800 data-points
            if count >= 800:
                subsetFeatures.append(subset[["carat", "depth", "table"]])
                subsetTargets.append(subset[["price"]])
                datasetNames.append(cut+" "+color+" "+clarity)

# Task 6 K-Fold Cross initialization
kf = model_selection.KFold(n_splits=3, shuffle=True)

# Task 6 Loop through each dataset calculating the best degree
index = 0
datasetScores = []
while index < len(subsetFeatures):

    # Convert our sub-datasets into numpy arrays
    featuresAsNumpy = np.array(subsetFeatures[index][["carat", "depth", "table"]], dtype='float')
    targetsAsNumpy = np.array(subsetTargets[index]["price"], dtype='float')

    # Store the score per degree
    scorePerDegree = {}

    # Define a max number of iterations for the inner procedure
    maxIteration = 10

    # Set up the K-fold cross-validation train test splits
    for trainIndex, testIndex, in kf.split(featuresAsNumpy):

        # Cycle through possible degree values 0-3
        for deg in range(4):

            p0 = calculateBestCoefVector(deg, maxIteration, featuresAsNumpy[trainIndex], targetsAsNumpy[trainIndex])

            # Run our model with the optimal p0 to get our most accurate estimates
            testTarget = calculate_model_function(deg, featuresAsNumpy[testIndex], p0)

            # List to store the price differences between estimate and actual
            priceDifference = []

            # Cycle through each test data entry, getting the absolute difference between prices
            tIndex = 0
            for i in testIndex:
                if testTarget[tIndex] > targetsAsNumpy[i]:
                    priceDifference.append(testTarget[tIndex] - targetsAsNumpy[i])
                else:
                    priceDifference.append(targetsAsNumpy[i] - testTarget[tIndex])
                tIndex += 1

            # Store the degree and the list of price differences (key value pair)
            if deg in scorePerDegree.keys():
                scoreList = scorePerDegree[deg]
                scoreList += priceDifference
                scorePerDegree[deg] = scoreList
            else:
                scorePerDegree[deg] = priceDifference

    # Get the avg price difference per degree
    for key in scorePerDegree:
        scorePerDegree[key] = np.mean(scorePerDegree[key])

    # Create a key value pair which is the dataset index and the list of scores by degree
    datasetScores.append({index: scorePerDegree})
    index += 1

# Task 6 Loop through dataset scores to determine the best degree value
for datasetScore in datasetScores:

    # for each dataset, get the lowest score
    for dataset in datasetScore:
        lowestAvgDiff = min(datasetScore[dataset].values())
        scoreDict = datasetScore[dataset]

        # for each degree find which one the lowest score belongs to
        for degree in scoreDict:
            if scoreDict[degree] == lowestAvgDiff:
                datasetScores[dataset] = degree

# Task 7 Finally process each dataset with the optimal degree
index = 0
while index < len(subsetFeatures):
    # Convert our sub-datasets into numpy arrays
    featuresAsNumpy = np.array(subsetFeatures[index][["carat", "depth", "table"]], dtype='float')
    targetsAsNumpy = np.array(subsetTargets[index]["price"], dtype='float')
    maxIteration = 10
    deg = datasetScores[index]

    # Create the coefficient vector based on the degree value
    p0 = calculateBestCoefVector(deg, maxIteration, featuresAsNumpy, targetsAsNumpy)

    # Run our model with the optimal p0 to get our most accurate estimates
    estimates = calculate_model_function(deg, featuresAsNumpy, p0)

    # Plot estimate vs true sales prices
    plt.close()
    plt.figure()
    plt.scatter(estimates, targetsAsNumpy, label="Estimate")
    plt.plot(targetsAsNumpy, targetsAsNumpy, c='c', label="True")
    plt.xlabel("Estimated Price")
    plt.ylabel("True Price")
    plt.legend()
    plt.title("Estimate / True Price for Dataset : " + datasetNames[index])
    plt.show()

    index = index + 1
