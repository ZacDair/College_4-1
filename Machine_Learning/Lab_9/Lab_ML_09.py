import numpy as np
from sklearn import datasets
from sklearn import neural_network
from sklearn import model_selection
from sklearn import metrics
import matplotlib.pyplot as plt

def mlp():
    plt.close("all")
    digits = datasets.load_digits()
    
    X_train, X_test, y_train, y_test = model_selection.train_test_split(digits.data, digits.target, test_size=0.1)
    
    nodes = []
    score_train = []
    score_test = []
    
    for i in range(2,10):
        mlp = neural_network.MLPClassifier(hidden_layer_sizes=(i*i,))
        mlp.fit(X_train,y_train)
        
        ig, axes = plt.subplots(i, i)
        vmin = np.abs(mlp.coefs_[0]).min()
        vmax = np.abs(mlp.coefs_[0]).max()
        for coef, ax in zip(mlp.coefs_[0].T, axes.ravel()):
            ax.imshow(np.abs(coef.reshape(8, 8)), vmin=vmin, vmax=vmax)
            ax.set_xticks(())
            ax.set_yticks(())
        
        p_train = mlp.predict(X_train)
        p_test = mlp.predict(X_test)
        
        nodes.append(i*i)
        score_train.append(metrics.accuracy_score(y_train, p_train))
        score_test.append(metrics.accuracy_score(y_test, p_test))
        
    plt.figure()
    plt.plot(nodes, score_train)
    plt.plot(nodes, score_test)

    return

def polynomials():
    def num_coefficients_2(d):
        t = 0
        for n in range(d+1):
            for i in range(n+1):
                for j in range(n+1):
                    if i+j==n:
                        t = t+1
        return t
    
    def num_coefficients_3(d):
        t = 0
        for n in range(d+1):
            for i in range(n+1):
                for j in range(n+1):
                    for k in range(n+1):
                        if i+j+k==n:
                            t = t+1
        return t
    
    def eval_poly_2(d,a,x,y):
        r = 0
        t = 0
        for n in range(d+1):
            for i in range(n+1):
                for j in range(n+1):
                    if i+j==n:
                        r += a[t]*(x**i)*(y**j)
                        t = t+1
        return r
    
    def eval_poly_3(d,a,x,y,z):
        r = 0
        t = 0
        for n in range(d+1):
            for i in range(n+1):
                for j in range(n+1):
                    for k in range(n+1):
                        if i+j+k==n:
                            r += a[t]*(x**i)*(y**j)*(z**k)
                            t = t+1
        return r
    
    def eval_poly_1(a,x):
        r = 0
        for i in range(len(a)):
            r += a[i]*(x**i)
        return r
    
    def numerical_derivative_poly(a,x):
        f0 = eval_poly_1(a,x)    
        epsilon = 1e-6
        f1 = eval_poly_1(a,x+epsilon)
        d = (f1-f0)/epsilon
        return d
    
    def analytical_derivative_poly(a,x):
        r = 0
        for i in range(1,len(a)):
            r += i*a[i]*(x**(i-1))
        return r

    print("2A", num_coefficients_2(2))
    print("2B", num_coefficients_3(2))
    
    print("2C", eval_poly_1([1.,2.,3.],4.))
    print("2D", eval_poly_2(2,[1.,2.,3.,4.,5.,6.],2.,3.))
    print("2E", eval_poly_3(2,[1.,2.,3.,4.,5.,6.,7.,8.,9.,10.],2.,3.,4.))
    print()
    
    print("3A", numerical_derivative_poly([1.,2.,3.],4.))
    print("3B", analytical_derivative_poly([1.,2.,3.],4.))
    print()


    A = np.array([[1.,2.],[3.,4.]])
    AtA = np.matmul(A.T,A)
    a = np.array([1.,2.])
    x = np.array([3.,4.])
    print("4A a=", a)
    print("4A x=", x)
    d = np.zeros(2)    
    f0 = np.dot(a,x)    
    epsilon = 1e-6
    for i in range(len(x)):
        x[i] += epsilon
        fi = np.dot(a,x)
        x[i] -= epsilon
        d[i] = (fi - f0)/epsilon
    print("4A d=", d)
    

    print("4B A=\n", A)
    print("4B AtA=\n", AtA)
    d = np.zeros(2)    
    f0 = np.matmul(x.T,np.matmul(AtA,x))    
    epsilon = 1e-6
    for i in range(len(x)):
        x[i] += epsilon
        fi = np.matmul(x.T,np.matmul(AtA,x))
        x[i] -= epsilon
        d[i] = (fi - f0)/epsilon
    print("4B d=", d)
    print("4B 2*AtA*x=",2*np.matmul(AtA,x))
    print()
    return

def main():
    mlp()
    polynomials()
    
main()
