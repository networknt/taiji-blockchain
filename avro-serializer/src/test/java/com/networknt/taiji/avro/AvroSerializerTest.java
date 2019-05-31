package com.networknt.taiji.avro;

import com.networknt.taiji.event.EventId;
import com.networknt.taiji.token.TokenCreatedEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AvroSerializerTest {

    private final AvroSerializer avroSerializer;
    private final AvroDeserializer avroDeserializer;
    private final AvroDecoder avroDecoder;

    public AvroSerializerTest() {
        avroSerializer = new AvroSerializer();
        avroDeserializer = new AvroDeserializer();
        avroDecoder = new AvroDecoder();

    }

    private TokenCreatedEvent tokenCreatedEvent() {
        EventId eventId = EventId.newBuilder()
                .setAddress("000085DF3b608aF058482391681C3a04C437776C")
                .setNonce(1L)
                .build();

        TokenCreatedEvent event = TokenCreatedEvent.newBuilder()
                .setEventId(eventId)
                .setEntityAddress("0001a01963b09fe09DE62d83BD2B9A0c2b618D74")
                .setName("Light Token")
                .setSymbol("LIGHT")
                .setTotalSupply(1000L)
                .setDecimals(9)
                .build();

        return event;

    }

    @Disabled
    @Test
    public void testTokenCreatedEvent() {
        AvroSerializer avroSerializer = new AvroSerializer();
        byte[] bytes = avroSerializer.serialize(tokenCreatedEvent());
        AvroDeserializer deserializer = new AvroDeserializer(true);
        Object object = deserializer.deserialize(bytes);
        System.out.println("object class = " + object.getClass());
    }

    @Disabled
    @Test
    public void testKafkaAvroSerializer() {

        /*
        bytes = avroSerializer.serialize(null);
        Assert.assertEquals(null, avroDeserializer.deserialize(bytes));
        Assert.assertEquals(null, avroDecoder.fromBytes(bytes));


        bytes = avroSerializer.serialize(true);
        Assert.assertEquals(true, avroDeserializer.deserialize(bytes));
        Assert.assertEquals(true, avroDecoder.fromBytes(bytes));

        bytes = avroSerializer.serialize(123);
        Assert.assertEquals(123, avroDeserializer.deserialize(bytes));
        Assert.assertEquals(123, avroDecoder.fromBytes(bytes));

        bytes = avroSerializer.serialize(345L);
        Assert.assertEquals(345l, avroDeserializer.deserialize(bytes));
        Assert.assertEquals(345l, avroDecoder.fromBytes(bytes));

        bytes = avroSerializer.serialize(1.23f);
        Assert.assertEquals(1.23f, avroDeserializer.deserialize(bytes));
        Assert.assertEquals(1.23f, avroDecoder.fromBytes(bytes));

        bytes = avroSerializer.serialize(2.34d);
        Assert.assertEquals(2.34, avroDeserializer.deserialize(bytes));
        Assert.assertEquals(2.34, avroDecoder.fromBytes(bytes));

        bytes = avroSerializer.serialize("abc");
        Assert.assertEquals("abc", avroDeserializer.deserialize(bytes));
        Assert.assertEquals("abc", avroDecoder.fromBytes(bytes));

        bytes = avroSerializer.serialize(topic, "abc".getBytes());
        assertArrayEquals("abc".getBytes(), (byte[]) avroDeserializer.deserialize(topic, bytes));
        assertArrayEquals("abc".getBytes(), (byte[]) avroDecoder.fromBytes(bytes));

        bytes = avroSerializer.serialize(topic, new Utf8("abc"));
        assertEquals("abc", avroDeserializer.deserialize(topic, bytes));
        assertEquals("abc", avroDecoder.fromBytes(bytes));
        */
    }


}
