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
public class TokenTransactions extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4441667421904892524L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TokenTransactions\",\"namespace\":\"com.networknt.taiji.token\",\"fields\":[{\"name\":\"TokenTransactionArray\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"TokenTransaction\",\"fields\":[{\"name\":\"timestamp\",\"type\":\"long\",\"doc\":\"transaction timestamp in milliseconds from epoch\"},{\"name\":\"symbol\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"token symbol which is one word capitalized\"},{\"name\":\"type\",\"type\":{\"type\":\"enum\",\"name\":\"TokenTranType\",\"symbols\":[\"T\",\"A\"]},\"doc\":\"token transaction type, T for transaction and A for Approval and Allowance\"},{\"name\":\"from\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"from address\",\"default\":null},{\"name\":\"to\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"to address\"},{\"name\":\"amount\",\"type\":\"long\",\"doc\":\"transaction amount\"}]}},\"namespace\":\"com.networknt.taiji.token\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TokenTransactions> ENCODER =
      new BinaryMessageEncoder<TokenTransactions>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TokenTransactions> DECODER =
      new BinaryMessageDecoder<TokenTransactions>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<TokenTransactions> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<TokenTransactions> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TokenTransactions>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this TokenTransactions to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a TokenTransactions from a ByteBuffer. */
  public static TokenTransactions fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.util.List<com.networknt.taiji.token.TokenTransaction> TokenTransactionArray;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TokenTransactions() {}

  /**
   * All-args constructor.
   * @param TokenTransactionArray The new value for TokenTransactionArray
   */
  public TokenTransactions(java.util.List<com.networknt.taiji.token.TokenTransaction> TokenTransactionArray) {
    this.TokenTransactionArray = TokenTransactionArray;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return TokenTransactionArray;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: TokenTransactionArray = (java.util.List<com.networknt.taiji.token.TokenTransaction>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'TokenTransactionArray' field.
   * @return The value of the 'TokenTransactionArray' field.
   */
  public java.util.List<com.networknt.taiji.token.TokenTransaction> getTokenTransactionArray() {
    return TokenTransactionArray;
  }

  /**
   * Sets the value of the 'TokenTransactionArray' field.
   * @param value the value to set.
   */
  public void setTokenTransactionArray(java.util.List<com.networknt.taiji.token.TokenTransaction> value) {
    this.TokenTransactionArray = value;
  }

  /**
   * Creates a new TokenTransactions RecordBuilder.
   * @return A new TokenTransactions RecordBuilder
   */
  public static com.networknt.taiji.token.TokenTransactions.Builder newBuilder() {
    return new com.networknt.taiji.token.TokenTransactions.Builder();
  }

  /**
   * Creates a new TokenTransactions RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TokenTransactions RecordBuilder
   */
  public static com.networknt.taiji.token.TokenTransactions.Builder newBuilder(com.networknt.taiji.token.TokenTransactions.Builder other) {
    return new com.networknt.taiji.token.TokenTransactions.Builder(other);
  }

  /**
   * Creates a new TokenTransactions RecordBuilder by copying an existing TokenTransactions instance.
   * @param other The existing instance to copy.
   * @return A new TokenTransactions RecordBuilder
   */
  public static com.networknt.taiji.token.TokenTransactions.Builder newBuilder(com.networknt.taiji.token.TokenTransactions other) {
    return new com.networknt.taiji.token.TokenTransactions.Builder(other);
  }

  /**
   * RecordBuilder for TokenTransactions instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TokenTransactions>
    implements org.apache.avro.data.RecordBuilder<TokenTransactions> {

    private java.util.List<com.networknt.taiji.token.TokenTransaction> TokenTransactionArray;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.networknt.taiji.token.TokenTransactions.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.TokenTransactionArray)) {
        this.TokenTransactionArray = data().deepCopy(fields()[0].schema(), other.TokenTransactionArray);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TokenTransactions instance
     * @param other The existing instance to copy.
     */
    private Builder(com.networknt.taiji.token.TokenTransactions other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.TokenTransactionArray)) {
        this.TokenTransactionArray = data().deepCopy(fields()[0].schema(), other.TokenTransactionArray);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'TokenTransactionArray' field.
      * @return The value.
      */
    public java.util.List<com.networknt.taiji.token.TokenTransaction> getTokenTransactionArray() {
      return TokenTransactionArray;
    }

    /**
      * Sets the value of the 'TokenTransactionArray' field.
      * @param value The value of 'TokenTransactionArray'.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenTransactions.Builder setTokenTransactionArray(java.util.List<com.networknt.taiji.token.TokenTransaction> value) {
      validate(fields()[0], value);
      this.TokenTransactionArray = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'TokenTransactionArray' field has been set.
      * @return True if the 'TokenTransactionArray' field has been set, false otherwise.
      */
    public boolean hasTokenTransactionArray() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'TokenTransactionArray' field.
      * @return This builder.
      */
    public com.networknt.taiji.token.TokenTransactions.Builder clearTokenTransactionArray() {
      TokenTransactionArray = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TokenTransactions build() {
      try {
        TokenTransactions record = new TokenTransactions();
        record.TokenTransactionArray = fieldSetFlags()[0] ? this.TokenTransactionArray : (java.util.List<com.networknt.taiji.token.TokenTransaction>) defaultValue(fields()[0]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TokenTransactions>
    WRITER$ = (org.apache.avro.io.DatumWriter<TokenTransactions>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TokenTransactions>
    READER$ = (org.apache.avro.io.DatumReader<TokenTransactions>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
