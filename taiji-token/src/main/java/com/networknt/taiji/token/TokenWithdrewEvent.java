/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.networknt.taiji.token;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TokenWithdrewEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord, TokenEvent {
  private static final long serialVersionUID = -8175296731683570159L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TokenWithdrewEvent\",\"namespace\":\"com.networknt.taiji.token\",\"fields\":[{\"name\":\"EventId\",\"type\":{\"type\":\"record\",\"name\":\"EventId\",\"namespace\":\"com.networknt.taiji.event\",\"fields\":[{\"name\":\"address\",\"type\":\"string\"},{\"name\":\"nonce\",\"type\":\"long\"}]}},{\"name\":\"symbol\",\"type\":\"string\"},{\"name\":\"fromAddress\",\"type\":\"string\"},{\"name\":\"amount\",\"type\":\"long\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TokenWithdrewEvent> ENCODER =
      new BinaryMessageEncoder<TokenWithdrewEvent>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TokenWithdrewEvent> DECODER =
      new BinaryMessageDecoder<TokenWithdrewEvent>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<TokenWithdrewEvent> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<TokenWithdrewEvent> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TokenWithdrewEvent>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this TokenWithdrewEvent to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a TokenWithdrewEvent from a ByteBuffer. */
  public static TokenWithdrewEvent fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public com.networknt.taiji.event.EventId EventId;
  @Deprecated public java.lang.CharSequence symbol;
  @Deprecated public java.lang.CharSequence fromAddress;
  @Deprecated public long amount;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TokenWithdrewEvent() {}

  /**
   * All-args constructor.
   * @param EventId The new value for EventId
   * @param symbol The new value for symbol
   * @param fromAddress The new value for fromAddress
   * @param amount The new value for amount
   */
  public TokenWithdrewEvent(com.networknt.taiji.event.EventId EventId, java.lang.CharSequence symbol, java.lang.CharSequence fromAddress, java.lang.Long amount) {
    this.EventId = EventId;
    this.symbol = symbol;
    this.fromAddress = fromAddress;
    this.amount = amount;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return EventId;
    case 1: return symbol;
    case 2: return fromAddress;
    case 3: return amount;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: EventId = (com.networknt.taiji.event.EventId)value$; break;
    case 1: symbol = (java.lang.CharSequence)value$; break;
    case 2: fromAddress = (java.lang.CharSequence)value$; break;
    case 3: amount = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'EventId' field.
   * @return The value of the 'EventId' field.
   */
  public com.networknt.taiji.event.EventId getEventId() {
    return EventId;
  }

  /**
   * Sets the value of the 'EventId' field.
   * @param value the value to set.
   */
  public void setEventId(com.networknt.taiji.event.EventId value) {
    this.EventId = value;
  }

  /**
   * Gets the value of the 'symbol' field.
   * @return The value of the 'symbol' field.
   */
  public java.lang.CharSequence getSymbol() {
    return symbol;
  }

  /**
   * Sets the value of the 'symbol' field.
   * @param value the value to set.
   */
  public void setSymbol(java.lang.CharSequence value) {
    this.symbol = value;
  }

  /**
   * Gets the value of the 'fromAddress' field.
   * @return The value of the 'fromAddress' field.
   */
  public java.lang.CharSequence getFromAddress() {
    return fromAddress;
  }

  /**
   * Sets the value of the 'fromAddress' field.
   * @param value the value to set.
   */
  public void setFromAddress(java.lang.CharSequence value) {
    this.fromAddress = value;
  }

  /**
   * Gets the value of the 'amount' field.
   * @return The value of the 'amount' field.
   */
  public java.lang.Long getAmount() {
    return amount;
  }

  /**
   * Sets the value of the 'amount' field.
   * @param value the value to set.
   */
  public void setAmount(java.lang.Long value) {
    this.amount = value;
  }

  /**
   * Creates a new TokenWithdrewEvent RecordBuilder.
   * @return A new TokenWithdrewEvent RecordBuilder
   */
  public static com.networknt.taiji.token.TokenWithdrewEvent.Builder newBuilder() {
    return new com.networknt.taiji.token.TokenWithdrewEvent.Builder();
  }

  /**
   * Creates a new TokenWithdrewEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TokenWithdrewEvent RecordBuilder
   */
  public static com.networknt.taiji.token.TokenWithdrewEvent.Builder newBuilder(com.networknt.taiji.token.TokenWithdrewEvent.Builder other) {
    return new com.networknt.taiji.token.TokenWithdrewEvent.Builder(other);
  }

  /**
   * Creates a new TokenWithdrewEvent RecordBuilder by copying an existing TokenWithdrewEvent instance.
   * @param other The existing instance to copy.
   * @return A new TokenWithdrewEvent RecordBuilder
   */
  public static com.networknt.taiji.token.TokenWithdrewEvent.Builder newBuilder(com.networknt.taiji.token.TokenWithdrewEvent other) {
    return new com.networknt.taiji.token.TokenWithdrewEvent.Builder(other);
  }

  /**
   * RecordBuilder for TokenWithdrewEvent instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TokenWithdrewEvent>
    implements org.apache.avro.data.RecordBuilder<TokenWithdrewEvent> {

    private com.networknt.taiji.event.EventId EventId;
    private com.networknt.taiji.event.EventId.Builder EventIdBuilder;
    private java.lang.CharSequence symbol;
    private java.lang.CharSequence fromAddress;
    private long amount;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.networknt.taiji.token.TokenWithdrewEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.EventId)) {
        this.EventId = data().deepCopy(fields()[0].schema(), other.EventId);
        fieldSetFlags()[0] = true;
      }
      if (other.hasEventIdBuilder()) {
        this.EventIdBuilder = com.networknt.taiji.event.EventId.newBuilder(other.getEventIdBuilder());
      }
      if (isValidValue(fields()[1], other.symbol)) {
        this.symbol = data().deepCopy(fields()[1].schema(), other.symbol);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.fromAddress)) {
        this.fromAddress = data().deepCopy(fields()[2].schema(), other.fromAddress);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.amount)) {
        this.amount = data().deepCopy(fields()[3].schema(), other.amount);
        fieldSetFlags()[3] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TokenWithdrewEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(com.networknt.taiji.token.TokenWithdrewEvent other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.EventId)) {
        this.EventId = data().deepCopy(fields()[0].schema(), other.EventId);
        fieldSetFlags()[0] = true;
      }
      this.EventIdBuilder = null;
      if (isValidValue(fields()[1], other.symbol)) {
        this.symbol = data().deepCopy(fields()[1].schema(), other.symbol);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.fromAddress)) {
        this.fromAddress = data().deepCopy(fields()[2].schema(), other.fromAddress);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.amount)) {
        this.amount = data().deepCopy(fields()[3].schema(), other.amount);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'EventId' field.
      * @return The value.
      */
    public com.networknt.taiji.event.EventId getEventId() {
      return EventId;
    }

    /**
      * Sets the value of the 'EventId' field.
      * @param value The value of 'EventId'.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder setEventId(com.networknt.taiji.event.EventId value) {
      validate(fields()[0], value);
      this.EventIdBuilder = null;
      this.EventId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'EventId' field has been set.
      * @return True if the 'EventId' field has been set, false otherwise.
      */
    public boolean hasEventId() {
      return fieldSetFlags()[0];
    }

    /**
     * Gets the Builder instance for the 'EventId' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.networknt.taiji.event.EventId.Builder getEventIdBuilder() {
      if (EventIdBuilder == null) {
        if (hasEventId()) {
          setEventIdBuilder(com.networknt.taiji.event.EventId.newBuilder(EventId));
        } else {
          setEventIdBuilder(com.networknt.taiji.event.EventId.newBuilder());
        }
      }
      return EventIdBuilder;
    }

    /**
     * Sets the Builder instance for the 'EventId' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder setEventIdBuilder(com.networknt.taiji.event.EventId.Builder value) {
      clearEventId();
      EventIdBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'EventId' field has an active Builder instance
     * @return True if the 'EventId' field has an active Builder instance
     */
    public boolean hasEventIdBuilder() {
      return EventIdBuilder != null;
    }

    /**
      * Clears the value of the 'EventId' field.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder clearEventId() {
      EventId = null;
      EventIdBuilder = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'symbol' field.
      * @return The value.
      */
    public java.lang.CharSequence getSymbol() {
      return symbol;
    }

    /**
      * Sets the value of the 'symbol' field.
      * @param value The value of 'symbol'.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder setSymbol(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.symbol = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'symbol' field has been set.
      * @return True if the 'symbol' field has been set, false otherwise.
      */
    public boolean hasSymbol() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'symbol' field.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder clearSymbol() {
      symbol = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'fromAddress' field.
      * @return The value.
      */
    public java.lang.CharSequence getFromAddress() {
      return fromAddress;
    }

    /**
      * Sets the value of the 'fromAddress' field.
      * @param value The value of 'fromAddress'.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder setFromAddress(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.fromAddress = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'fromAddress' field has been set.
      * @return True if the 'fromAddress' field has been set, false otherwise.
      */
    public boolean hasFromAddress() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'fromAddress' field.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder clearFromAddress() {
      fromAddress = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'amount' field.
      * @return The value.
      */
    public java.lang.Long getAmount() {
      return amount;
    }

    /**
      * Sets the value of the 'amount' field.
      * @param value The value of 'amount'.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder setAmount(long value) {
      validate(fields()[3], value);
      this.amount = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'amount' field has been set.
      * @return True if the 'amount' field has been set, false otherwise.
      */
    public boolean hasAmount() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'amount' field.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenWithdrewEvent.Builder clearAmount() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TokenWithdrewEvent build() {
      try {
        TokenWithdrewEvent record = new TokenWithdrewEvent();
        if (EventIdBuilder != null) {
          record.EventId = this.EventIdBuilder.build();
        } else {
          record.EventId = fieldSetFlags()[0] ? this.EventId : (com.networknt.taiji.event.EventId) defaultValue(fields()[0]);
        }
        record.symbol = fieldSetFlags()[1] ? this.symbol : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.fromAddress = fieldSetFlags()[2] ? this.fromAddress : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.amount = fieldSetFlags()[3] ? this.amount : (java.lang.Long) defaultValue(fields()[3]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TokenWithdrewEvent>
    WRITER$ = (org.apache.avro.io.DatumWriter<TokenWithdrewEvent>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TokenWithdrewEvent>
    READER$ = (org.apache.avro.io.DatumReader<TokenWithdrewEvent>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
