import math
import pandas as pd
import time
from sklearn.model_selection import KFold
import random
start = time.time()


# Tasks 1: Reading, Splitting and Returning train and test data
def reviewTestTrainSplit(filename):
    # File reading with error checking
    foundFile = False
    data = ''
    try:
        data = pd.read_excel(filename, usecols=['Review', 'Sentiment', 'Split'])
        foundFile = True
    except FileNotFoundError:
        print("ERROR - Sorry the file:", filename, "could not be found!")

    if foundFile:
        # Task 1: Splitting Subtasks
        # Using the Split column, store the review and sentiment of train and test entries
        trainData = data[data["Split"] == 'train']["Review"]
        trainLabels = data[data["Split"] == 'train']["Sentiment"]
        testData = data[data["Split"] == 'test']["Review"]
        testLabels = data[data["Split"] == 'test']["Sentiment"]

        # Task 1: Printing Subtasks
        # Print the data counts (positive, negative reviews in train and test data)
        print("Train Data:")
        print(len(trainLabels[trainLabels == 'positive']), "positive reviews")
        print(len(trainLabels[trainLabels == 'negative']), "negative reviews")
        print("Test Data:")
        print(len(testLabels[testLabels == 'positive']), "positive reviews")
        print(len(testLabels[testLabels == 'negative']), "negative reviews")

        # Return our four lists
        return trainData, trainLabels, testData, testLabels


# Task 2: Pre-Processing and Extracting relevant features
def cleanAndSplitReviews(reviewData, minWordLength, minWordCount):
    # Task 2: Removes all non-alphanumeric characters excluding spaces
    reviewData = reviewData.str.replace('[^a-zA-Z0-9 \n]', '')

    # Task 2: Converts all characters to lowercase
    reviewData = reviewData.str.lower()

    # Task 2: Converts review content into a list of individual words, using space as the delimiter
    reviewData = reviewData.str.split(" ")

    # Task 2: Counts the occurrence of every word, storing them in key pairs (Word, occurrence Count)
    wordCount = {}
    # IMPROVEMENT REMOVE ALL WORDS WHERE LEN < Min WORD LEN
    for entry in reviewData.to_numpy():
        for word in entry:
            if word not in wordCount:
                wordCount[word] = 1
            else:
                wordCount[word] = wordCount[word] + 1

    # Converts our wordCount dict into a dataframe for easy conditional extraction
    words = pd.DataFrame(wordCount.items(), columns=['Word', 'Count'])

    # Task 2: Requirements (length greater than min length, and count greater than min count)
    aboveMinLength = words['Word'].str.len() >= minWordLength
    aboveMinCount = words['Count'] >= minWordCount
    words = words[aboveMinLength & aboveMinCount]

    # Return a numpy array of words, and our processed review data
    return words['Word'].to_numpy(), reviewData


# Task 3: Count Feature Frequencies
def countFeatureFrequencies(reviewSet, wordSet):
    wordCount = {}
    reviewSet = reviewSet.to_numpy()

    # Task 3: Cycle through our reviews, and our words from task 2, counting occurrences
    for reviewContent in reviewSet:
        # Using set and list comparison we can extract the intersection (words in both lists)
        commonWords = set(reviewContent) & set(wordSet)
        # Cycle through these words
        for word in commonWords:
            # Set or increment counts
            if word in wordCount:
                wordCount[word] = wordCount[word] + 1
            else:
                wordCount[word] = 1

    # Task 3: Mapping un-found words as 0 (if needed)
    if len(wordCount) != len(wordSet):
        # Gets the difference between the words from task 2, and the ones already in wordCount
        uncommonWords = set(wordSet) - set(wordCount.keys())
        zeroes = [0] * len(uncommonWords)
        # Append the un-found words as keys, with a 0 as values
        wordCount.update(zip(uncommonWords, zeroes))

    # Return the wordCount dict {words: occurrence in review, ...}
    return wordCount


# Task 4: Likelihood and Prior Calculations
def calculateLikelihoodsAndPriors(positiveFF, negativeFF, smoothingVal, tpCount, tnCount):
    positiveLikelihoods = {}
    # Task 4: P[word in review | review is positive]
    for x in positiveFF:
        positiveLikelihoods[x] = (positiveFF[x] + smoothingVal) / (sum(positiveFF.values()) + (smoothingVal * (len(positiveFF))))

    # Task 4: P[word in review | review is negative]
    negativeLikelihoods = {}
    for x in negativeFF:
        negativeLikelihoods[x] = (negativeFF[x] + smoothingVal) / (sum(negativeFF.values()) + (smoothingVal * (len(negativeFF))))

    # Task 4: Likelihoods are returned, and priors are calculated and returned
    return positiveLikelihoods, negativeLikelihoods, (tpCount/(tpCount+tnCount)), (tnCount/(tpCount+tnCount))


# Task 5: Maximum likelihood classification
def classifyReview(testReview, posL, negL, posPrior, negPrior):
    # preProcess (lowercase, symbol removing)
    testReview = testReview.replace('[^a-zA-Z0-9 \n]', '').lower().split(' ')

    # Find any words from the review in training data (intersection)
    commonWords = set(testReview) & set(posL.keys())

    # Check if we failed to find any common features (random guess as we have no training data for that case)
    if len(commonWords) == 0:
        classification = random.choice(["positive", "negative"])
    else:
        # Get the sum of all positive and then negative likelihoods
        positiveLogLikelihood = sum([math.log(posL[x]) for x in commonWords])
        negativeLogLikelihood = sum([math.log(negL[x]) for x in commonWords])

        if positiveLogLikelihood - negativeLogLikelihood > math.log(negPrior) - math.log(posPrior):
            classification = "positive"  # Positive
        else:
            classification = 'negative'
    return classification


# This function encompasses tasks 2-5
def trainAndRunModel(minLen, train_Data, test_Data, test_Labels):
    sTime = time.time()
    # Task 2: pre-processing and extract relevant features
    wordList, data = cleanAndSplitReviews(train_Data, minLen, 100)
    # Task 3: (positive frequency)
    positiveFreq = countFeatureFrequencies(data[trainLabels == 'positive'], wordList)
    # Task 3: (negative frequency)
    negativeFreq = countFeatureFrequencies(data[trainLabels == 'negative'], wordList)
    # Task 4: (extra len lines to get counts)
    posReviewCount = len(data[trainLabels == 'positive'])
    negReviewCount = len(data[trainLabels == 'negative'])
    # Task 4: Gets the likelihoods and priors
    posLikelihood, negLikelihood, posP, negP = calculateLikelihoodsAndPriors(positiveFreq, negativeFreq, 1,
                                                                             posReviewCount, negReviewCount)

    # Task 5: Classifications using the k-fold test data
    classifications = []
    for review in test_Data:
        classifications.append(classifyReview(review, posLikelihood, negLikelihood, posP, negP))

    # Task 6: Accuracy (fraction of correctly classifier samples)
    correctCount = 0
    wrongCount = 0
    confMatrix = {"tp": 0, "tn": 0, "fp": 0, "fn": 0}
    # Cycle through the classifications and the actual labels
    for a, b in zip(classifications, test_Labels):
        if a == b:
            correctCount = correctCount + 1
            if a == "positive":
                confMatrix["tp"] = confMatrix["tp"] + 1
            else:
                confMatrix["tn"] = confMatrix["tn"] + 1
        else:
            wrongCount = wrongCount + 1
            if a == "positive":
                confMatrix["fp"] = confMatrix["fp"] + 1
            else:
                confMatrix["fn"] = confMatrix["fn"] + 1
    # Task 6: Accuracy
    accuracy = correctCount / (correctCount + wrongCount)

    # Return our model results
    return sTime, correctCount, wrongCount, accuracy, confMatrix


# Task 1: Using our splitting function, read in the contents of the dataset file
trainData, trainLabels, testData, testLabels = reviewTestTrainSplit("movie_reviews.xlsx")

# Define our K-folds, initialize our model results list and accuracy total for mean calculation
kf = KFold(n_splits=10, shuffle=True)
modelResults = []
accuracyTotal = 0

# Task 6: Initially start with min word length as 1 (this increments per k-fold)
minWordLen = 1

# Task 6: Running our classifier with K-folds
for train_index, test_index in kf.split(trainData):
    # Task 6: Trains and tests our model
    trainingData = trainData.iloc[train_index]
    testingData = trainData.iloc[test_index]
    actualLabels = trainLabels.iloc[test_index]
    startTime, correct, wrong, acc, cMatrix = trainAndRunModel(minWordLen, trainingData, testingData, actualLabels)
    accString = str(round(acc * 100, 2)) + "%"
    # Task 6: Add accuracy to total for mean calculation
    accuracyTotal = accuracyTotal + acc
    print("\nModel Results:")
    print("Using", minWordLen, "as min word length")
    print(correct, "correct,", wrong, "wrong classifications, out of", correct+wrong)
    timeTook = time.time()-startTime
    print("Completion took", timeTook, "seconds")
    print("Model Accuracy", accString)

    # Append the model results for cross evaluation
    modelResults.append({"Word Len": minWordLen, "Accuracy": acc, "Time Taken": timeTook})

    # Task 6: Increment the minimum word length (to evaluate each model on different lengths 1-10)
    minWordLen = minWordLen + 1

# Task 6: Pick the best model
bestAccuracy = 0
bestMinLen = 0
for model in modelResults:
    if model['Accuracy'] > bestAccuracy:
        bestAccuracy = model['Accuracy']
        bestMinLen = model['Word Len']
print("\nCross-evaluation result:\nThe best accuracy was", str(round(bestAccuracy * 100, 2)) + "%")
print("Using a minimum word length of", bestMinLen)

# Task 6: Calculate the mean accuracy
print("Mean Accuracy: ", str(round((accuracyTotal/len(modelResults)*100), 2))+"%")

# Task 6: Retrain the model on the full dataset and test dataset
print("\nFinal Model: Minimum Word Length is", bestMinLen)
startTime, correct, wrong, acc, cMatrix = trainAndRunModel(bestMinLen, trainData, testData, testLabels)

# Task 6: Confusion Matrix
print("Confusion Matrix:", cMatrix)
total = correct + wrong

# Task 6: Confusion Matrix percentages
print("True Positive:", str(round((cMatrix['tp']/total)*100, 2))+"%")
print("True Negative:", str(round((cMatrix['tn']/total)*100, 2))+"%")
print("False Positive:", str(round((cMatrix['fp']/total)*100, 2))+"%")
print("False Negative:", str(round((cMatrix['fn']/total)*100, 2))+"%")
print(correct, "correct,", wrong, "wrong, out of", correct+wrong, "classifications")
timeTook = time.time()-startTime
print("Completion took", timeTook, "seconds")

# Task 6: Classification Accuracy
print("Model Accuracy", str(round(acc * 100, 2)) + "%")
print("Total time took: ", time.time()-start)
