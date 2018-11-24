package com.networknt.template;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.config.Config;
import org.junit.Test;

public class TokenTest {

    @Test
    public void testSerde() throws Exception {
        Token token = new Token("000085DF3b608aF058482391681C3a04C437776C", "Light Community Token", "LIGHT", 10000000);

        final String json = Config.getInstance().getMapper().writeValueAsString(token);
        final JsonNode jsonNode = Config.getInstance().getMapper().readTree(json);
        final String type = jsonNode.get("@type").asText();
        final Class<?> cls = Class.forName(type);
        final App obj = (App)Config.getInstance().getMapper().convertValue(jsonNode, cls);
        System.out.println(obj.getClass());


    }
}
