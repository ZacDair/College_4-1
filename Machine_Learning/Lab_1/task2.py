# Lab-1 Task 2
# R00159222 Zac Dair
# Read the contents of the file record all words that have a least length n
# Print the results that occur more than x times
import os
import string


# Opens our file, loops through all lines, in each line all words and stores the occurrence count in a dict based on len
def findWords(length, filename):
    words = {}
    # Opens our file in read mode
    with open(filename, 'r') as file:

        # Reads in our lines and loop through them
        for line in file.readlines():

            # Uses the built in string library to remove any punctuation
            line = line.translate(str.maketrans('', '', string.punctuation))

            # Create an array of words using space as a delimiter
            temp = line.split(" ")

            # Cycle through our words, stripping any extra spaces, checking the length and presence in our words dict
            for word in temp:
                word = word.strip()
                if len(word) >= length:
                    if word in words.keys():
                        words[word] = words[word] + 1
                    else:
                        words[word] = 1
    return words


# Our variables, a file name, minimum word length and minimum occurrence
draculaFile = "Dracula.txt"
minWordLength = 3
minWordOccurrence = 300

# Error checking to ensure the file exists
if os.path.isfile(draculaFile):
    foundWords = findWords(minWordLength, draculaFile)
    print("Here is the result of our program:\n"
          "mindWordLength: %d, minWordOccurrence: %d, file: %s", minWordLength, minWordOccurrence, draculaFile)
    for x in foundWords:
        if foundWords[x] > 300:

            print('"'+x+'"->', foundWords[x])
