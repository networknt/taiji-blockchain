package com.networknt.taiji.avro;

import com.networknt.taiji.event.BlockchainEvent;
import com.networknt.taiji.event.EventId;
import com.networknt.taiji.token.TokenCreatedEvent;
import org.junit.Test;

import java.nio.ByteBuffer;

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
        TokenCreatedEvent event = TokenCreatedEvent.newBuilder()
                .setOwnerAddress("000085DF3b608aF058482391681C3a04C437776C")
                .setTokenAddress("0001a01963b09fe09DE62d83BD2B9A0c2b618D74")
                .setName("Light Token")
                .setSymbol("LIGHT")
                .setTotal(1000L)
                .setDecimals(9)
                .build();

        return event;

    }

    private BlockchainEvent blockchainEvent() {
        EventId eventId = EventId.newBuilder()
                .setAddress("000085DF3b608aF058482391681C3a04C437776C")
                .setNonce(1L)
                .build();

        TokenCreatedEvent tokenCreatedEvent = tokenCreatedEvent();
        AvroSerializer avroSerializer = new AvroSerializer();

        BlockchainEvent event = BlockchainEvent.newBuilder()
                .setEventId(eventId)
                .setEntityAddress("0001a01963b09fe09DE62d83BD2B9A0c2b618D74")
                .setEntityType("com.networknt.taiji.entity.Token")
                .setEventType(TokenCreatedEvent.class.getName())
                .setEventData(ByteBuffer.wrap(avroSerializer.serialize(tokenCreatedEvent)))
                .build();
        return event;
    }

    @Test
    public void testBlockchainEvent() {
        AvroSerializer avroSerializer = new AvroSerializer();
        byte[] bytes = avroSerializer.serialize(blockchainEvent());
        AvroDeserializer deserializer = new AvroDeserializer(true);
        BlockchainEvent blockchainEvent = (BlockchainEvent)deserializer.deserialize(bytes);
        ByteBuffer byteBuffer = blockchainEvent.getEventData();
        byte[] ba = new byte[byteBuffer.remaining()];
        byteBuffer.get(ba);

        Object object = deserializer.deserialize(ba);
        System.out.println("object class = " + object.getClass());
    }

    @Test
    public void testTokenCreatedEvent() {
        AvroSerializer avroSerializer = new AvroSerializer();
        byte[] bytes = avroSerializer.serialize(tokenCreatedEvent());
        AvroDeserializer deserializer = new AvroDeserializer(true);
        Object object = deserializer.deserialize(bytes);
        System.out.println("object class = " + object.getClass());
    }

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
