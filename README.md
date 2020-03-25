# stache-lag-backend
A tech task for Rock Seven

stache-lag-backend
A technical challenge for prospective backend developers at Rock 7 / YB Tracking

Parse the JSON document; it contains positional data "moments" from one of our transatlantic yacht races. You can visualise it here: https://yb.tl/arc2017

Populate this information appropriately into a MySQL database for subsequent processing.

Decide upon an effective method to determine the number of other vessels “visible” for a given moment. e.g. for each moment, decide the number of other vessels that would have been seen by a crew member on that vessel at that position/time (make any appropriate assumptions you need to).

Output a summary table showing average number of sightings per vessel, per day. For example, it should be possible to make conclusions such as "On day 6 of the rally, a vessel is likely to see 5 other vessels during the day" and "On the day before they finish, a typical vessel is likely to see 4 other vessels".
