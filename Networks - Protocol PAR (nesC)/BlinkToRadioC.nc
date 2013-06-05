 #include <Timer.h>
 #include "BlinkToRadio.h"
 
module BlinkToRadioC {
  uses {
    interface Boot;
    interface SplitControl as RadioControl;

    interface Leds;
    interface Timer<TMilli> as Timer0;
    //OneShot timer for use on timeout
    interface Timer<TMilli> as Timer1;

    interface Packet;
    interface AMPacket;
    interface AMSendReceiveI;
  }
}

implementation {
  uint16_t counter = -1;
  message_t sendMsgBuf;
  message_t* sendMsg = &sendMsgBuf; // initially points to sendMsgBuf


  //Buffer for the  ackmsg
  message_t ackMsgBuf;
  message_t* ackMsg = &ackMsgBuf;

  //Buffer for saved message
  message_t saveMsgBuf;
  message_t* saveMsg = &saveMsgBuf;

  //Boolean value - determines if ACK has been received
  //Initially true to send first message
  bool ackRec = TRUE;

  //On boot start radio
  event void Boot.booted() {
    call RadioControl.start();
  };

  //If successful then start timer0 (sends messages)
  event void RadioControl.startDone(error_t error) {
    if (error == SUCCESS) {
      call Timer0.startPeriodic(TIMER_PERIOD_MILLI);
    }
  };

  event void RadioControl.stopDone(error_t error){};



  event void Timer0.fired() {

    //Begin timeout timer - if it hasn't already been started
    //Will resend previous message if no ack is received in 10s
    if(call Timer1.isRunning() == FALSE){      
      if(ackRec == FALSE){
	call Timer1.startOneShot(10000);
      }
    }

    //If an ack has been received create and send message
    while(ackRec == TRUE){

  
      BlinkToRadioMsg* btrpkt;      

      call AMPacket.setType(sendMsg, 6);
      call AMPacket.setDestination(sendMsg, DEST_ECHO);
      call AMPacket.setSource(sendMsg, TOS_NODE_ID);
      call Packet.setPayloadLength(sendMsg, sizeof(BlinkToRadioMsg));

      btrpkt = (BlinkToRadioMsg*)(call Packet.getPayload(sendMsg, sizeof (BlinkToRadioMsg)));
      counter++;
      btrpkt->type = TYPE_DATA;
      //Counter increments each time fired is called, 
      //So, sequence will be 0 when counter is even and 1 when it is odd
      btrpkt->seq = counter%2;
      btrpkt->nodeid = TOS_NODE_ID;
      btrpkt->counter = counter;

      //Save the message for use in timeout if ack is not received after sending
      //Old message is only overwritten once ack is received
      saveMsg = sendMsg;

      // send message and store returned pointer to free buffer for next message
      sendMsg = call AMSendReceiveI.send(sendMsg);
      //Then set boolean to false - awaiting ack - send no more messages
      ackRec = FALSE;

      //Stop timeout, ack received and message sent
      call Timer1.stop();

    }
 
  }

  //After 10s resend the message
  event void Timer1.fired(){
    
   
    //Don't free the  buffer, may need to resend this message
    call AMSendReceiveI.send(saveMsg);
    
 }


  //Receive event - occurs whenever a message(msg) is received
  event message_t* AMSendReceiveI.receive(message_t* msg) {


    uint8_t len = call Packet.payloadLength(msg);
    BlinkToRadioMsg* btrpkt = (BlinkToRadioMsg*)(call Packet.getPayload(msg, len));
    BlinkToRadioMsg* ack;

    //If the received message is _not_ an ACK (it's a regular message) then send an ACK
    if (btrpkt->type == TYPE_DATA){      

      //Setup message values for ack
      call AMPacket.setType(ackMsg, 6);
      call AMPacket.setDestination(ackMsg, DEST_ECHO);
      call AMPacket.setSource(ackMsg, TOS_NODE_ID);
      call Packet.setPayloadLength(ackMsg, sizeof(BlinkToRadioMsg));

      ack = (BlinkToRadioMsg*)(call Packet.getPayload(ackMsg, sizeof (BlinkToRadioMsg)));
      //Set type of message to ACK (distinguish from regular message)
      ack->type = TYPE_ACK;
      
      //Match ack sequence number to message sequence number
      ack->seq = btrpkt->seq;
      ack->nodeid = TOS_NODE_ID;
      ack->counter = counter;    

      //Now send the ack
      ackMsg = call AMSendReceiveI.send(ackMsg); 

      //Stop timeout(if running),message received, ack sent
      call Timer1.stop();

    }
    else{

      //Otherwise an ack has been received, set boolean to true
      ackRec = TRUE;

    }

    call Leds.set(btrpkt->counter);
    return msg; // no need to make msg point to new buffer as msg is no longer needed
  
  }

} 
