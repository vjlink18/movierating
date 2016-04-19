SUMMARY
=============================================

MovieLens data sets were collected by the GroupLens Research Project
at the University of Minnesota.
 
This data set consists of:
	* 100,000 ratings (1-5) from 943 users on 1682 movies. 
	* Each user has rated at least 20 movies. 
        * Simple demographic info for the users (age, gender)

DETAILED DESCRIPTIONS OF DATA FILES
==============================================

Here is brief description of the data.

ratings.dat   -- The full data set, 100000 ratings by 943 users on 1682 items.
              Each user has rated at least 20 movies.  Users and items are
              numbered consecutively from 1.  The data is randomly
              ordered. The data is separated by the double colon character or 
	      “::”. Genres are separated by the comma character or “,”. The data
	      is organized as follows:

	      movie id :: movie name :: movie genre [,movie genre] :: 
	      user id :: user age :: user gender :: rating :: timestamp

	      There are a finite number of genres. Ratings are given on a 1-5 
	      scale. The timestamp is given in Posix time. 

