#!/bin/bash

# -string is very necessary, otherwise the key of map will be Utf8 internal class. You cannot find object with string.

java -jar /home/steve/tool/avro-tools-1.8.2.jar compile -string schema KycCreatedEvent.avsc .
java -jar /home/steve/tool/avro-tools-1.8.2.jar compile -string schema KycVerifiedEvent.avsc .
java -jar /home/steve/tool/avro-tools-1.8.2.jar compile -string schema KycUpdatedEvent.avsc .

# Update events to implement from KycEvent interface in order to group these events in streams processing.

find . -name '*Event.java' -exec sed -i "s/implements org.apache.avro.specific.SpecificRecord/implements KycEvent/g" {} +

# move to the right directory and remove the generated folder.

mv com/networknt/taiji/kyc/KycCreatedEvent.java ../java/com/networknt/taiji/kyc
mv com/networknt/taiji/kyc/KycVerifiedEvent.java ../java/com/networknt/taiji/kyc
mv com/networknt/taiji/kyc/KycType.java ../java/com/networknt/taiji/kyc
mv com/networknt/taiji/kyc/KycUpdatedEvent.java ../java/com/networknt/taiji/kyc

rm -rf com
