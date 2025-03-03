package com.test.bookledger.application.service;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.jupiter.api.Test
    public void solution() {
        String hexString = "850105435056303161025A4F0307D41000000140105704139200779000001352D20106017700000000000F5F340102";
        Map<String, String> tlvMap = new HashMap<>();
        try {
            parseTLV(hexString, 0, tlvMap);
        } catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        System.out.println(tlvMap);
    }

    private void parseTLV(String hexString, int index, Map<String, String> tlvMap) throws StringIndexOutOfBoundsException {
        if (index >= hexString.length()) return;

        int currIndex = index;
        // Type 2 Byte, Length 1 Byte
        int typeByte = 2;
        int lengthByte = 1;

        // 1 Byte 00 ~ FF, 2Byte 0000 ~ FFFF
        int typeHexSize = typeByte * 2;
        int lengthHexSize = lengthByte * 2;

        String type = hexString.substring(currIndex, currIndex + typeHexSize);
        currIndex += typeHexSize;

        String strLength = hexString.substring(currIndex, currIndex + lengthHexSize);
        int length = Integer.parseInt(strLength, 16);
        currIndex += lengthHexSize;

        String value = hexString.substring(currIndex, currIndex + length * 2);
        currIndex += length * 2;
        tlvMap.put(type, value);

        parseTLV(hexString, currIndex, tlvMap);
    }
}
