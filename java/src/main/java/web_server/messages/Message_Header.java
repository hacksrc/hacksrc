package web_server.messages;

import java.nio.*;

class Header implements IMessage
{
    private int magic_string = 7;
    private int message_id;
    private long timestamp;
    private int body_size;

    Header(int messageid, int bodysize)
    {
        message_id = messageid;
        body_size = bodysize;
    }

    @Override
    public boolean deserialize(byte[] raw_message)
    {
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.put(raw_message, 0, 24);
        magic_string = buffer.getInt();
        message_id = buffer.getInt();
        timestamp = buffer.getLong();
        body_size = buffer.getInt();

        boolean return_value = false;
        if(magic_string == 7)
        {
            return_value = true;
        }
        return return_value;
    }

    @Override
    public byte[] serialize()
    {
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.putInt(magic_string);
        buffer.putInt(message_id);
        buffer.putLong(timestamp);
        buffer.putInt(body_size);

        return buffer.array();
    }

    public int getMagic_string()
    {
        return magic_string;
    }

    public int getBody_size()
    {
        return body_size;
    }

    public int getMessage_id()
    {
        return message_id;
    }

    public void setTimestamp(long timestamp)
    {
        timestamp = timestamp;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public boolean verifyMagicString()
    {
        return magic_string == 7;
    }
}