# G2 Exercise  Weather Balloon

###Description

There is a weather balloon traversing the globe, periodically taking observations. At each observation, the balloon records the temperature and its current location. When possible, the balloon relays this data back to observation posts on the ground.
A log line returned from the weather balloon looks something like this:

```
2014-12-31T13:44|10,5|243|AU
```

More formally this is:

```
<timestamp>|<location>|<temperature>|<observatory>
```

Where the timestamp is yyyy-MM-ddThh:mm in UTC.
Where the location is a co-ordinate x,y. And x, and y are natural numbers in observatory specific units.
Where the temperature is an integer representing temperature in observatory specific units.
Where the observatory is a code indicating where the measurements were relayed from.
Data from the balloon is of varying quality, so don't make any assumptions about the quality of the input.
Data from the balloon often comes in large batches, so assume you may need to deal with data that doesn't fit in memory.
Data from the balloon does not necessarily arrive in order.
Unfortunately, units of measurement are dependent on the observatory. The following is a lookup table for determining the correct unit of measure:

```
Observatory        Temperature	   Distance
AU                   celsius        km
US                   fahrenheit     miles
FR                   kelvin         m
All Others           kelvin         km
```


##Assumptions 

Expected inputs 

* Path input File
* Path output File
* Out Observatory 


###Prerequisites

* maven
* java 8


###Running 
We need 3 params to execute jar file

* pathInFile path input file with recolected data
* pathOutFile path output file that will be generated
* outObservatory out of unit measurement 


Example:

```
java -jar g2-exercise-webservices-0.0.1-SNAPSHOT.jar src/test/resources/testdata/input/weatherBalloonInputs.csv src/test/resources/testdata/output/weatherBalloonOutput.csv AU
```

## Author

* **Filiberto Hernandez**

