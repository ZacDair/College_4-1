import math

import pandas as pd
import numpy as np


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
        # Using the Split column, store the review and sentiment of train and test entries
        trainData = data[data["Split"] == 'train']["Review"]
        trainLabels = data[data["Split"] == 'train']["Sentiment"]
        testData = data[data["Split"] == 'test']["Review"]
        testLabels = data[data["Split"] == 'test']["Sentiment"]

        # Print the data counts (positive, negative reviews in train and test data)
        print("Train Data:")
        print(len(trainLabels[trainLabels == 'positive']), "positive reviews")
        print(len(trainLabels[trainLabels == 'negative']), "negative reviews")
        print("Test Data:")
        print(len(testLabels[testLabels == 'positive']), "positive reviews")
        print(len(testLabels[testLabels == 'negative']), "negative reviews")

        # Return our four lists
        return trainData, trainLabels, testData, testLabels


# Task 2
def cleanAndSplitReviews(reviewData, minWordLength, minWordCount):
    # Removes all non-alphanumeric characters excluding spaces
    reviewData = reviewData.str.replace('[^a-zA-Z0-9 \n]', '')

    # Converts all characters to lowercase
    reviewData = reviewData.str.lower()

    # Converts all sentences into a list of words, using space as the delimiter
    reviewData = reviewData.str.split(" ")

    # Counts the occurence of every word, storing them in key pairs (Word, Occurence Count)
    wordCount = {}
    for entry in reviewData.to_numpy():
        for word in entry:
            if word not in wordCount:
                wordCount[word] = 1
            else:
                wordCount[word] = wordCount[word] + 1

    # Converts our wordCount dict into a dataframe for easy conditional extraction
    words = pd.DataFrame(wordCount.items(), columns=['Word', 'Count'])

    # Removes all words less than the min length, leaving only the words above the min length
    words = words[words['Word'].str.len() > minWordLength]

    # Removes all words that occur less than the min occurrence
    words = words[words['Count'] > minWordCount]

    return words['Word'].to_numpy(), reviewData  # Would have just returned the pandas frame... might still


# Task 3
def countFeatureFrequencies(reviewSet, wordSet):
    # if we can populate the dict initially, we can remove the else check and the bottom if for adding 0s
    wordCount = {}
    reviewSet = reviewSet.to_numpy()
    for review in reviewSet:
        commonWords = set(review) & set(wordSet)
        for word in commonWords:
            if word in wordCount:
                wordCount[word] = wordCount[word] + 1
            else:
                wordCount[word] = 1

    if len(wordCount) != len(wordSet):
        for word in wordSet:
            if word not in wordCount:
                wordCount[word] = 0

    return wordCount


# Task 4
def calculateLikelihoodsAndPriors(positiveFF, negativeFF, smoothingVal, tpCount, tnCount, tCount):
    positiveLikelihoods = {}
    for x in positiveFF:
        positiveLikelihoods[x] = (positiveFF[x] + smoothingVal) / (tpCount + (smoothingVal * (len(positiveFF))))
    negativeLikelihoods = {}
    for x in negativeFF:
        negativeLikelihoods[x] = (negativeFF[x] + smoothingVal) / (tnCount + (smoothingVal * (len(negativeFF))))
    return positiveLikelihoods, negativeLikelihoods, (tpCount/tCount), (tnCount/tCount)


# Task 5
def classifyReview(testReview, posL, negL, posPrior, negPrior):
    # preProcess (lowercase, symbol removing)
    classification = -1
    testReview = testReview.replace('[^a-zA-Z0-9 \n]', '').lower().split(' ')
    commonWords = set(testReview) & set(posL.keys())
    if len(commonWords) == 0:
        # print("No words with likelihoods found in the review...")
        pass
    else:
        val1 = 0
        val2 = 0
        for word in commonWords:
            val1 = val1 + posL[word]
            val2 = val2 + negL[word]

        posterior = math.exp(math.log(val1) - math.log(val2))

        if posterior > negPrior / posPrior:
            classification = 1  # Positive
        else:
            classification = 0
    return classification


trainData, trainLabels, testData, testLabels = reviewTestTrainSplit("movie_reviews.xlsx")
wordList, trainData = cleanAndSplitReviews(trainData, 6, 100)

# Task 3 (positive frequency)
positiveFreq = countFeatureFrequencies(trainData[trainLabels == 'positive'], wordList)
# Task 3 (negative frequency)
negativeFreq = countFeatureFrequencies(trainData[trainLabels == 'negative'], wordList)

# Task 4 (extra len lines to get counts)
posReviewCount = len(trainLabels[trainLabels == 'positive'])
negReviewCount = len(trainLabels[trainLabels == 'negative'])
totalReviewCount = len(trainLabels)

# Gets the likelihoods and priors
posLikelihood, negLikelihood, posP, negP = calculateLikelihoodsAndPriors(positiveFreq, negativeFreq, 1, posReviewCount, negReviewCount, totalReviewCount)
classifications = []
for review in testData:
    classifications.append(classifyReview(review, posLikelihood, negLikelihood, posP, negP))
correct = 0
wrong = 0
for a, b in zip(classifications, testLabels):
    if a == 0:
        a = 'negative'
    elif a == 1:
        a = 'positive'
    else:
        a = 'undefined'
    if a == b:
        correct = correct + 1
    else:
        wrong = wrong + 1
print("Out of:", correct+wrong)
print(correct, "correct and", wrong, "wrong classifications")
