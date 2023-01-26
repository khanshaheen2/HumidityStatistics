This script reads in a directory of files with the extension ".csv" and processes the data contained in these files.
Each line of the file represents a measurement from a sensor with the format: "sensorId,humidity", where sensorId is a string and humidity is a double.

The script starts by getting the path of the directory passed as the first argument of the main method, and then gets all the files in the directory that end with ".csv".

Then, it reads the data from each file, and for each line, it splits the line into an array of strings, where the first element is the sensorId and the second element is the humidity. Then it tries to parse humidity as Double, if it fails it returns a None value.

The script then groups the measurements by sensorId, and for each group, it calculates the minimum, average, and maximum humidity values. If there are no measurements for a sensor, or if all measurements are failed (None or NaN), these values will be set to NaN.

At the end, the script prints some statistics about the processed data, and then it sorts the sensor values by descending maximum humidity, and prints the results in the following format: "sensorId,min,avg,max"

