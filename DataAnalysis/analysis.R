# US Air Quality Analysis
# Sushant Bansal 1410110454
# Pragya Chaturvedi 1410110289
# Ishan Tyagi 1410110164

library(tidyverse)
library(shiny)
library(shinydashboard)
library(plotly)
library(treemap)
library(data.tree)
library(d3treeR)
library(lubridate)

data1 <- read.csv("annual_all_2000.csv")
data2 <- read.csv("annual_all_2004.csv")
data3 <- read.csv("annual_all_2008.csv")
data4 <- read.csv("annual_all_2012.csv")
data5 <- read.csv("annual_all_2016.csv")

subData1 <- data1[,c("Latitude","Longitude","Parameter.Name","Pollutant.Standard",
                     "Units.of.Measure","Arithmetic.Mean","State.Name",
                     "County.Name","City.Name","Date.of.Last.Change")]
subData2 <- data2[,c("Latitude","Longitude","Parameter.Name","Pollutant.Standard",
                     "Units.of.Measure","Arithmetic.Mean","State.Name",
                     "County.Name","City.Name","Date.of.Last.Change")]
subData3 <- data3[,c("Latitude","Longitude","Parameter.Name","Pollutant.Standard",
                     "Units.of.Measure","Arithmetic.Mean","State.Name",
                     "County.Name","City.Name","Date.of.Last.Change")]
subData4 <- data4[,c("Latitude","Longitude","Parameter.Name","Pollutant.Standard",
                     "Units.of.Measure","Arithmetic.Mean","State.Name",
                     "County.Name","City.Name","Date.of.Last.Change")]
subData5 <- data5[,c("Latitude","Longitude","Parameter.Name","Pollutant.Standard",
                     "Units.of.Measure","Arithmetic.Mean","State.Name",
                     "County.Name","City.Name","Date.of.Last.Change")]

partMatter1 <- filter(subData1,Parameter.Name == c("PM2.5 - Local Conditions"),Pollutant.Standard == c("PM25 24-hour 2013"))
partMatter2 <- filter(subData2,Parameter.Name == c("PM2.5 - Local Conditions"),Pollutant.Standard == c("PM25 24-hour 2013"))
partMatter3 <- filter(subData3,Parameter.Name == c("PM2.5 - Local Conditions"),Pollutant.Standard == c("PM25 24-hour 2012"))
partMatter4 <- filter(subData4,Parameter.Name == c("PM2.5 - Local Conditions"),Pollutant.Standard == c("PM25 24-hour 2012"))
partMatter5 <- filter(subData5,Parameter.Name == c("PM2.5 - Local Conditions"),Pollutant.Standard == c("PM25 24-hour 2012"))
stateData1 <- aggregate(partMatter1[, c("Arithmetic.Mean")],list(partMatter1$State.Name), mean)
stateData2 <- aggregate(partMatter2[, c("Arithmetic.Mean")],list(partMatter2$State.Name), mean)
stateData3 <- aggregate(partMatter3[, c("Arithmetic.Mean")],list(partMatter3$State.Name), mean)
stateData4 <- aggregate(partMatter4[, c("Arithmetic.Mean")],list(partMatter4$State.Name), mean)
stateData5 <- aggregate(partMatter5[, c("Arithmetic.Mean")],list(partMatter5$State.Name), mean)
names(stateData1) = c("State.Name","PM2.5Levels")
names(stateData2) = c("State.Name","PM2.5Levels")
names(stateData3) = c("State.Name","PM2.5Levels")
names(stateData4) = c("State.Name","PM2.5Levels")
names(stateData5) = c("State.Name","PM2.5Levels")

ozone1 <- filter(subData1,Parameter.Name == c("Ozone"),Pollutant.Standard == c("Ozone 1-hour Daily 2005"))
ozone2 <- filter(subData2,Parameter.Name == c("Ozone"),Pollutant.Standard == c("Ozone 1-hour Daily 2005"))
ozone3 <- filter(subData3,Parameter.Name == c("Ozone"),Pollutant.Standard == c("Ozone 1-hour Daily 2005"))
ozone4 <- filter(subData4,Parameter.Name == c("Ozone"),Pollutant.Standard == c("Ozone 1-hour Daily 2005"))
ozone5 <- filter(subData5,Parameter.Name == c("Ozone"),Pollutant.Standard == c("Ozone 1-hour Daily 2005"))
stOzone1 <- aggregate(ozone1[, c("Arithmetic.Mean")],list(ozone1$State.Name), mean) 
stOzone2 <- aggregate(ozone2[, c("Arithmetic.Mean")],list(ozone2$State.Name), mean) 
stOzone3 <- aggregate(ozone3[, c("Arithmetic.Mean")],list(ozone3$State.Name), mean) 
stOzone4 <- aggregate(ozone4[, c("Arithmetic.Mean")],list(ozone4$State.Name), mean) 
stOzone5 <- aggregate(ozone5[, c("Arithmetic.Mean")],list(ozone5$State.Name), mean) 
names(stOzone1) <- c("State.Name","OzoneLevels")
names(stOzone2) <- c("State.Name","OzoneLevels")
names(stOzone3) <- c("State.Name","OzoneLevels")
names(stOzone4) <- c("State.Name","OzoneLevels")
names(stOzone5) <- c("State.Name","OzoneLevels")

s02_1 <- filter(subData1,Parameter.Name == c("Sulfur dioxide"),Pollutant.Standard == c("SO2 1-hour 2010"))
s02_2 <- filter(subData2,Parameter.Name == c("Sulfur dioxide"),Pollutant.Standard == c("SO2 1-hour 2010"))
s02_3 <- filter(subData3,Parameter.Name == c("Sulfur dioxide"),Pollutant.Standard == c("SO2 1-hour 2010"))
s02_4 <- filter(subData4,Parameter.Name == c("Sulfur dioxide"),Pollutant.Standard == c("SO2 1-hour 2010"))
s02_5 <- filter(subData5,Parameter.Name == c("Sulfur dioxide"),Pollutant.Standard == c("SO2 1-hour 2010"))
sts02_1 <- aggregate(s02_1[, c("Arithmetic.Mean")],list(s02_1$State.Name), mean) 
sts02_2 <- aggregate(s02_2[, c("Arithmetic.Mean")],list(s02_2$State.Name), mean) 
sts02_3 <- aggregate(s02_3[, c("Arithmetic.Mean")],list(s02_3$State.Name), mean) 
sts02_4 <- aggregate(s02_4[, c("Arithmetic.Mean")],list(s02_4$State.Name), mean) 
sts02_5 <- aggregate(s02_5[, c("Arithmetic.Mean")],list(s02_5$State.Name), mean) 
names(sts02_1) <- c("State.Name","S02Levels")
names(sts02_2) <- c("State.Name","S02Levels")
names(sts02_3) <- c("State.Name","S02Levels")
names(sts02_4) <- c("State.Name","S02Levels")
names(sts02_5) <- c("State.Name","S02Levels")

n02_1 <- filter(subData1,Parameter.Name == c("Nitrogen dioxide (NO2)"),Pollutant.Standard == c("NO2 1-hour"))
n02_2 <- filter(subData2,Parameter.Name == c("Nitrogen dioxide (NO2)"),Pollutant.Standard == c("NO2 1-hour"))
n02_3 <- filter(subData3,Parameter.Name == c("Nitrogen dioxide (NO2)"),Pollutant.Standard == c("NO2 1-hour"))
n02_4 <- filter(subData4,Parameter.Name == c("Nitrogen dioxide (NO2)"),Pollutant.Standard == c("NO2 1-hour"))
n02_5 <- filter(subData5,Parameter.Name == c("Nitrogen dioxide (NO2)"),Pollutant.Standard == c("NO2 1-hour"))
stn02_1 <- aggregate(n02_1[, c("Arithmetic.Mean")],list(n02_1$State.Name), mean) 
stn02_2 <- aggregate(n02_2[, c("Arithmetic.Mean")],list(n02_2$State.Name), mean) 
stn02_3 <- aggregate(n02_3[, c("Arithmetic.Mean")],list(n02_3$State.Name), mean) 
stn02_4 <- aggregate(n02_4[, c("Arithmetic.Mean")],list(n02_4$State.Name), mean) 
stn02_5 <- aggregate(n02_5[, c("Arithmetic.Mean")],list(n02_5$State.Name), mean) 
names(stn02_1) <- c("State.Name","n02Levels")
names(stn02_2) <- c("State.Name","n02Levels")
names(stn02_3) <- c("State.Name","n02Levels")
names(stn02_4) <- c("State.Name","n02Levels")
names(stn02_5) <- c("State.Name","n02Levels")

MO1 <- filter(subData1,Parameter.Name == c("Carbon monoxide"),Pollutant.Standard == c("CO 1-hour 1971"))
MO2 <- filter(subData2,Parameter.Name == c("Carbon monoxide"),Pollutant.Standard == c("CO 1-hour 1971"))
MO3 <- filter(subData3,Parameter.Name == c("Carbon monoxide"),Pollutant.Standard == c("CO 1-hour 1971"))
MO4 <- filter(subData4,Parameter.Name == c("Carbon monoxide"),Pollutant.Standard == c("CO 1-hour 1971"))
MO5 <- filter(subData5,Parameter.Name == c("Carbon monoxide"),Pollutant.Standard == c("CO 1-hour 1971"))
stMO1 <- aggregate(MO1[, c("Arithmetic.Mean")],list(MO1$State.Name), mean) 
stMO2 <- aggregate(MO2[, c("Arithmetic.Mean")],list(MO2$State.Name), mean) 
stMO3 <- aggregate(MO3[, c("Arithmetic.Mean")],list(MO3$State.Name), mean) 
stMO4 <- aggregate(MO4[, c("Arithmetic.Mean")],list(MO4$State.Name), mean) 
stMO5 <- aggregate(MO5[, c("Arithmetic.Mean")],list(MO5$State.Name), mean) 
names(stMO1) <- c("State.Name","COLevels")
names(stMO2) <- c("State.Name","COLevels")
names(stMO3) <- c("State.Name","COLevels")
names(stMO4) <- c("State.Name","COLevels")
names(stMO5) <- c("State.Name","COLevels")


cust_trans <- function(stateData){
  stateData$Hover <- with(stateData,paste(State.Name))
  stateData <- transform(stateData,
                         State.Name = state.abb[match(as.character(State.Name), state.name)])
}

stateData1 <- cust_trans(stateData1)
stateData2 <- cust_trans(stateData2)
stateData3 <- cust_trans(stateData3)
stateData4 <- cust_trans(stateData4)
stateData5 <- cust_trans(stateData5)
stOzone1 <- cust_trans(stOzone1)
stOzone2 <- cust_trans(stOzone2)
stOzone3 <- cust_trans(stOzone3)
stOzone4 <- cust_trans(stOzone4)
stOzone5 <- cust_trans(stOzone5)
sts02_1 <- cust_trans(sts02_1)
sts02_2 <- cust_trans(sts02_2)
sts02_3 <- cust_trans(sts02_3)
sts02_4 <- cust_trans(sts02_4)
sts02_5 <- cust_trans(sts02_5)
stn02_1 <- cust_trans(stn02_1)
stn02_2 <- cust_trans(stn02_2)
stn02_3 <- cust_trans(stn02_3)
stn02_4 <- cust_trans(stn02_4)
stn02_5 <- cust_trans(stn02_5)
stMO1 <- cust_trans(stMO1)
stMO2 <- cust_trans(stMO2)
stMO3 <- cust_trans(stMO3)
stMO4 <- cust_trans(stMO4)
stMO5 <- cust_trans(stMO5)

tmp1 <- stateData1
names(tmp1) <- c("State.Name","Level","Hover")
tmp2 <- stOzone1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(tmp1,tmp2)
tmp2 <- sts02_1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(yrAvDat1,tmp2)
tmp2 <- stn02_1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(yrAvDat1,tmp2)
tmp2 <- stMO1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(yrAvDat1,tmp2)
yrAvDat1 <- aggregate(yrAvDat1[, c("Level")],list(yrAvDat1$State.Name), mean)

tmp1 <- stateData2
names(tmp1) <- c("State.Name","Level","Hover")
tmp2 <- stOzone2
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat2 <- rbind(tmp1,tmp2)
tmp2 <- sts02_2
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat2 <- rbind(yrAvDat2,tmp2)
tmp2 <- stn02_2
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat2 <- rbind(yrAvDat2,tmp2)
tmp2 <- stMO2
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat2 <- rbind(yrAvDat2,tmp2)
yrAvDat2 <- aggregate(yrAvDat2[, c("Level")],list(yrAvDat2$State.Name), mean)

tmp1 <- stateData3
names(tmp1) <- c("State.Name","Level","Hover")
tmp2 <- stOzone3
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat3 <- rbind(tmp1,tmp2)
tmp2 <- sts02_3
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat3 <- rbind(yrAvDat3,tmp2)
tmp2 <- stn02_3
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat3 <- rbind(yrAvDat3,tmp2)
tmp2 <- stMO3
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat3 <- rbind(yrAvDat3,tmp2)
yrAvDat3 <- aggregate(yrAvDat3[, c("Level")],list(yrAvDat3$State.Name), mean)

tmp1 <- stateData4
names(tmp1) <- c("State.Name","Level","Hover")
tmp2 <- stOzone4
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat4 <- rbind(tmp1,tmp2)
tmp2 <- sts02_4
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat4 <- rbind(yrAvDat4,tmp2)
tmp2 <- stn02_4
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat4 <- rbind(yrAvDat4,tmp2)
tmp2 <- stMO4
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat4 <- rbind(yrAvDat4,tmp2)
yrAvDat4 <- aggregate(yrAvDat4[, c("Level")],list(yrAvDat4$State.Name), mean)

tmp1 <- stateData5
names(tmp1) <- c("State.Name","Level","Hover")
tmp2 <- stOzone5
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat5 <- rbind(tmp1,tmp2)
tmp2 <- sts02_5
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat5 <- rbind(yrAvDat5,tmp2)
tmp2 <- stn02_5
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat5 <- rbind(yrAvDat5,tmp2)
tmp2 <- stMO5
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat5 <- rbind(yrAvDat5,tmp2)
yrAvDat5 <- aggregate(yrAvDat5[, c("Level")],list(yrAvDat5$State.Name), mean)

tmp1 <- stateData1
names(tmp1) <- c("State.Name","Level","Hover")
tmp2 <- stOzone1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(tmp1,tmp2)
tmp2 <- sts02_1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(yrAvDat1,tmp2)
tmp2 <- stn02_1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(yrAvDat1,tmp2)
tmp2 <- stMO1
names(tmp2) <- c("State.Name","Level","Hover")
yrAvDat1 <- rbind(yrAvDat1,tmp2)
yrAvDat1 <- aggregate(yrAvDat1[, c("Level")],list(yrAvDat1$State.Name), mean)

########################################## 2016
histDF16 <- filter(data5,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
mymonths <- c("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
histDF16$month <- month(histDF16$X1st.Max.DateTime)
mymonths <- factor(mymonths,levels = mymonths)
histDF16$month <- mymonths[ histDF16$month ]

# % of each pollutant per month
plot16 <- ggplot(data = histDF16) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill") +
  labs(title = "Percentage of Each Pollutant in Each Month",
       y = "Precentage",
       x = "Months",colour = "Pollutant")

########################################## 2000
histDF00 <- filter(data1,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
histDF00$month <- month(histDF00$X1st.Max.DateTime)
histDF00$month <- mymonths[ histDF00$month ]

# % of each pollutant per month
plot00 <- ggplot(data = histDF00) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill")

plot00 <- plot00 + labs(title = "Percentage of Each Pollutant in Each Month",
                        y = "Precentage",
                        x = "Months",colour = "Pollutant")


########################################## 2004
histDF04 <- filter(data2,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
                     Parameter.Code == 88101 | Parameter.Code == 81102)
histDF04$month <- month(histDF04$X1st.Max.DateTime)
histDF04$month <- mymonths[ histDF04$month ]

# % of each pollutant per month
plot04 <- ggplot(data = histDF04) + 
  geom_bar(mapping = aes(x = month , fill = Parameter.Name), position = "fill")

plot04 <- plot04 + labs(title = "Percentage of Each Pollutant in Each Month",
                        y = "Precentage",
                        x = "Months",colour = "Pollutant")


########################################## 2008
histDF08 <- filter(data3,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
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
histDF12 <- filter(data4,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |
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

######Google maps
##Plot address wise ozone
#dataOzone <- filter(data5,Parameter.Code == 44201 | Parameter.Code ==42401 | Parameter.Code ==42101 | Parameter.Code ==42602 |Parameter.Code == 88101 | Parameter.Code == 81102)
#location <- dataOzone[,c("Address","Latitude","Longitude","Arithmetic.Mean")]
#location <- subset(location,!duplicated(Address))
#location$Address <- as.numeric(location$Address)
#location$Arithmetic.Mean <- round(location$Arithmetic.Mean,digits = 2)
#coordinates(location)=~Longitude+Latitude
#projection(location)=CRS("+init=epsg:4326")
#map <- plotGoogleMaps(location,zcol="Arithmetic.Mean",filename="EPA_GoogleMaps.html",layerName="EPA Stations")



# GUI [Shiny, D3Tree, GoogleMaps]

# give state boundaries a white border
l <- list(color = toRGB("white"), width = 2)
# specify some map projection/options
g <- list(
  scope = 'usa',
  projection = list(type = 'albers usa'),
  showlakes = FALSE,
  lakecolor = toRGB('white')
)

ui <- dashboardPage(
  dashboardHeader(title = "Pollution Levels Across USA",titleWidth = 300),
  dashboardSidebar(
    sidebarMenu(menuItem("Overall Pouultion Levels",tabName = "overall"),
                menuItem("State Wise Data",
                menuSubItem("PM 2.5 Levels", tabName = "PM25Levels"),
                menuSubItem("Ozone Levels", tabName = "ozLevels"),
                menuSubItem("Sulphur Dioxide Levels", tabName = "s02Levels"),
                menuSubItem("Nitrogen Dioxide Levels", tabName = "n02Levels"),
                menuSubItem("Carbon Monoxide Levels", tabName = "moLevels")),
                menuItem("CountyWiseData",
                menuSubItem("PM 2.5 Levels",tabName = "cPM25" ),
                menuSubItem("Ozone Levels",tabName = "cOzone" ),
                menuSubItem("Sulphur Dioxide Levels",tabName = "cSO2" ),
                menuSubItem("Nitrogen Dioxide Levels",tabName = "cNO2" ),
                menuSubItem("Carbon Monoxide Levels",tabName = "cMO" )),
                menuItem("Individual Year Data",
                menuSubItem("2016",tabName = "2016"),
                menuSubItem("2012",tabName = "2012"),
                menuSubItem("2008",tabName = "2008"),
                menuSubItem("2004",tabName = "2004"),
                menuSubItem("2000",tabName = "2000")
                ))
    ),
  dashboardBody(
    tabItems(
      tabItem( tabName = "overall",
               fluidRow(
                 box(plotlyOutput("plotX", height = 400),width = 12),
                 
                 box(
                   sliderInput("sliderX", "Choose Year", 2000, 2016, 2000,step = 4)
                 )
               )
      ),
      tabItem( tabName = "PM25Levels",
               fluidRow(
                 box(plotlyOutput("plot", height = 400),width = 12),
                 
                 box(
                   sliderInput("slider", "Choose Year", 2000, 2016, 2000,step = 4)
                 )
               )
            ),
      tabItem(tabName = "ozLevels",
            fluidRow(
              box(plotlyOutput("plot1", height = 400),width = 12),
        
              box(
                sliderInput("slider1", "Choose Year", 2000, 2016, 2000,step = 4)
              )
            )
          ),
      tabItem(tabName = "s02Levels",
              fluidRow(
                box(plotlyOutput("plot2", height = 400),width = 12),
                
                box(
                  sliderInput("slider2", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )
      ),
      tabItem(tabName = "n02Levels",
              fluidRow(
                  box(plotlyOutput("plot3", height = 400),width = 12),
                    
                  box(
                    sliderInput("slider3", "Choose Year", 2000, 2016, 2000,step = 4)
                  )
              )        
      ),
      tabItem(tabName = "moLevels",
              fluidRow(
                box(plotlyOutput("plot4", height = 400),width = 12),
                
                box(
                  sliderInput("slider4", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "cPM25",
          fluidRow(
            box(d3tree2Output("plot5",height = 400),width = 12),
            box(
              sliderInput("slider5", "Choose Year", 2000, 2016, 2000,step = 4)
            )
          )        
      ),
      tabItem(tabName = "cOzone",
              fluidRow(
                box(d3tree2Output("plot6",height = 400),width = 12),
                box(
                  sliderInput("slider6", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "cSO2",
              fluidRow(
                box(d3tree2Output("plot7",height = 400),width = 12),
                box(
                  sliderInput("slider7", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "cNO2",
              fluidRow(
                box(d3tree2Output("plot8",height = 400),width = 12),
                box(
                  sliderInput("slider8", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "cMO",
              fluidRow(
                box(d3tree2Output("plot9",height = 400),width = 12),
                box(
                  sliderInput("slider9", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "2016",
              fluidRow(
              box(plotlyOutput("plotY16",height = 500),width = 12)
              )     
        ),
      tabItem(tabName = "2012",
              fluidRow(
                box(plotlyOutput("plotY12",height = 500),width = 12)
              )     
      ),
      tabItem(tabName = "2008",
              fluidRow(
                box(plotlyOutput("plotY08",height = 500),width = 12)
              )     
      ),
      tabItem(tabName = "2004",
              fluidRow(
                box(plotlyOutput("plotY04",height = 500),width = 12)
              )     
      ),
      tabItem(tabName = "2000",
              fluidRow(
                box(plotlyOutput("plotY00",height = 500),width = 12)
              )     
      )
      
    )
  )
)


server <- function(input, output,session) {
  
  # renderPlotly() also understands ggplot2 objects!
  
  output$plotX <- renderPlotly({
    if(input$sliderX == 2000){
      yrAvDat <- yrAvDat1
    } else if(input$sliderX == 2004){
      yrAvDat <- yrAvDat2
    }else if(input$sliderX == 2008){
      yrAvDat <- yrAvDat3
    }else if(input$sliderX == 2012){
      yrAvDat <- yrAvDat4
    } else{
      yrAvDat <- yrAvDat5
    }
    
    plot_geo(yrAvDat, locationmode = 'USA-states') %>%
      add_trace(
        z = ~x,  locations = ~Group.1,
        color = ~x, colors = 'YlOrRd'
      ) %>%
      colorbar(title = "Pollutation Levels") %>%
      layout(
        title = 'Pollution Level',
        geo = g
      )
    
    })
  
  output$plot <- renderPlotly({
    if(input$slider == 2000){
      stateData <- stateData1
    } else if(input$slider == 2004){
      stateData <- stateData2
    }else if(input$slider == 2008){
      stateData <- stateData3
    }else if(input$slider == 2012){
      stateData <- stateData4
    } else{
      stateData <- stateData5
    }
    plot_geo(stateData, locationmode = 'USA-states') %>%
      add_trace(
        z = ~PM2.5Levels, text = ~Hover, locations = ~State.Name,
        color = ~PM2.5Levels, colors = 'YlOrRd'
      ) %>%
      colorbar(title = "PM 2.5 Levels [Micrograms / Cubic Meter]") %>%
      layout(
        title = 'PM 2.5 Levels by State',
        geo = g
      )
  })
  
  output$plot1 <- renderPlotly({
    if(input$slider1 == 2000){
      stateDataOz <- stOzone1
    } else if(input$slider1 == 2004){
      stateDataOz <- stOzone2
    }else if(input$slider1 == 2008){
      stateDataOz <- stOzone3
    }else if(input$slider1 == 2012){
      stateDataOz <- stOzone4
    } else{
      stateDataOz <- stOzone5
    }
    plot_geo(stateDataOz, locationmode = 'USA-states') %>%
      add_trace(
        z = ~OzoneLevels, text = ~Hover, locations = ~State.Name,
        color = ~OzoneLevels, colors = 'Blues'
      ) %>%
      colorbar(title = "Ozone Levels [Parts per Million]") %>%
      layout(
        title = 'Ozone Levels by State',
        geo = g
      )
  })
  
  output$plot2 <- renderPlotly({
    if(input$slider2 == 2000){
      stateDataS02 <- sts02_1
    } else if(input$slider2 == 2004){
      stateDataS02 <- sts02_2
    }else if(input$slider2 == 2008){
      stateDataS02 <- sts02_3
    }else if(input$slider2 == 2012){
      stateDataS02 <- sts02_4
    } else{
      stateDataS02 <- sts02_5
    }
    plot_geo(stateDataS02, locationmode = 'USA-states') %>%
      add_trace(
        z = ~S02Levels, text = ~Hover, locations = ~State.Name,
        color = ~S02Levels, colors = 'Oranges'
      ) %>%
      colorbar(title = "SO2 Levels [Parts per Billion]") %>%
      layout(
        title = 'Sulphur Dioxide Levels by State',
        geo = g
      )
  })
  
  output$plot3 <- renderPlotly({
    if(input$slider3 == 2000){
      stateDataN02 <- stn02_1
    } else if(input$slider3 == 2004){
      stateDataN02 <- stn02_2
    }else if(input$slider3 == 2008){
      stateDataN02 <- stn02_3
    }else if(input$slider3 == 2012){
      stateDataN02 <- stn02_4
    } else{
      stateDataN02 <- stn02_5
    }
    plot_geo(stateDataN02, locationmode = 'USA-states') %>%
      add_trace(
        z = ~n02Levels, text = ~Hover, locations = ~State.Name,
        color = ~n02Levels, colors = 'YlGnBu'
      ) %>%
      colorbar(title = "NO2 Levels [Parts per Billion]") %>%
      layout(
        title = 'Nitrogen Dioxide Levels by State',
        geo = g
      )
  })
  
  output$plot4 <- renderPlotly({
    if(input$slider4 == 2000){
      stateDataMO <- stMO1
    } else if(input$slider4 == 2004){
      stateDataMO <- stMO2
    }else if(input$slider4 == 2008){
      stateDataMO <- stMO3
    }else if(input$slider4 == 2012){
      stateDataMO <- stMO4
    } else{
      stateDataMO <- stMO5
    }
    plot_geo(stateDataMO, locationmode = 'USA-states') %>%
      add_trace(
        z = ~COLevels, text = ~Hover, locations = ~State.Name,
        color = ~COLevels, colors = 'BuPu'
      ) %>%
      colorbar(title = "CO Levels [Parts per Million]") %>%
      layout(
        title = 'Carbon Monoxide Levels by State',
        geo = g
      )
  })
  
  output$plotY16 <- renderPlotly({
    ggplotly(plot16)
  })
  output$plotY12 <- renderPlotly({
    ggplotly(plot12)
  })
  output$plotY08 <- renderPlotly({
    ggplotly(plot08)
  })
  output$plotY04 <- renderPlotly({
    ggplotly(plot04)
  })
  output$plotY00 <- renderPlotly({
    ggplotly(plot00)
  })
  
  
  output$plot5 <- renderD3tree2({
    if(input$slider5 == 2000){
      partMatter <- partMatter1
    } else if(input$slider5 == 2004){
      partMatter <- partMatter2
    }else if(input$slider5 == 2008){
      partMatter <- partMatter3
    }else if(input$slider5 == 2012){
      partMatter <- partMatter4
    } else{
      partMatter <- partMatter5
    }
    d3tree2(treemap(partMatter,index=c("State.Name","County.Name","City.Name"),
                    vSize = "Arithmetic.Mean",
                    type = "value", palette = "YlOrRd",
                    fontsize.title = 8,fontsize.labels = 6,
                    position.legend = "none"),
            rootname = "Click to Zoom Out",height = "100%",width = "100%")
  })
  output$plot6 <- renderD3tree2({
    if(input$slider6 == 2000){
      ozone <- ozone1
    } else if(input$slider6 == 2004){
      ozone <- ozone2
    }else if(input$slider6 == 2008){
      ozone <- ozone3
    }else if(input$slider6 == 2012){
      ozone <- ozone4
    } else{
      ozone <- ozone5
    }
    d3tree2(treemap(ozone,index=c("State.Name","County.Name","City.Name"),
                    vSize = "Arithmetic.Mean",
                    type = "value", palette = "Blues",
                    fontsize.title = 8,fontsize.labels = 6,
                    position.legend = "none"),
            rootname = "Click to Zoom Out",height = "100%",width = "100%")
  })
  output$plot7 <- renderD3tree2({
    if(input$slider7 == 2000){
      s02 <- s02_1
    } else if(input$slider7 == 2004){
      s02 <- s02_2
    }else if(input$slider7 == 2008){
      s02 <- s02_3
    }else if(input$slider7== 2012){
      s02 <- s02_4
    } else{
      s02 <- s02_5
    }
    d3tree2(treemap(s02,index=c("State.Name","County.Name","City.Name"),
                    vSize = "Arithmetic.Mean",
                    type = "value", palette = "Oranges",
                    fontsize.title = 8,fontsize.labels = 6,
                    position.legend = "none"),
            rootname = "Click to Zoom Out",height = "100%",width = "100%")
  })
  output$plot8 <- renderD3tree2({
    if(input$slider8 == 2000){
      n02 <- n02_1
    } else if(input$slider8 == 2004){
      n02 <- n02_2
    }else if(input$slider8 == 2008){
      n02 <- n02_3
    }else if(input$slider8 == 2012){
      n02 <- n02_4
    } else{
      n02 <- n02_5
    }
    d3tree2(treemap(n02,index=c("State.Name","County.Name","City.Name"),
                    vSize = "Arithmetic.Mean",
                    type = "value", palette = "YlGnBu",
                    fontsize.title = 8,fontsize.labels = 6,
                    position.legend = "none"),
            rootname = "Click to Zoom Out",height = "100%",width = "100%")
  })
  output$plot9 <- renderD3tree2({
    if(input$slider9 == 2000){
      MO <- MO1
    } else if(input$slider9 == 2004){
      MO <- MO2
    }else if(input$slider9 == 2008){
      MO <- MO3
    }else if(input$slider9 == 2012){
      MO <- MO4
    } else{
      MO <- MO5
    }
    d3tree2(treemap(MO,index=c("State.Name","County.Name","City.Name"),
                    vSize = "Arithmetic.Mean",
                    type = "value", palette = "BuPu",
                    fontsize.title = 8,fontsize.labels = 6,
                    position.legend = "none"),
            rootname = "Click to Zoom Out",height = "100%",width = "100%")
  })
    
}
remove(tmp1)
remove(tmp2)
runApp(list(ui = ui, server = server),host="127.0.0.1",port=5013, launch.browser = TRUE)


