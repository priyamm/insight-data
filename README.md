#Insight Data Coding Challenge
I have made the project on Java.

Run the MedianDegree.java file in the src/com/insightdatascience/ folder to get the output to the /venmo_output/output.txt file.

If it doesn't run then you might have to open ot in eclipse and run the MedianDegree.java file to let it take input from the venmo_input//venmo-trans.txt and will automatically write to /venmo_output/output.txt file at present at the base folder.

I have made the use of HashMap and List to store the values...
#Explanation below:

{"created_time": "2016-03-28T23:23:12Z", "target": "Raffi-Antilian", "actor": "Amber-Sauer"}<br/>
{"created_time": "2016-03-28T23:23:12Z", "target": "Caroline-Kaiser-2", "actor": "Amber-Sauer"}<br/>
{"created_time": "2016-03-28T23:23:12Z", "target": "charlotte-macfarlane", "actor": "Amber-Sauer"}<br/>

HashMap to allot numbers to actor and target:
Raffi-Antilian  &nbsp &nbsp - &nbsp &nbsp  1<br/>
Amber-Sauer     &nbsp &nbsp - &nbsp &nbsp 2<br/>
Caroline-Kaiser-2 &nbsp &nbsp - &nbsp &nbsp   3<br/>
charlotte-macfarlane &nbsp &nbsp - &nbsp &nbsp  4<br/>

Created a Transaction class so that we can create a link
class Transaction{
	public Date createdTime;<br/>
	public Integer target;<br/>
	public Integer actor;<br/>
	public Integer status;<br/>
}

Storing all the Transactions in List<Transaction>

Another HasMap to count the number of links to the actor/target

actor/target  &nbsp &nbsp  Link<br/>
1	&nbsp &nbsp &nbsp &nbsp	1<br/>
2	&nbsp &nbsp &nbsp &nbsp	3<br/>
3	&nbsp &nbsp &nbsp &nbsp	1<br/>
4	&nbsp &nbsp &nbsp &nbsp	1<br/>

Now checking the status from each transaction to see if the WINDOW_SIZE of 60 sec is fulfilled or not.

Finally printing out the results.
