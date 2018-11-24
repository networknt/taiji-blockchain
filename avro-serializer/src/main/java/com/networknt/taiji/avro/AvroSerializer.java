package com.networknt.taiji.avro;

public class AvroSerializer extends AbstractAvroSerializer {

    public AvroSerializer() {
    }

    public byte[] serialize(Object record) {
        return serializeImpl(record);
    }

}
