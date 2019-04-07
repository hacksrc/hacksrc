/**
 *<p> This is a simple message interface to be implemented
 * </p>
 */

package web_server.messages;

interface IMessage
{
    final int VERSION = 0;

    boolean deserialize(byte[] raw_message);
    byte[] serialize ();

}