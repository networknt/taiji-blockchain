#!/bin/bash

# -string is very necessary, otherwise the key of map will be Utf8 internal class. You cannot find object with string.

java -jar /home/steve/tool/avro-tools-1.9.2.jar compile -string schema TokenApprovedEvent.avsc .
java -jar /home/steve/tool/avro-tools-1.9.2.jar compile -string schema TokenCreatedEvent.avsc .
java -jar /home/steve/tool/avro-tools-1.9.2.jar compile -string schema TokenTransferredEvent.avsc .
java -jar /home/steve/tool/avro-tools-1.9.2.jar compile -string schema TokenWithdrewEvent.avsc .

# Update events to implement from TokenEvent interface in order to group these events in streams processing.

find . -name '*Event.java' -exec sed -i "s/implements org.apache.avro.specific.SpecificRecord/implements TokenEvent/g" {} +

# move to the right directory and remove the generated folder.

mv com/networknt/taiji/token/TokenApprovedEvent.java ../java/com/networknt/taiji/token
mv com/networknt/taiji/token/TokenCreatedEvent.java ../java/com/networknt/taiji/token
mv com/networknt/taiji/token/TokenTransferredEvent.java ../java/com/networknt/taiji/token
mv com/networknt/taiji/token/TokenTranType.java ../java/com/networknt/taiji/token
mv com/networknt/taiji/token/TokenWithdrewEvent.java ../java/com/networknt/taiji/token

rm -rf com
