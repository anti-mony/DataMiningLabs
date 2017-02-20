#Sushant Bansal 1410110454
#Pragya Chaturvedi 1410110289
#Ishan Tyagi 1410110164
#Python Visualiser

import matplotlib.pyplot as plt
from wordcloud import WordCloud

# Read the whole text.
tempText = open("processed_retail.txt")
freqCount = dict()
for line in tempText:
	if not line.startswith('['):
		continue
	linex = line.replace(" ","").replace("[","").replace("]","").replace(":"," ").replace(",","")
	pieces = linex.split()
	freqCount[pieces[0]] = int(pieces[1])
# print freqCount
listOfKeys = freqCount.keys()
listOfKeys.sort(key = len, reverse = True)
check = 0
count = 0
initialLength = 0
text = ""
for k in listOfKeys:
	initialLength = len(k)
	break
	# print k, sortedDict[k]


for key in listOfKeys:
	if check == 10 or initialLength == len(key) + 1:
		if initialLength == len(key) + 1:
			initialLength = initialLength - 1
			check = 0
		continue
	count = 0;
	while count <= freqCount[key]:	
		text = text + key + " " 
		count = count + 1
	text += '\n'
	check = check + 1
	
# print text	

# read the mask image
# taken from
# http://www.stencilry.org/stencils/movies/alice%20in%20wonderland/255fk.jpg
# alice_mask = np.array(Image.open("img.jpg"))

wc = WordCloud(background_color="white",width=960 , height=540 )
# generate word cloud
wc.generate(text)

# store to file
wc.to_file("visualData.png")

# show
plt.figure( figsize=(13,7) )
plt.imshow(wc)
plt.axis("off")
plt.show()