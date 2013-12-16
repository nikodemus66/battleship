/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.model;

/**
 *
 * @author kraeki
 */

  public enum EngineState { 
      SELECTING_OPPONEND, 
      PREPARING_GRID, 
      PLAY, 
      YOUR_TURN, 
      OPPONENDS_TURN, 
      YOU_LOST,
      YOU_WON,
      FINISHED 
  }
