#Graded Assignment 4
#Sushant Bansal 1410110454
#Pragya Chaturvedi 1410110289
#Ishan Tyagi 1410110164
# Processing the classic Pride and Prejudice by Jane Austen

library(SnowballC)
library(tm)
library(wordcloud2)
library(tidyverse)
library(shiny)
library(shinydashboard)

#Pre-Processing
book <- readLines("book.txt")
crps <- Corpus(VectorSource(book))
crps <- tm_map(crps,removePunctuation)
crps <- tm_map(crps,removeNumbers)
crps <- tm_map(crps,tolower)
crps <- tm_map(crps,removeWords,stopwords("english"))
crps <- tm_map(crps,stripWhitespace)

#Processing
dtm <- DocumentTermMatrix(crps)
WordFrequency <- colSums(as.matrix(dtm))
WordFrequency <- as_tibble(WordFrequency)
WordFrequency<-rownames_to_column(WordFrequency,var = "rowname")
WordFrequency<-data.frame(WordFrequency)
colnames(WordFrequency) = c("word","freq")
rownames(WordFrequency) = NULL
WordFrequency<-arrange(WordFrequency,desc(freq),word)

#UI
ui <- dashboardPage(
  dashboardHeader(title = "Pride and Prejudice Word Analysis",titleWidth = 350),
  dashboardSidebar(
    sidebarMenu(menuItem("WordCloud",tabName = "overall"),
                sliderInput("max",label = "Select Max number of Words",
                            min = 20,max = 250,animate = TRUE,value = 45),
                sliderInput("freq",label = "Select Min frequency of Words",
                            min = 1,max = 400,animate = TRUE,value = 250),
                menuItem("Search for a word!"),
                sidebarSearchForm(textId = "searchText", buttonId = "searchButton",
                                  label = "Search..."))),
  dashboardBody(
    tabItems(
      tabItem(tabName = "overall",
               fluidRow(
                 box(wordcloud2Output("plot",height = "535px",width = "100%"),width = 12)
              )
      )
    )
  )
)

#Backend Server 
server <- function(input, output, session) {
  #Genarating Wordcloud with input from slider defined in UI
  output$plot <- renderWordcloud2({
    wordcloud2(head(filter(WordFrequency,freq>input$freq),input$max),color="random-dark",
               size = 1,fontFamily = "AvantGarde",ellipticity = 0.65 )
  })
  
  #Search for a Word
  observeEvent(input$searchButton,{
    resMat = filter(WordFrequency,word==input$searchText)
    if(nrow(resMat)>0){
      fRes = as.character(resMat[1,2])
      resString = paste("Found with Frequency:",fRes, sep=" ")
      
    }else{
      resString = c("Not Found")
  }
  showModal(modalDialog(
      title = "Results",resString,easyClose = TRUE,fade = TRUE))
  })
}

runApp(list(ui = ui, server = server),host="127.0.0.1",port=5013, launch.browser = TRUE)

