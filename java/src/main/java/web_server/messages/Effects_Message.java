package web_server.messages;

import web_server.data.*;

import java.nio.*;

class Effects_Message implements IMessage
{
    private Header header;
    private final int EFFECTS_MESSAGE_ID = 1;
    private final int EFFECTS_MESSAGE_SIZE = 96;
    private Effects_Enum effect_num;
    private Color_Enums color_num;
    private int duration_sec;

    Effects_Message()
    {
        header = new Header(EFFECTS_MESSAGE_ID, EFFECTS_MESSAGE_SIZE);
    }

    public void init()
    {
        header.setTimestamp(System.currentTimeMillis()/1000);
    }

    @Override
    public boolean deserialize(byte[] raw_message)
    {
        ByteBuffer buffer = ByteBuffer.allocate(36);
        header.deserialize(raw_message);
        buffer.put(raw_message, 24,12);
        effect_num = Effects_Enum.values()[buffer.getInt()];
        color_num = Color_Enums.values()[buffer.getInt()];
        duration_sec = buffer.getInt();

        return header.verifyMagicString();
    }

    @Override
    public byte[] serialize()
    {
        ByteBuffer buffer = ByteBuffer.allocate(36);
        buffer.put(header.serialize());
        buffer.putInt(effect_num.ordinal());
        buffer.putInt(color_num.ordinal());
        buffer.putInt(duration_sec);

        return buffer.array();
    }

    //white
    //red
    //green
    //blue
    //purple
    //yellow
    //orange
    //turquoise
    //random
    //off

}