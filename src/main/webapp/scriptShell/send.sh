#!/bin/bash
 my_array=$*
for i in $(seq 0 $#)
do 
table["$index"]="$i"
index=$(($index + 1))
done

from=From=${!table[1]}
to=To=${!table[4]}
message="${!table[5]}"
#echo $message
curl -k -X  POST https://api.twilio.com/2010-04-01/Accounts/${!table[2]}/SMS/Messages.json \
    -u ${!table[2]}:${!table[3]} \
    --data-urlencode $from \
    --data-urlencode $to \
    --data-urlencode "Body=$message"



