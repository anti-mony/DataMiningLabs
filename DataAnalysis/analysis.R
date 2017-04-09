######Static Charts
library(tidyverse)
library(lubridate)

########################################## 2016
setwd("C:/Users/pragy/Desktop")
data16 <- read.csv("annual_all_2016.csv")
histDF16 <- filter(data16,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
mymonths <- c("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
histDF16$month <- month(histDF16$X1st.Max.DateTime)
mymonths <- factor(mymonths,levels = mymonths)
histDF16$month <- mymonths[ histDF16$month ]

# % of each pollutant per month
plot16 <- ggplot(data = histDF16) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill")

plot16 <- plot16 + labs(title = "Percentage of Each Pollutant in Each Month",
                        y = "Precentage",
                        x = "Months",colour = "Pollutant")
ggplotly(plot16)

########################################## 2000
data00 <- read.csv("annual_all_2000.csv")
histDF00 <- filter(data00,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
histDF00$month <- month(histDF00$X1st.Max.DateTime)
histDF00$month <- mymonths[ histDF00$month ]

# % of each pollutant per month
plot00 <- ggplot(data = histDF00) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill")

plot00 <- plot00 + labs(title = "Percentage of Each Pollutant in Each Month",
                        y = "Precentage",
                        x = "Months",colour = "Pollutant")
ggplotly(plot00)


########################################## 2004
data04 <- read.csv("annual_all_2004.csv")
histDF04 <- filter(data04,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
histDF04$month <- month(histDF04$X1st.Max.DateTime)
histDF04$month <- mymonths[ histDF04$month ]

# % of each pollutant per month
plot04 <- ggplot(data = histDF04) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill")

plot04 <- plot04 + labs(title = "Percentage of Each Pollutant in Each Month",
                        y = "Precentage",
                        x = "Months",colour = "Pollutant")
ggplotly(plot04)


########################################## 2008
data08 <- read.csv("annual_all_2008.csv")
histDF08 <- filter(data08,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
histDF08$month <- month(histDF08$X1st.Max.DateTime)
histDF08$month <- mymonths[ histDF08$month ]

# % of each pollutant per month
plot08 <- ggplot(data = histDF08) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill")

plot08 <- plot08 + labs(title = "Percentage of Each Pollutant in Each Month",
                        y = "Precentage",
                        x = "Months",colour = "Pollutant")
ggplotly(plot08)

########################################## 2012
data12 <- read.csv("annual_all_2012.csv")
histDF12 <- filter(data12,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
histDF12$X1st.Max.DateTime <- as.Date(histDF12$X1st.Max.DateTime)
histDF12$month <- month(histDF12$X1st.Max.DateTime)
histDF12$month <- mymonths[ histDF12$month ]
histDF12 <- subset(histDF12,!is.na(month))

# % of each pollutant per month
plot12 <- ggplot(data = histDF12) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill")

plot12 <- plot12 + labs(title = "Percentage of Each Pollutant in Each Month",
                        y = "Precentage",
                        x = "Months",colour = "Pollutant")
ggplotly(plot12)







library(tidyverse)
library(plotGoogleMaps)
library(xts)
library(raster)
library(sp)

setwd("C:/Users/pragy/Desktop")
data <- read.csv("annual_all_2016.csv")
######Google maps
##Plot address wise ozone
dataOzone <- filter(data,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |Parameter.Code == 88101 | Parameter.Code == 81102)
location <- dataOzone[,c("Address","Latitude","Longitude","Arithmetic.Mean")]
location <- subset(location,!duplicated(Address))
location$Address <- as.numeric(location$Address)
location$Arithmetic.Mean <- round(location$Arithmetic.Mean,digits = 2)
coordinates(location)=~Longitude+Latitude
projection(location)=CRS("+init=epsg:4326")
map <- plotGoogleMaps(location,zcol="Arithmetic.Mean",filename="EPA_GoogleMaps.html",layerName="EPA Stations")










