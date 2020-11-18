from sklearn import datasets
from sklearn import metrics
from sklearn import linear_model
from sklearn import svm
from sklearn import model_selection
import matplotlib.pyplot as plt
import random

def main():
    plt.close("all")
    digits = datasets.load_digits()
    print(digits.DESCR)
    print()

    targets = set(digits.target)

    print(targets)
    for digit in targets:
        plt.figure()
        for i in range(3):
            for j in range(3):
                plt.subplot(3,3,i*3+j+1)
                index = random.randint(0,sum(digits.target==digit)-1)
                plt.imshow(digits.data[digits.target==digit][index].reshape(8, 8))

    train_data = digits.data[0:int(0.8*len(digits.data))]
    train_target = digits.target[0:int(0.8*len(digits.target))]
    test_data = digits.data[int(0.8*len(digits.data)):len(digits.data)]
    test_target = digits.target[int(0.8*len(digits.target)):len(digits.target)]

    kf = model_selection.KFold(n_splits=2, shuffle=True)

    best_score = 1e100

    for train_index,test_index in kf.split(train_data):
        clf1 = linear_model.Perceptron()
        clf2 = svm.SVC(kernel="rbf", gamma=1e-3)    
        clf3 = svm.SVC(kernel="sigmoid", gamma=1e-4)    
    
        clf1.fit(train_data[train_index], train_target[train_index ])
        prediction1 = clf1.predict(train_data[test_index])
    
        clf2.fit(train_data[train_index], train_target[train_index])
        prediction2 = clf2.predict(train_data[test_index])
    
        clf3.fit(train_data[train_index], train_target[train_index])
        prediction3 = clf3.predict(train_data[test_index])
        
        score1 = metrics.accuracy_score(train_target[test_index], prediction1)
        score2 = metrics.accuracy_score(train_target[test_index], prediction2)
        score3 = metrics.accuracy_score(train_target[test_index], prediction3)
        
        print("Perceptron accuracy score: ", score1)
        print("SVM with RBF kernel accuracy score: ", score2)
        print("SVM with Sigmoid kernel accuracy score: ", score3)
        print()

        if score1<best_score:
            best_clf = clf1
        if score2<best_score:
            best_clf = clf2
        if score3<best_score:
            best_clf = clf3

    prediction = best_clf.predict(test_data)
    for digit in targets:
        print(digit, " -> ", sum(test_target[prediction!=test_target]==digit))

    plt.figure()
    for i in range(3):
        for j in range(3):
            plt.subplot(3,3,i*3+j+1)
            index = random.randint(0, sum(prediction != test_target) - 1)
            plt.imshow(test_data[prediction!=test_target][index].reshape(8, 8))

    plt.show()

main()
