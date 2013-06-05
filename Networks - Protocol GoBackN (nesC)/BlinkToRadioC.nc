 #include <Timer.h>
 #include "BlinkToRadio.h"
 
module BlinkToRadioC {
  uses {
    interface Boot;
    interface SplitControl as RadioControl;

    interface Leds;
    //interface Timer<TMilli> as Timer0;
    //OneShot timer for use on timeout
    interface Timer<TMilli> as Timer1;
    interface Timer<TMilli> as Timer2;
    interface Timer<TMilli> as Timer3;
    interface Timer<TMilli> as Timer4;

    interface Packet;
    interface AMPacket;
    interface AMSendReceiveI;
  }
}

implementation {
  uint16_t counter = 0;
  uint16_t seqnum = 1;

  //Buffer for sending message
  message_t sendMsgBuf;
  message_t* sendMsg = &sendMsgBuf; // initially points to sendMsgBuf

  //Buffer for the ackmsg
  message_t ackMsgBuf;
  message_t* ackMsg = &ackMsgBuf;


  //Boolean value - determines if ACK has been received
  //Initially true to send first message
  bool ackRec = TRUE;

  //Array of buffers to store sent messages
  message_t messages[8];
  message_t *buff[8];
  
  //loop
  int i;

  //Where to save current message in buffer
  int saveind;

  //Current position to read from buffer one for each timer
  int saveind1 = 0;
  int saveind2 = 1;
  int saveind3 = 2;
  int saveind4 = 3;

  //Stores left-end of window
  int trailing = 0;

  //Stores right-end of window
  int leading = 3;

  //Sent Messages
  int sent = 0;

  //On boot start radio
  event void Boot.booted() {
    call RadioControl.start();     

  };


  event void RadioControl.stopDone(error_t error){};


  //Task to send message
  task void taskSendMessage() {  

      BlinkToRadioMsg* btrpkt;      
      BlinkToRadioMsg* savebtrpkt;

   //limits sent messages to 4 at a time
    if (sent < 4){    
      
      //Determine where to save
      if(counter < 3){

	saveind = counter;
      }
      else{

	saveind = leading;

      }

      call AMPacket.setType(sendMsg, AM_BLINKTORADIO);
      call AMPacket.setDestination(sendMsg, DEST_ECHO);
      call AMPacket.setSource(sendMsg, TOS_NODE_ID);
      call Packet.setPayloadLength(sendMsg, sizeof(BlinkToRadioMsg));

      btrpkt = (BlinkToRadioMsg*)(call Packet.getPayload(sendMsg, sizeof (BlinkToRadioMsg)));
      counter++;
      btrpkt->type = TYPE_DATA;
      btrpkt->seq = seqnum;
      btrpkt->nodeid = TOS_NODE_ID;
      btrpkt->counter = counter;

      //Save into buffer array, stay within window

      savebtrpkt = (BlinkToRadioMsg*)(call Packet.getPayload(buff[saveind], sizeof (BlinkToRadioMsg)));
  
      savebtrpkt -> type = btrpkt -> type; 
      savebtrpkt -> seq = btrpkt -> seq;
      savebtrpkt -> nodeid = btrpkt -> nodeid;
      savebtrpkt -> counter = btrpkt -> counter;

      //send message and store returned pointer to free buffer for next message     
      sendMsg = call AMSendReceiveI.send(sendMsg);
      
      //Start relevant timer and point it to where it's message currently is
      if(seqnum == 1){
      
	saveind1 = saveind;
		call Timer1.startOneShot(900);
      }
      if(seqnum == 2){

	saveind2 = saveind;
		call Timer2.startOneShot(900);
      }
      if(seqnum == 3){
	
	saveind3 = saveind;
		call Timer3.startOneShot(900);
	
      }
      if(seqnum == 4){

	saveind4 = saveind;
		call Timer4.startOneShot(900);

      }

      //Seqnum increase
      seqnum++;
      sent++;

      //Repost task
      post taskSendMessage();

     }     
  }

    //After xs resend  message1
    event void Timer1.fired(){

     //Create message using value in buffer
     call AMPacket.setType(buff[saveind1], AM_BLINKTORADIO);
     call AMPacket.setDestination(buff[saveind1], DEST_ECHO);
     call AMPacket.setSource(buff[saveind1], TOS_NODE_ID);

     call Packet.setPayloadLength(buff[saveind1], sizeof(BlinkToRadioMsg));

   
    //Send but don't free the  buffer, may need to resend this message
    call AMSendReceiveI.send(buff[saveind1]);
    //Restart timeout, will be stopped if ack is received
    call Timer1.startOneShot(900);

 }


    //After xs resend  message2
    event void Timer2.fired(){

     //Create message using value in buffer
     call AMPacket.setType(buff[saveind2], AM_BLINKTORADIO);
     call AMPacket.setDestination(buff[saveind2], DEST_ECHO);
     call AMPacket.setSource(buff[saveind2], TOS_NODE_ID);
     call Packet.setPayloadLength(buff[saveind2], sizeof(BlinkToRadioMsg));

   
    //Send but don't free the  buffer, may need to resend this message
    call AMSendReceiveI.send(buff[saveind2]);
    //Restart timeout, will be stopped if ack is received
    call Timer2.startOneShot(900);

 }


    //After xs resend  message3
    event void Timer3.fired(){

     //Create message using value in buffer
     call AMPacket.setType(buff[saveind3], AM_BLINKTORADIO);
     call AMPacket.setDestination(buff[saveind3], DEST_ECHO);
     call AMPacket.setSource(buff[saveind3], TOS_NODE_ID);
     call Packet.setPayloadLength(buff[saveind3], sizeof(BlinkToRadioMsg));

   
    //Send but don't free the  buffer, may need to resend this message
    call AMSendReceiveI.send(buff[saveind3]);
    //Restart timeout, will be stopped if ack is received
    call Timer3.startOneShot(900);

 }

    //After xs resend  message4
    event void Timer4.fired(){

     //Create message using value in buffer
     call AMPacket.setType(buff[saveind4], AM_BLINKTORADIO);
     call AMPacket.setDestination(buff[saveind4], DEST_ECHO);
     call AMPacket.setSource(buff[saveind4], TOS_NODE_ID);
     call Packet.setPayloadLength(buff[saveind4], sizeof(BlinkToRadioMsg));

   
    //Send but don't free the  buffer, may need to resend this message
    call AMSendReceiveI.send(buff[saveind4]);
    //Restart timeout, will be stopped if ack is received
    call Timer4.startOneShot(900);

 }

  //Receive event - occurs whenever a message(msg) is received
  event message_t* AMSendReceiveI.receive(message_t* msg) {


    uint8_t len = call Packet.payloadLength(msg);
    BlinkToRadioMsg* btrpkt = (BlinkToRadioMsg*)(call Packet.getPayload(msg, len));
    BlinkToRadioMsg* ack;

    //If the received message is _not_ an ACK (it's a regular message) then send an ACK
    if (btrpkt->type == TYPE_DATA){      

      //Setup message values for ack
      call AMPacket.setType(ackMsg, AM_BLINKTORADIO);
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

      //Waiting for ack with this seqnum
      // waiting = btrpkt->seq;

      //Now send the ack
      ackMsg = call AMSendReceiveI.send(ackMsg); 

    }
    //If ack has been received
    else if (btrpkt->type == TYPE_ACK){

  
      //Compare sequence number
      if(btrpkt->seq == seqnum-1){

	//Cancel appropriate timeout
	if(seqnum-1 == 1){
	  call Timer1.stop();
	}
	if(seqnum-1 == 2){
	  call Timer2.stop();
	}
	if(seqnum-1 == 3){
	  call Timer3.stop();
	}
	if(seqnum-1 == 4){
	  call Timer4.stop();
	}

	//Reset seqnum if needed
	if(seqnum == 5){
	  seqnum = 1;
       	}

	//Now 'move up' the window to allow another message to be sent
	if(trailing == 7){

	  trailing = 0;

	}
	else{

	  trailing++;
	}

	if(leading == 7){

	  leading = 0;
	}
	else{

	  leading++;
	}
	
	sent--;

	//Post task
	post taskSendMessage();
      }     
    }

    call Leds.set(btrpkt->counter);
    return msg; // no need to make msg point to new buffer as msg is no longer needed
  
  }
  
   
  //If successful then populate buffer
  event void RadioControl.startDone(error_t error) {
    if (error == SUCCESS) {

      //Setup buffer array
      for(i = 0; i < 8; i++){
	
	buff[i] = &messages[i];

      }

      post taskSendMessage();

    }
  }
}
 
   
  






