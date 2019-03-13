library(tidyverse)
library(shiny)
library(shinydashboard)
library(plotly)
library(treemap)
library(data.tree)
library(d3Tree)
library(lubridate)

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
            box(d3treeOutput("plot5",height = 400),width = 12),
            box(
              sliderInput("slider5", "Choose Year", 2000, 2016, 2000,step = 4)
            )
          )        
      ),
      tabItem(tabName = "cOzone",
              fluidRow(
                box(d3treeOutput("plot6",height = 400),width = 12),
                box(
                  sliderInput("slider6", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "cSO2",
              fluidRow(
                box(d3treeOutput("plot7",height = 400),width = 12),
                box(
                  sliderInput("slider7", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "cNO2",
              fluidRow(
                box(d3treeOutput("plot8",height = 400),width = 12),
                box(
                  sliderInput("slider8", "Choose Year", 2000, 2016, 2000,step = 4)
                )
              )        
      ),
      tabItem(tabName = "cMO",
              fluidRow(
                box(d3treeOutput("plot9",height = 400),width = 12),
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