
package com.networknt.taiji.crypto;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Fee {

    
    private Integer interChain;
    
    private Integer innerChain;
    
    private Integer application;
    
    private String bankAddress;
    

    public Fee () {
    }

    
    
    @JsonProperty("interChain")
    public Integer getInterChain() {
        return interChain;
    }

    public void setInterChain(Integer interChain) {
        this.interChain = interChain;
    }
    
    
    
    @JsonProperty("innerChain")
    public Integer getInnerChain() {
        return innerChain;
    }

    public void setInnerChain(Integer innerChain) {
        this.innerChain = innerChain;
    }
    
    
    
    @JsonProperty("application")
    public Integer getApplication() {
        return application;
    }

    public void setApplication(Integer application) {
        this.application = application;
    }
    
    
    
    @JsonProperty("bankAddress")
    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fee Fee = (Fee) o;

        return Objects.equals(interChain, Fee.interChain) &&
        Objects.equals(innerChain, Fee.innerChain) &&
        Objects.equals(application, Fee.application) &&
        
        Objects.equals(bankAddress, Fee.bankAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interChain, innerChain, application,  bankAddress);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Fee {\n");
        
        sb.append("    interChain: ").append(toIndentedString(interChain)).append("\n");
        sb.append("    innerChain: ").append(toIndentedString(innerChain)).append("\n");
        sb.append("    application: ").append(toIndentedString(application)).append("\n");
        sb.append("    bankAddress: ").append(toIndentedString(bankAddress)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
