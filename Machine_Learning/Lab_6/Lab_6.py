import random
from sklearn import datasets, model_selection, linear_model, svm, metrics
import matplotlib.pyplot as plt

# Load digits dataset
data = datasets.load_digits()

# Print Dataset Description
print(data.DESCR)

# Gets possible classifications (0-9)
targets = set(data.target)
print(targets)

# For each digit create a plot using a random index
for digit in targets:
    plt.figure()

    # Get a random index for that specific digit
    randomIndex = random.randint(0, sum(data.target==digit)-1)

    # Plot our data
    # print(data.data[data.target == digit][randomIndex].reshape(8, 8))
    # print("\n\n")  Old prints that show the digit data format
    plt.imshow(data.data[data.target == digit][randomIndex].reshape(8, 8))
    plt.show()

# Split the data in train and test using ranges (0-80% of the dataset for train, 80%-100% for test)
trainData = data.data[0:int(0.8*len(data.data))]
trainTarget = data.target[0:int(0.8*len(data.target))]
testData = data.data[int(0.8*len(data.data)):len(data.data)]
testTarget = data.target[int(0.8*len(data.target)):len(data.target)]

# K-fold init
kf = model_selection.KFold(n_splits=2, shuffle=True)

# Best Score
bestScore = 1e100
bestModel = "undefined"
# Train Test Splits for Cross Validation
for trainIndex, testIndex in kf.split(trainData):

    # Initialise a bunch of models
    perceptron = linear_model.Perceptron()
    svm1 = svm.SVC(kernel='linear')
    svm2 = svm.SVC(kernel='poly')
    svm3 = svm.SVC(kernel='rbf')
    svm4 = svm.SVC(kernel='sigmoid')

    models = [perceptron, svm1, svm2, svm3, svm4]
    scores = []

    # For each model, fit them to the data, run predictions and append the score
    for model in models:
        model.fit(trainData[trainIndex], trainTarget[trainIndex])
        prediction = model.predict(trainData[testIndex])
        score = metrics.accuracy_score(trainTarget[testIndex], prediction)
        scores.append(score)
        if type(model).__name__ == "Perceptron":
            print("Model: Perceptron")

        else:
            print("Model: SVC", model.kernel)

        print("Score: ", score)

    # Retrieve the highest score of this k-fold iteration, and it's index
    maxScore = max(scores)
    if maxScore < bestScore:
        bestModel = models[scores.index(maxScore)]

if bestModel != "undefined":
    # Run our best model on our test data
    prediction = bestModel.predict(testData)

    # For each digit, print the amount of miss-classifications
    for digit in targets:
        print("Class: ", digit)
        print("Miss-Classifications: ", sum(testTarget[prediction!=testTarget]==digit))

    # Display some of the miss-classifications
    plt.figure()
    for i in range(3):
        for j in range(3):
            plt.subplot(3, 3, i * 3 + j + 1)
            index = random.randint(0, sum(prediction != testTarget) - 1)
            plt.imshow(testData[prediction != testTarget][index].reshape(8, 8))

    plt.show()