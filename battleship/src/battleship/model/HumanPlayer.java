
package battleship.model;

import battleship.controller.*;
import battleship.view.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class HumanPlayer extends Player
{
  private final static Logger LOGGER = Logger.getGlobal();
  private View view;
  public enum UserInterface { CommandLineView, GUI2dView }
  public UserInterface userinterface = UserInterface.CommandLineView;

  public HumanPlayer( )
  {
    LOGGER.info( this + ":HumanPlayer:Constructor" );
    this.view = null;
  }

  public void init( )
  {
    LOGGER.info( this + ":HumanPlayer:init" );
    switch( userinterface )
    {
      case CommandLineView:
        LOGGER.info( "HumanPlayer: create view CommandLineView" );
        LOGGER.severe( "XXXXXXXXX view created: " + this );

        view = new CommandLineView( this );
        if( view == null)
            LOGGER.severe( "XXXXXXXXX view [1] null: " + this );

        break;
      case GUI2dView:
        LOGGER.info( "HumanPlayer: create view GUI2dView" );
        view = new GUI2dView( this );
        break;
    }
    view.do_start();
  }

  public void setUserInterface( UserInterface c )
  {
    LOGGER.info( this + ":HumanPlayer:setuserInterface: " + c );
    userinterface = c;
  }

  public void changingPlayer( )
  {
    LOGGER.info( this + ":HumanPlayer:changingPlayer" );
    view.changingPlayer( );
  }

  public void do_update( )
  {
    LOGGER.info( this + ":HumanPlayer:do_update" );
    view.do_update( );
  }

  public void youLost( )
  {
    LOGGER.info( this + ":HumanPlayer:youLost" );
    view.youLost( );
  }

  public void youWon( )
  {
    LOGGER.info( this + ":HumanPlayer:youWon" );
    view.youWon( );
  }

  public void yourTurn( )
  {
    LOGGER.info( this + ":HumanPlayer:yourTurn" );
    this.view.yourTurn( );
  }

  public void startingGame() {
    LOGGER.info( this + ":HumanPlayer:startingGame" );
    view.startingGame( );
  }


  //
  // methods called by view
  //
  public void setOpponendAI( )
  {
    LOGGER.info( this + ":HumanPlayer:setOpponendAI" );
    engine.setOpponendAI( );
  }
  public void setOpponendNetworkClient( String ip )
  {
    LOGGER.info( this + ":HumanPlayer:setOpponendNetworkClient" );
    engine.setOpponendNetworkClient(ip );
  }
  public void setOpponendNetworkServer( ) // Server
  {
    LOGGER.info( this + ":HumanPlayer:setOpponendNetworkServer" );
    engine.setOpponendNetworkServer();
  }
  public ArrayList<Ship> getShips( )
  {
    LOGGER.info( this + ":HumanPlayer:getShips" );
    return engine.getShips( );
  }
  public boolean placeShip( Ship toPlace, int x, int y )
  {
    LOGGER.info( this + ":HumanPlayer:placeShip" );
    return engine.placeShip( this, toPlace, x, y );
  }
  public void playerReady(  )
  {
    LOGGER.info( this + ":HumanPlayer:playerReady" );
    try {
      engine.playerReady( this );
    }
    catch( Exception e)
    {
      LOGGER.severe( "Error: HumanPlayer:playerReady: " + e );
    }
  }
  public boolean shoot( int x,  int y  )
  {
    LOGGER.info( this + ":HumanPlayer:shoot" );
    return engine.shoot( this, x, y );
  }
  public Grid getGridOpponend( )
  {
    LOGGER.info( this + ":HumanPlayer:getGridOpponend" );
    return engine.getGridOpponend( this );
  }
  public Grid getGrid(  )
  {
    LOGGER.info( this + ":HumanPlayer:getGrid" );
    return super.getGrid( );
  }
  public void restart(  )
  {
    LOGGER.info( this + ":HumanPlayer:restart" );
    engine.restart( );
  }
  public boolean isMyTurn( )
  {
    LOGGER.info( this + ":HumanPlayer:isMyTurn" );
    return engine.isMyTurn( this );
  }
  public boolean isGameover( )
  {
    LOGGER.info( this + ":HumanPlayer:isGameover" );
    return engine.isGameover( );
  }
}
