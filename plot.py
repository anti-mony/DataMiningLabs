#importing dependencies
import matplotlib.pyplot as plt
import re

#File Read - Input File Name = climate_out.txt
with open('processed_data.txt') as f:
    file_read = f.readlines()

#Variables Initialization
x=[]
y=[]
color=[]
current_cluster = -1

#Stripping Off White Spaces from File Read
file_read = [p.strip() for p in file_read]

#Function To Update List For X,Y co-ordinates
def upadte_elements(x_coordinate,y_coordinate,cluster):
    x.append(float(x_coordinate))
    y.append(float(y_coordinate))
    color.append(int(cluster))

#Looping through the file extracting requiured data
for line in file_read:
    #Extracting Cluster Number
    if 'Cluster' in line and 'Size of Cluster' not in line:
        current_cluster = int(line[9])
        continue
    ##Extracting Co-Ordinate Of Centroid
    if 'Centroid' in line:
        a,b=re.findall("\d+\.\d+", line)
        upadte_elements(a,b,int(0))
        continue
    #Finding Co-ordinates of points within Cluster
    if '[' and ']' in line:
        a,b=re.findall("\d+\.\d+", line)
        upadte_elements(a, b, (current_cluster + 1))

plt.figure(figsize=(10, 6), dpi=120)
plt.scatter(x, y, c=color)
plt.ylabel('Y-Axis')
plt.xlabel('X-Axis')
plt.savefig('ClustersVisualization.png')
plt.title("Clusters", fontsize='large')
plt.show()