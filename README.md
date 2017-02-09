APRIORI ALGORITHM ASSOCIATION RULE MINING

Apriori is an algorithm for frequent item set mining and association rule learning over transactional databases. It proceeds by identifying the frequent individual items in the database and extending them to larger and larger item sets as long as those item sets appear sufficiently often in the database.


USAGE

Get the generated dataset from the file "retail_transactions.txt"
Complile AssociationRuleMining using javac or any other java IDE. Run the AssociationRuleMining class.

#Defaults
Minimum Threshold = 3
Input filename = "retail_transactions.txt"
Output filename = "processed_retail.txt"


Inputs from the user:
Enter minimum threshold <Integer> :                
Enter the input file name <String> :                                          
Do you want to out in a file( N for console) ? Y/N : 
Enter output file name <String> :                 

Output: 
The datasets generated from the algorithm after different passes. (Either on console or specified/default file)
Memory usage
Time taken


                         --------------------------------------------------------------------------------------


ZIPF INPUT GENERATION

According to the Zipf's law the frequency of occurrence of an instance of a class is roughly inversely proportional to the rank of that class in the frequency list.

USAGE 

Complile inputSetGenerator.java using javac or any other java IDE. Run the inputSetGenerator class.

#Defaults
Skew factor = 0.1
Number of transactions = 10000
Maximum number of items in each transactions = 5
Output file name = retail_transactions.txt


Inputs from the user:
Enter the skewFactor <Integer> : 
Enter number of transactions <Integer>:
Enter maximum number of items in each transaction <Integer>:
Enter output file name <String>:


Output:
Transactions dataset generated in the file specified 

