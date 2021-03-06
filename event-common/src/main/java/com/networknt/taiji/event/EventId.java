/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.networknt.taiji.event;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class EventId extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5461600223561183495L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EventId\",\"namespace\":\"com.networknt.taiji.event\",\"fields\":[{\"name\":\"address\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"nonce\",\"type\":\"long\"},{\"name\":\"derived\",\"type\":\"boolean\",\"doc\":\"indicate if the event is derived from event processor\",\"default\":false}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<EventId> ENCODER =
      new BinaryMessageEncoder<EventId>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<EventId> DECODER =
      new BinaryMessageDecoder<EventId>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<EventId> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<EventId> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<EventId> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<EventId>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this EventId to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a EventId from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a EventId instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static EventId fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private String address;
   private long nonce;
  /** indicate if the event is derived from event processor */
   private boolean derived;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public EventId() {}

  /**
   * All-args constructor.
   * @param address The new value for address
   * @param nonce The new value for nonce
   * @param derived indicate if the event is derived from event processor
   */
  public EventId(String address, Long nonce, Boolean derived) {
    this.address = address;
    this.nonce = nonce;
    this.derived = derived;
  }

  public SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
    case 0: return address;
    case 1: return nonce;
    case 2: return derived;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: address = value$ != null ? value$.toString() : null; break;
    case 1: nonce = (Long)value$; break;
    case 2: derived = (Boolean)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'address' field.
   * @return The value of the 'address' field.
   */
  public String getAddress() {
    return address;
  }


  /**
   * Sets the value of the 'address' field.
   * @param value the value to set.
   */
  public void setAddress(String value) {
    this.address = value;
  }

  /**
   * Gets the value of the 'nonce' field.
   * @return The value of the 'nonce' field.
   */
  public long getNonce() {
    return nonce;
  }


  /**
   * Sets the value of the 'nonce' field.
   * @param value the value to set.
   */
  public void setNonce(long value) {
    this.nonce = value;
  }

  /**
   * Gets the value of the 'derived' field.
   * @return indicate if the event is derived from event processor
   */
  public boolean getDerived() {
    return derived;
  }


  /**
   * Sets the value of the 'derived' field.
   * indicate if the event is derived from event processor
   * @param value the value to set.
   */
  public void setDerived(boolean value) {
    this.derived = value;
  }

  /**
   * Creates a new EventId RecordBuilder.
   * @return A new EventId RecordBuilder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Creates a new EventId RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new EventId RecordBuilder
   */
  public static Builder newBuilder(Builder other) {
    if (other == null) {
      return new Builder();
    } else {
      return new Builder(other);
    }
  }

  /**
   * Creates a new EventId RecordBuilder by copying an existing EventId instance.
   * @param other The existing instance to copy.
   * @return A new EventId RecordBuilder
   */
  public static Builder newBuilder(EventId other) {
    if (other == null) {
      return new Builder();
    } else {
      return new Builder(other);
    }
  }

  /**
   * RecordBuilder for EventId instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<EventId>
    implements org.apache.avro.data.RecordBuilder<EventId> {

    private String address;
    private long nonce;
    /** indicate if the event is derived from event processor */
    private boolean derived;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.address)) {
        this.address = data().deepCopy(fields()[0].schema(), other.address);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.nonce)) {
        this.nonce = data().deepCopy(fields()[1].schema(), other.nonce);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.derived)) {
        this.derived = data().deepCopy(fields()[2].schema(), other.derived);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing EventId instance
     * @param other The existing instance to copy.
     */
    private Builder(EventId other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.address)) {
        this.address = data().deepCopy(fields()[0].schema(), other.address);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.nonce)) {
        this.nonce = data().deepCopy(fields()[1].schema(), other.nonce);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.derived)) {
        this.derived = data().deepCopy(fields()[2].schema(), other.derived);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'address' field.
      * @return The value.
      */
    public String getAddress() {
      return address;
    }


    /**
      * Sets the value of the 'address' field.
      * @param value The value of 'address'.
      * @return This builder.
      */
    public Builder setAddress(String value) {
      validate(fields()[0], value);
      this.address = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'address' field has been set.
      * @return True if the 'address' field has been set, false otherwise.
      */
    public boolean hasAddress() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'address' field.
      * @return This builder.
      */
    public Builder clearAddress() {
      address = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'nonce' field.
      * @return The value.
      */
    public long getNonce() {
      return nonce;
    }


    /**
      * Sets the value of the 'nonce' field.
      * @param value The value of 'nonce'.
      * @return This builder.
      */
    public Builder setNonce(long value) {
      validate(fields()[1], value);
      this.nonce = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'nonce' field has been set.
      * @return True if the 'nonce' field has been set, false otherwise.
      */
    public boolean hasNonce() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'nonce' field.
      * @return This builder.
      */
    public Builder clearNonce() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'derived' field.
      * indicate if the event is derived from event processor
      * @return The value.
      */
    public boolean getDerived() {
      return derived;
    }


    /**
      * Sets the value of the 'derived' field.
      * indicate if the event is derived from event processor
      * @param value The value of 'derived'.
      * @return This builder.
      */
    public Builder setDerived(boolean value) {
      validate(fields()[2], value);
      this.derived = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'derived' field has been set.
      * indicate if the event is derived from event processor
      * @return True if the 'derived' field has been set, false otherwise.
      */
    public boolean hasDerived() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'derived' field.
      * indicate if the event is derived from event processor
      * @return This builder.
      */
    public Builder clearDerived() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public EventId build() {
      try {
        EventId record = new EventId();
        record.address = fieldSetFlags()[0] ? this.address : (String) defaultValue(fields()[0]);
        record.nonce = fieldSetFlags()[1] ? this.nonce : (Long) defaultValue(fields()[1]);
        record.derived = fieldSetFlags()[2] ? this.derived : (Boolean) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<EventId>
    WRITER$ = (org.apache.avro.io.DatumWriter<EventId>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<EventId>
    READER$ = (org.apache.avro.io.DatumReader<EventId>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.address);

    out.writeLong(this.nonce);

    out.writeBoolean(this.derived);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.address = in.readString();

      this.nonce = in.readLong();

      this.derived = in.readBoolean();

    } else {
      for (int i = 0; i < 3; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.address = in.readString();
          break;

        case 1:
          this.nonce = in.readLong();
          break;

        case 2:
          this.derived = in.readBoolean();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }

  public String asString() {
    return String.format("%s-%016x-%b", address, nonce, derived);
  }

  @Override
  public String toString() {
    return "EventId{" + asString() + "}";
  }

  public static EventId fromString(String str) {
    String[] s = str.split("-");
    if (s.length != 3) throw new IllegalArgumentException("Should have length of 3: " + str);
    return new EventId(s[0], Long.parseUnsignedLong(s[1], 16), Boolean.parseBoolean(s[2]));
  }
}
