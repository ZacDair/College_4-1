import pandas as pd


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

        # Extract cut qualities
        cutQualities = data.cut.unique()

        # Extract color grades
        colorGrades = data.color.unique()

        # Extract clarity grades
        clarityGrades = data.clarity.unique()

        return data, cutQualities, colorGrades, clarityGrades


# Use our loadData function, to retrieve the full dataset, unique values for cut, color and clarity
dataset, cuts, colors, clarities = loadData()

# Initialise a dataframe to use for features (might need changing)
dataFeatures = pd.DataFrame(columns=["carat", "cut", "color", "clarity", "depth", "table"])

# Loop through our possible combinations of cut, color, clarity and count the occurences for each
combinationCounts = {}
for cut in cuts:
    for color in colors:
        for clarity in clarities:
            count = len(dataset[(dataset["cut"] == cut) & (dataset["color"] == color) & (dataset["clarity"] == clarity)])
            combinationCounts[cut+"_"+color+"_"+clarity] = count


for x in combinationCounts:
    if combinationCounts[x] >= 800:
        print(x, "has", combinationCounts[x], "entries.")



