package web_server.messages;

import java.nio.ByteBuffer;

class Heartbeat_Message implements IMessage
{
    private Header header;
    private final int HEARTBEAT_MESSAGE_ID = 0;
    private final int HEARTBEAT_MESSAGE_SIZE = 96;
    private int spare01;
    private int spare02;
    private int spare03;

    Heartbeat_Message()
    {
        header = new Header(HEARTBEAT_MESSAGE_ID, HEARTBEAT_MESSAGE_SIZE);
    }

    public void init()
    {
        header.setTimestamp(System.currentTimeMillis()/1000);
        spare01 = 0;
        spare02 = 0;
        spare03 = 0;
    }

    @Override
    public boolean deserialize(byte[] raw_message)
    {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        header.deserialize(raw_message);

        buffer.put(raw_message,24,12);
        spare01 = buffer.getInt();
        spare02 = buffer.getInt();
        spare03 = buffer.getInt();

        return header.verifyMagicString();
    }

    @Override
    public byte[] serialize()
    {
        ByteBuffer buffer = ByteBuffer.allocate(36);
        buffer.put(header.serialize());
        buffer.putInt(spare01);
        buffer.putInt(spare02);
        buffer.putInt(spare03);

        return  buffer.array();
    }

    public Header getHeader()
    {
        return header;
    }
}