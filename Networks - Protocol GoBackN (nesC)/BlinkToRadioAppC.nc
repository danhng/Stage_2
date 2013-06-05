 #include <Timer.h>
 #include "BlinkToRadio.h"
 
configuration BlinkToRadioAppC {}

implementation {
  components BlinkToRadioC;

  components MainC;
  components LedsC;
  components AMSendReceiveC as Radio;
  // components new TimerMilliC() as Timer0;
  //New timer - oneshot
  components new TimerMilliC() as Timer1;
  components new TimerMilliC() as Timer2;
  components new TimerMilliC() as Timer3;
  components new TimerMilliC() as Timer4;

  BlinkToRadioC.Boot -> MainC;
  BlinkToRadioC.RadioControl -> Radio;

  BlinkToRadioC.Leds -> LedsC;
  //BlinkToRadioC.Timer0 -> Timer0;
  BlinkToRadioC.Timer1 -> Timer1;
  BlinkToRadioC.Timer2 -> Timer2;
  BlinkToRadioC.Timer3 -> Timer3;
  BlinkToRadioC.Timer4 -> Timer4;
  

  BlinkToRadioC.Packet -> Radio;
  BlinkToRadioC.AMPacket -> Radio;
  BlinkToRadioC.AMSendReceiveI -> Radio;
}
