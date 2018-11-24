package com.networknt.template;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface App {

    String ID_TOKEN = "0000"; // Token is minted by an owner and represents vale or right that transferable.

    String getId();
    int getVersion();
    String getAddress();

    /**
     * The first byte in data is always indicate the purpose of the data. For distributed applications,
     * it uses "00" as the first byte in the data field follow the encoded app data.
     * @return String application indicator
     */
    @JsonIgnore
    default String getAppIndicator() {
        return "00";
    }
}
