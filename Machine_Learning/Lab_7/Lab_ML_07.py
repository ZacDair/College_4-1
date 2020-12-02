import pandas as pd
import numpy as np
from sklearn import neighbors
from sklearn import model_selection
from sklearn import datasets
from sklearn import metrics
import matplotlib.pyplot as plt

def part1():
    iris = datasets.load_iris()    
    maxK = 50
    kf = model_selection.KFold(n_splits=len(iris.target), shuffle=True)
    correct = [0]*(maxK-1)
    max_correct = -1
    best_k = -1
    for k in range(1,maxK):
        print('\r',100*(k+1)/maxK,"%",end="")
        for train_index,test_index in kf.split(iris.data):    
            clf = neighbors.KNeighborsClassifier(n_neighbors=k)
            clf.fit(iris.data[train_index],iris.target[train_index])
            prediction = clf.predict(iris.data[test_index])
            if iris.target[test_index] == prediction:
                correct[k-1] = correct[k-1] + 1
        if correct[k-1]>max_correct:
            max_correct = correct[k-1]
            best_k = k
        
        
    plt.close("all")
    plt.figure()
    plt.plot(range(1,maxK),np.array(correct)/len(iris.target))
    
    print("Best k:", best_k)
    return

def part2():
    A = np.array([[1.,2.],[3.,4.],[5.,6.]])
    print("A-1, A=\n", A)
    print("A-1, A.T=\n", A.T)
    print()

    a = np.array([1.,2.,3.])
    b = np.array([4.,5.,6.])
    print("B-1, a=", a)
    print("B-2, b=", b)
    print("B-3, a.T*b=", np.dot(a,b))
    print("B-4, a*b.T=\n", np.outer(a,b))
    print()
    
    A = np.array([[1.,2.],[3.,4.],[5.,6.]])
    b = np.array([7.,8.])
    print("C-1, A=\n", A)
    print("C-2, b=\n", b)
    print("C-3, Ab=\n", np.matmul(A,b))
    print()
    
    A = np.array([[1.,2.],[3.,4.],[5.,6.]])
    B = np.array([[1.,2.,3.,4.],[5.,6.,7.,8.]])
    print("D-1, A=\n", A)
    print("D-2, B=\n", B)
    print("D-3, AB=\n", np.matmul(A,B))
    print()

    print("E, B.T*A.T=\n", np.matmul(B.T,A.T))
    print()
    return

def main():
    part1()
    part2()
    return
    
main()
