# Lab-1 Task 1
# R00159222 Zac Dair
# Create a list of n fibonacci numbers, get user input and print fibonacci[userInput]


# Function used to create and return a list of size n of fibonacci numbers
def createFibonacci(n):
    result = []
    i = 0
    while i <= n:
        if i == 0 or i == 1:
            result.append(i)
        else:
            f1 = result[i-1]
            f2 = result[i-2]
            result.append(f1+f2)
        i = i + 1
    return result


# Value to define the length of our series
fibLength = 40

# Basic Error checking to ensure the user inputs a decimal number in our ranges
isValid = False
userNum = -1
while not isValid:
    # Take in our input, validate it's a decimal and between our ranges
    userNum = input("What number in the Fibonacci sequence would you like to see ?")
    if userNum.isdecimal() and 1 <= int(userNum) <= fibLength:
        isValid = True
    else:
        print("Please input a decimal number between 1 and ", fibLength)

fibonacci = createFibonacci(fibLength)
print("Number", userNum, "in the Fibonacci sequence is: ", fibonacci[int(userNum)-1])
