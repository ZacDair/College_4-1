import numpy as np
import matplotlib.pyplot as plt
import numpy as np
from sklearn.cluster import KMeans

def task1():
    def calculate_gradient(f, x):
        f0 = f(x)
        gradient = np.zeros(len(x))
        epsilon = 1e-6
        for i in range(len(x)):
            x[i] += epsilon
            fi = f(x)
            x[i] -= epsilon
            di = (fi - f0)/epsilon
            gradient[i] = di
        return gradient
    
    
    def cost_function(p):
        return (p[0]-1)**2 + (p[1]-3)**2 + (p[2]+2)**2

    max_iter = 10000
    epsilon = 1e-6
    for eta in [1,.99,.9,.8,.7,.6,.5,.4,.3,.2,.1,.01,.001,.0001]:
        x = np.zeros(3)
        for i in range(max_iter):
            gradient = calculate_gradient(cost_function, x)
            x -= eta * gradient
            if np.max(np.abs(x-[1,3,-2]))<epsilon:
                break
        print(eta,i)
    

def task2():
    def k_means_clustering(data, no_of_clusters):
        assignments = np.random.randint(0,no_of_clusters, data.shape[0])    
        cluster_means = data[np.random.randint(0,data.shape[0],no_of_clusters),:]
        distances = np.zeros((no_of_clusters,data.shape[0]))
        while True:
            for i in range(no_of_clusters):
                distances[i,:] = np.sum((data - cluster_means[i,:])**2,axis=1)
            new_assignments = np.argmin(distances,axis=0)
            if np.sum(new_assignments!=assignments)==0:
                break
            assignments = new_assignments
    
            for i in range(no_of_clusters):
                cluster_means[i,:] = np.mean(data[assignments==i,:], axis=0)
    #    print(cluster_means)
        return assignments

    plt.close('all')
    
    no_of_clusters = 3
    cluster_mean = np.random.rand(no_of_clusters,2)
        
#    print(cluster_mean)
    
    data = np.array([[]])
    target = np.array([[]], dtype='int')
    
    points_per_cluster = 100
    sigma = 0.1
    for i in range(no_of_clusters):
        noise = sigma * np.random.randn(points_per_cluster,2)
        cluster = cluster_mean[i,:] + noise
        data = np.append(data,cluster).reshape((i+1)*points_per_cluster,2)
        target = np.append(target,[i]*points_per_cluster)
        
    no_of_outliers = 0
    outliers = np.random.rand(no_of_outliers,2)
    outlier_labels = np.random.randint(0,no_of_clusters,no_of_outliers)
        
    data = np.append(data,outliers).reshape(no_of_clusters*points_per_cluster+no_of_outliers,2)
    target = np.append(target,outlier_labels)

    plt.figure()
    plt.scatter(data[:,0], data[:,1], c=target)
    
    kmeans = KMeans(n_clusters=no_of_clusters)
    kmeans.fit(data)
    prediction = kmeans.predict(data)

    plt.figure()
    plt.scatter(data[:,0], data[:,1], c=prediction)

    prediction2 = k_means_clustering(data, no_of_clusters)
    plt.figure()
    plt.scatter(data[:,0], data[:,1], c=prediction2)
 
def main():
    task1()
    task2()

main()
 