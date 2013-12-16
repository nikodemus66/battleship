/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.communication;

/**
 *
 * @author kraeki
 */
  public enum MessageType {
    REGISTER,
    
    PLACE_SHIP,
    PLACE_SHIP_RESPONSE,
    
    PLAYER_READY,
    
    SHOOT,
    OPPONEND_SHOOT,
    SHOOT_RESPONSE,
    
    RESTART,
    STATE_CHANGE
  }
