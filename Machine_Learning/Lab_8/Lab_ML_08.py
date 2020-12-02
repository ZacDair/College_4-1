from sklearn import datasets
from sklearn import tree
from sklearn import model_selection
from sklearn import metrics
import numpy as np
import matplotlib.pyplot as plt

def part1():
    iris = datasets.load_iris()

    kf = model_selection.KFold(n_splits=5, shuffle=True)
    avg_acc_train = []
    avg_acc_test = []
    for d in range(1,11):
        clf = tree.DecisionTreeClassifier(max_depth=d)
        acc_train = []
        acc_test = []
        for i in range(100):
            for train_index,test_index in kf.split(iris.data):
                clf.fit(iris.data[train_index], iris.target[train_index])
                prediction_train = clf.predict(iris.data[train_index])
                prediction_test = clf.predict(iris.data[test_index])
                acc_train.append(metrics.accuracy_score(iris.target[train_index], prediction_train))
                acc_test.append(metrics.accuracy_score(iris.target[test_index], prediction_test))

        plt.figure()
        tree.plot_tree(clf)

        avg_acc_train.append(np.mean(acc_train))
        avg_acc_test.append(np.mean(acc_test))

    plt.figure()
    plt.plot(np.arange(1,len(avg_acc_test)+1), avg_acc_test)
    plt.plot(np.arange(1,len(avg_acc_train)+1), avg_acc_train)


    plt.show()

    return

def part2():
    A = np.array([[1., 2.], [3., 4.], [5., 6.]])
    AtA = np.matmul(A.T, A)
    AtA_inv = np.linalg.inv(AtA)
    print("A-1, A=\n", A)
    print("A-2, AtA=\n", AtA)
    print("A-3, AtA^-1=\n", AtA_inv)
    AAt = np.matmul(A, A.T)
    print("A-4, AAt=\n", AAt)
    try:
        print("A-4, AAt^-1=\n", np.linalg.inv(AAt))
    except:
        print("singular")
    print()

    x = np.array([1., 2.])
    b = np.matmul(AtA, x)
    print("B-1 x=", x)
    print("B-2 b=", b)
    print("B-3 AtA_inv*b=", np.matmul(AtA_inv, b))
    print()
    return

def main():
    part1()
    part2()
    return

main()
