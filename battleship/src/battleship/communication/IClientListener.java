
package battleship.communication;

public interface IClientListener
{

    /**
     *
     * @param message
     */
    void receive( Message message );
}
